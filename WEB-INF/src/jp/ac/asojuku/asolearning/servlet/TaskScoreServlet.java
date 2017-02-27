/**
 *
 */
package jp.ac.asojuku.asolearning.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jp.ac.asojuku.asolearning.bo.ResultBo;
import jp.ac.asojuku.asolearning.bo.impl.ResultBoImpl;
import jp.ac.asojuku.asolearning.dto.LogonInfoDTO;
import jp.ac.asojuku.asolearning.dto.TaskResultDetailDto;
import jp.ac.asojuku.asolearning.exception.AsoLearningSystemErrException;
import jp.ac.asojuku.asolearning.param.RequestConst;

/**
 * 得点画面
 * @author nishino
 *
 */
@WebServlet(name="TaskScoreServlet",urlPatterns={"/scoredetail"})
public class TaskScoreServlet extends BaseServlet {

	Logger logger = LoggerFactory.getLogger(TaskScoreServlet.class);
	private final String DISPNO = "00202";
	@Override
	protected String getDisplayNo() {
		return DISPNO;
	}
	/* (非 Javadoc)
	 * @see jp.ac.asojuku.asolearning.servlet.BaseServlet#doGetMain(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doGetMain(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException, AsoLearningSystemErrException {

		//////////////////////////////////
		//パラメータ取得
		String dispNo = req.getParameter(RequestConst.REQUEST_DISP_NO);
		Integer taskId = getIntParam(RequestConst.REQUEST_TASK_ID,req);
		Integer userId = getIntParam("userId",req);

		//////////////////////////////////
		//結果情報取得

		if( userId == null){
			//引数でUserIDを指定されない場合は、セッションからログイン情報を取得
			LogonInfoDTO loginInfo = getUserInfoDtoFromSession(req);

			userId = loginInfo.getUserId();
		}

		ResultBo bo = new ResultBoImpl();
		TaskResultDetailDto dto =bo.getResultDetail(taskId, userId);
		if( dto == null ){
			//IDが無い場合は、エラー画面へ
			RequestDispatcher rd = req.getRequestDispatcher("view/error/st_task_error.jsp");
			rd.forward(req, resp);
			return;
		}

		//////////////////////////////
		//画面転送
		req.setAttribute(RequestConst.REQUEST_DISP_NO, dispNo);
		req.setAttribute(RequestConst.REQUEST_TASK_RESULT, dto);
		req.setAttribute(RequestConst.REQUEST_USER_ID, userId);
		RequestDispatcher rd = req.getRequestDispatcher("view/st_taskscore.jsp");
		rd.forward(req, resp);

	}
	/* (非 Javadoc)
	 * @see jp.ac.asojuku.asolearning.servlet.BaseServlet#doPostMain(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doPostMain(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException, AsoLearningSystemErrException {

		doGetMain(req, resp);
	}


}
