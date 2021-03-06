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

import jp.ac.asojuku.asolearning.bo.UserBo;
import jp.ac.asojuku.asolearning.bo.impl.UserBoImpl;
import jp.ac.asojuku.asolearning.condition.SearchUserCondition;
import jp.ac.asojuku.asolearning.dto.UserSearchResultDto;
import jp.ac.asojuku.asolearning.exception.AsoLearningSystemErrException;
import jp.ac.asojuku.asolearning.param.RequestConst;

/**
 * CSVを出力する
 *
 * @author nishino
 *
 */
@WebServlet(name="CreateUserCSVServlet",urlPatterns={"/creUserCsv"})
public class CreateUserCSVServlet extends BaseServlet {

	Logger logger = LoggerFactory.getLogger(CreateRankingCSV.class);

	private final String DISPNO = "01202";
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
			//課題一覧を取得
			UserBo userBo = new UserBoImpl();

			List<UserSearchResultDto> retDtoList = userBo.search(cond);

			//ファイル名を取得
			String fname = userBo.createUserCSV(retDtoList);

			//////////////////////////////////////////////
	        //出力処理を行う
	        resp.setContentType("text/plain; charset=utf-8");
	        out = resp.getOutputStream();
	        out.write(fname.getBytes());
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
