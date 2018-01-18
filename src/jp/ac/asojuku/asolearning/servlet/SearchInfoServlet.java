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
import jp.ac.asojuku.asolearning.bo.InfomationBo;
import jp.ac.asojuku.asolearning.bo.impl.CourseBoImpl;
import jp.ac.asojuku.asolearning.bo.impl.InfomationBoImpl;
import jp.ac.asojuku.asolearning.condition.SearchInfomationCondition;
import jp.ac.asojuku.asolearning.dto.CourseDto;
import jp.ac.asojuku.asolearning.dto.InfomationSearchResultDto;
import jp.ac.asojuku.asolearning.exception.AsoLearningSystemErrException;
import jp.ac.asojuku.asolearning.json.InformationSearchResultJson;
import jp.ac.asojuku.asolearning.param.RequestConst;
import jp.ac.asojuku.asolearning.util.DateUtil;

/**
 * お知らせ検索
 * @author nishino
 *
 */
@WebServlet(name="SearchInfoServlet",urlPatterns={"/searchInfoAjax"})
public class SearchInfoServlet extends BaseServlet {

	private final String DISPNO = "01601";
	@Override
	protected String getDisplayNo() {
		return DISPNO;
	}

	private final String DATE_FORMAT = "yyyy/mm/dd";

	@Override
	protected void doGetMain(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException, AsoLearningSystemErrException {


        OutputStream out = null;

        try{
			//////////////////////////////
			//パラメータを取得
        	SearchInfomationCondition cond = getCondition(req);

    		//////////////////////////////
    		//学科一覧を取得
    		CourseBo coursBo = new CourseBoImpl();

    		List<CourseDto> list = coursBo.getCourseAllList();
    		req.setAttribute(RequestConst.REQUEST_COURSE_LIST, list);

			//////////////////////////////
			//課題一覧を取得
    		InfomationBo infoBo = new InfomationBoImpl();

    		List<InfomationSearchResultDto> retDtoList = infoBo.search(cond);

			List<InformationSearchResultJson> jsonList = new ArrayList<>();
			for(InfomationSearchResultDto dto : retDtoList){
				InformationSearchResultJson json = getInformationSearchResultJsonFromDto(dto);
				if( json != null ){
					jsonList.add(json);
				}
			}

			InformationSearchResultJson[] jsons = jsonList.toArray(new InformationSearchResultJson[0]);

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
	private SearchInfomationCondition getCondition(HttpServletRequest req) throws AsoLearningSystemErrException{
		SearchInfomationCondition cond = new SearchInfomationCondition();

		cond.setMailAddress(req.getParameter("mailaddress"));
		cond.setCourseId(getIntParam(RequestConst.REQUEST_COURSE_ID,req));
		cond.setMessage(req.getParameter("message"));
		cond.setDispFrom(DateUtil.getDateFrom(req.getParameter("dispTermFrom"), DATE_FORMAT));
		cond.setDsipTo(DateUtil.getDateFrom(req.getParameter("dispTermTo"), DATE_FORMAT));

		return cond;
	}

	/**
	 * DTO -> JSON変換
	 * @param dto
	 * @return
	 */
	private InformationSearchResultJson getInformationSearchResultJsonFromDto(InfomationSearchResultDto dto){
		InformationSearchResultJson json = new InformationSearchResultJson();

		json.infoId = dto.getInfomationId();
		json.maileAddress = dto.getMaileAddress();
		json.nickName = dto.getNickName();
		json.publickCourse = dto.getPublickCourse();
		json.title = dto.getTitle();
		json.termFrom = dto.getTermFrom();
		json.termTo = dto.getTermTo();

		return json;
	}
}
