/**
 *
 */
package jp.ac.asojuku.asolearning.servlet;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import jp.ac.asojuku.asolearning.bo.CourseBo;
import jp.ac.asojuku.asolearning.bo.UserBo;
import jp.ac.asojuku.asolearning.bo.impl.CourseBoImpl;
import jp.ac.asojuku.asolearning.bo.impl.UserBoImpl;
import jp.ac.asojuku.asolearning.condition.SearchUserCondition;
import jp.ac.asojuku.asolearning.dto.CourseDto;
import jp.ac.asojuku.asolearning.dto.TaskResultDto;
import jp.ac.asojuku.asolearning.dto.UserDto;
import jp.ac.asojuku.asolearning.dto.UserSearchResultDto;
import jp.ac.asojuku.asolearning.exception.AsoLearningSystemErrException;
import jp.ac.asojuku.asolearning.json.UserSearchResultJson;
import jp.ac.asojuku.asolearning.param.RequestConst;

/**
 * @author nishino
 *
 */
@WebServlet(name="SearchUserServlet",urlPatterns={"/searchUserAjax"})
public class SearchUserServlet extends BaseServlet {

	Logger logger = LoggerFactory.getLogger(SearchUserServlet.class);

	private final String DISPNO = "00604";
	@Override
	protected String getDisplayNo() {
		return DISPNO;
	}

	@Override
	protected void doGetMain(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException, AsoLearningSystemErrException {


        OutputStream out = null;

        try{
			//////////////////////////////
			//パラメータを取得
        	SearchUserCondition cond = getCondition(req);

    		//////////////////////////////
    		//学科一覧を取得
    		CourseBo coursBo = new CourseBoImpl();

    		List<CourseDto> list = coursBo.getCourseAllList();
    		req.setAttribute(RequestConst.REQUEST_COURSE_LIST, list);

			//////////////////////////////
			//課題一覧を取得
			UserBo userBo = new UserBoImpl();

			List<UserSearchResultDto> retDtoList = userBo.search(cond);

			List<UserSearchResultJson> jsonList = new ArrayList<>();
			for(UserSearchResultDto dto : retDtoList){
				UserSearchResultJson json = getUserSearchResultJsonFromDto(dto,cond);
				if( json != null ){
					jsonList.add(json);
				}
			}

			UserSearchResultJson[] jsons = jsonList.toArray(new UserSearchResultJson[0]);

	        ObjectMapper mapper = new ObjectMapper();
	        String jsonString = mapper.writeValueAsString(jsons);

	        logger.trace("jsonString:{}",jsonString);
			//////////////////////////////////////////////
	        //JSON出力処理を行う
	        resp.setContentType("application/json; charset=utf-8");
	        out = resp.getOutputStream();
	        out.write(jsonString.getBytes());
	        out.flush();

        }finally{
        	if(out != null){
        		out.close();
        	}
        }
	}

	/**
	 * DTO->JSON
	 * @param dto
	 * @param cond
	 * @return
	 * @throws AsoLearningSystemErrException
	 */
	private UserSearchResultJson getUserSearchResultJsonFromDto(UserSearchResultDto dto,SearchUserCondition cond ) throws AsoLearningSystemErrException{

		UserSearchResultJson json = null;

		UserDto userDto = dto.getUserDto();

		if( cond.getGrade() != null || userDto.getGrade() == null ||
				userDto.getGrade() == cond.getGrade() ){
			json = new UserSearchResultJson();

			json.userId = userDto.getUserId();
			json.name = userDto.getName();
			json.mailAdress = userDto.getMailAdress();
			json.courseName = userDto.getCourseName();
			if(userDto.getGrade() != null){
				json.grade = userDto.getGrade().toString();
			}else{
				json.grade = "";
			}
			json.nickName = userDto.getNickName() ;

			List<String> handedList = new ArrayList<>();
			for(TaskResultDto ret : dto.getResultList()){
				if( ret.isHanded() ){
					handedList.add(ret.getTaskName());
				}
			}
		}

		return json;
	}

	/**
	 * 検索条件の取得
	 * @param req
	 * @return
	 */
	private SearchUserCondition getCondition(HttpServletRequest req){
		SearchUserCondition cond = new SearchUserCondition();

		cond.setName(req.getParameter("username"));
		cond.setMailaddress(req.getParameter("mailaddress"));
		cond.setGrade(getIntParam("grade",req));
		cond.setRoleId(getIntParam(RequestConst.REQUEST_ROLE_ID,req));
		cond.setCourseId(getIntParam(RequestConst.REQUEST_COURSE_ID,req));
		cond.setTaskId(getIntParam(RequestConst.REQUEST_TASK_ID,req));
		cond.setHanded(getIntParam(RequestConst.REQUEST_STATUS,req));

		return cond;
	}


}
