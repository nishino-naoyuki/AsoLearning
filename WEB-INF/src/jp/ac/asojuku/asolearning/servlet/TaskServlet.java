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

import jp.ac.asojuku.asolearning.bo.TaskBo;
import jp.ac.asojuku.asolearning.bo.impl.TaskBoImpl;
import jp.ac.asojuku.asolearning.dto.LogonInfoDTO;
import jp.ac.asojuku.asolearning.dto.TaskDto;
import jp.ac.asojuku.asolearning.exception.AsoLearningSystemErrException;
import jp.ac.asojuku.asolearning.param.RequestConst;

/**
 * 課題画面
 * 問題IDを指定品場合は、「指定した問題はありません」を表示
 * @author nishino
 *
 */
@WebServlet(name="TaskServlet",urlPatterns={"/task"})
public class TaskServlet extends BaseServlet {
	Logger logger = LoggerFactory.getLogger(TaskServlet.class);

	private final String DISPNO = "00102";
	@Override
	protected String getDisplayNo() {
		return DISPNO;
	}

	@Override
	protected void doGetMain(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException, AsoLearningSystemErrException {

		doPostMain(req,resp);
	}

	@Override
	protected void doPostMain(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException, AsoLearningSystemErrException {

		//////////////////////////////
		//課題IDを取得
		Integer taskId = getTaskIdFromParam(req);

		//////////////////////////////
		//問題詳細を取得

		//セッションからログイン情報を取得
		LogonInfoDTO loginInfo = getUserInfoDtoFromSession(req);

		TaskBo bo = new TaskBoImpl();
		TaskDto dto =bo.getTaskDetailForUser(taskId, loginInfo);
		if( dto == null ){
			//IDが無い場合は、エラー画面へ
			RequestDispatcher rd = req.getRequestDispatcher("view/error/st_task_error.jsp");
			rd.forward(req, resp);
			return;
		}


		//////////////////////////////
		//画面転送
		req.setAttribute(RequestConst.REQUEST_TASK, dto);
		RequestDispatcher rd = req.getRequestDispatcher("view/st_task.jsp");
		rd.forward(req, resp);
	}

	/**
	 * 一覧画面から
	 * @param req
	 * @return
	 * @throws AsoLearningSystemErrException
	 */
	private Integer getTaskIdFromParam(HttpServletRequest req) throws AsoLearningSystemErrException{
		Integer taskId = null;

		String sTaskId = req.getParameter("taskid");

		try{
			taskId = Integer.parseInt(sTaskId);
		}catch( NumberFormatException e ){
			logger.warn("パラメータが指定されていないか、不正です：",e);
		}

		return taskId;
	}

}
