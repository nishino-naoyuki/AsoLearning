/**
 *
 */
package jp.ac.asojuku.asolearning.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.ac.asojuku.asolearning.dto.TaskDto;
import jp.ac.asojuku.asolearning.dto.TaskPublicDto;
import jp.ac.asojuku.asolearning.dto.TaskTestCaseDto;
import jp.ac.asojuku.asolearning.exception.AsoLearningSystemErrException;
import jp.ac.asojuku.asolearning.param.RequestConst;

/**
 * 課題作成完了
 * @author nishino
 *
 */
@WebServlet(name="CreateTaskCompleteServlet",urlPatterns={"/tc_insertTaskComplete"})
public class CreateTaskCompleteServlet extends BaseServlet {

	private final String DISPNO = "display00503";
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


		/////////////////////////////////////
		//セッションの課題情報を削除
		HttpSession session = req.getSession(false);

		TaskDto dto = null;
		List<TaskTestCaseDto> testCaseList = null;
		List<TaskPublicDto> taskPublicList = null;

		if( session != null ){
			dto = (TaskDto)session.getAttribute(RequestConst.REQUEST_TASK_DTO);
			testCaseList = (List<TaskTestCaseDto>)session.getAttribute(RequestConst.REQUEST_PUBLICSTATE);
			taskPublicList = (List<TaskPublicDto>)session.getAttribute(RequestConst.REQUEST_TESTCASE);
		}

		//	セッションに情報が無い場合はシステムエラー（検査）
		if( dto == null || testCaseList == null || taskPublicList ==null ){
			throw new AsoLearningSystemErrException("セッションから課題情報が取得できません");
		}

		//削除
		session.removeAttribute(RequestConst.REQUEST_TASK_DTO);
		session.removeAttribute(RequestConst.REQUEST_PUBLICSTATE);
		session.removeAttribute(RequestConst.REQUEST_TESTCASE);

		/////////////////////////////////////
		//画面遷移
		RequestDispatcher rd = req.getRequestDispatcher("view/tc_createTaskFin.jsp");
		rd.forward(req, resp);
	}


}
