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

import com.fasterxml.jackson.databind.ObjectMapper;

import jp.ac.asojuku.asolearning.bo.CourseBo;
import jp.ac.asojuku.asolearning.bo.HistoryBo;
import jp.ac.asojuku.asolearning.bo.impl.CourseBoImpl;
import jp.ac.asojuku.asolearning.bo.impl.HistoryBoImpl;
import jp.ac.asojuku.asolearning.condition.SearchHistoryCondition;
import jp.ac.asojuku.asolearning.dto.CourseDto;
import jp.ac.asojuku.asolearning.dto.HistoryDto;
import jp.ac.asojuku.asolearning.exception.AsoLearningSystemErrException;
import jp.ac.asojuku.asolearning.json.HistorySearchResultJson;
import jp.ac.asojuku.asolearning.param.ActionId;
import jp.ac.asojuku.asolearning.param.RequestConst;
import jp.ac.asojuku.asolearning.param.RoleId;

/**
 * お知らせ検索
 * @author nishino
 *
 */
@WebServlet(name="SearchHistoryServlet",urlPatterns={"/searchHistoryAjax"})
public class SearchHistoryServlet extends BaseServlet {

	private final String DISPNO = "01601";
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
        	SearchHistoryCondition cond = getCondition(req);

    		//////////////////////////////
    		//学科一覧を取得
    		CourseBo coursBo = new CourseBoImpl();

    		List<CourseDto> list = coursBo.getCourseAllList();
    		req.setAttribute(RequestConst.REQUEST_COURSE_LIST, list);

    		HistoryBo histBo = new HistoryBoImpl();

    		List<HistoryDto> retDtoList = histBo.getList(cond, 0, -1);


			List<HistorySearchResultJson> jsonList = new ArrayList<>();
			for(HistoryDto dto : retDtoList){
				HistorySearchResultJson json = getHistorySearchResultJsonFromDto(dto);
				if( json != null ){
					jsonList.add(json);
				}
			}

			HistorySearchResultJson[] jsons = jsonList.toArray(new HistorySearchResultJson[0]);

	        ObjectMapper mapper = new ObjectMapper();
	        String jsonString = mapper.writeValueAsString(jsons);

	        //logger.trace("jsonString:{}",jsonString);
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
	 * 検索条件の取得
	 * @param req
	 * @return
	 * @throws AsoLearningSystemErrException
	 */
	private SearchHistoryCondition getCondition(HttpServletRequest req) throws AsoLearningSystemErrException{
		SearchHistoryCondition cond = new SearchHistoryCondition();

		cond.setActionId(
				ActionId.getByInteger(getIntParam(RequestConst.REQUEST_ACTION_ID,req))
				);
		cond.setCourseId(getIntParam(RequestConst.REQUEST_COURSE_ID,req));
		cond.setRoleId(RoleId.search(getIntParam(RequestConst.REQUEST_ROLE_ID,req)));
		cond.setFromDate(req.getParameter("dispTermFrom"));
		cond.setToDate(req.getParameter("dispTermTo"));
		cond.setMail(req.getParameter("mail"));

		return cond;
	}

	/**
	 * DTO -> JSON変換
	 * @param dto
	 * @return
	 */
	private HistorySearchResultJson getHistorySearchResultJsonFromDto(HistoryDto dto){
		HistorySearchResultJson json = new HistorySearchResultJson();

		json.actionName = dto.getActionName();
		json.courseName = dto.getCourseName();
		json.mailAddress = dto.getMailAddress();
		json.logDate = dto.getActionDate();
		json.message = dto.getMessage();

		return json;
	}
}
