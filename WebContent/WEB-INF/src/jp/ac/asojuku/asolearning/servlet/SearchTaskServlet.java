/**
 *
 */
package jp.ac.asojuku.asolearning.servlet;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import jp.ac.asojuku.asolearning.bo.TaskBo;
import jp.ac.asojuku.asolearning.bo.impl.TaskBoImpl;
import jp.ac.asojuku.asolearning.condition.SearchTaskCondition;
import jp.ac.asojuku.asolearning.exception.AsoLearningSystemErrException;
import jp.ac.asojuku.asolearning.json.TaskSearchResultJson;
import jp.ac.asojuku.asolearning.param.RequestConst;

/**
 * 課題検索処理
 * @author nishino
 *
 */
@WebServlet(name="SearchTaskServlet",urlPatterns={"/searchTask"})
public class SearchTaskServlet extends BaseServlet {

	Logger logger = LoggerFactory.getLogger(SearchTaskServlet.class);

	private final String DISPNO = "00801";
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
        	SearchTaskCondition cond = getCondition(req);

			//////////////////////////////
			//課題一覧を取得
			TaskBo taskBo = new TaskBoImpl();

			List<TaskSearchResultJson> jsonList = taskBo.search(cond);

			TaskSearchResultJson[] jsons = jsonList.toArray(new TaskSearchResultJson[0]);

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
	 * 検索条件の取得
	 * @param req
	 * @return
	 */
	private SearchTaskCondition getCondition(HttpServletRequest req){
		SearchTaskCondition cond = new SearchTaskCondition();

		cond.setCourseId(getIntParam(RequestConst.REQUEST_COURSE_ID,req));
		cond.setTaskName(req.getParameter("taskname"));
		cond.setCreator(req.getParameter("creator"));
		cond.setGroupName(req.getParameter("groupname"));

		return cond;
	}
}
