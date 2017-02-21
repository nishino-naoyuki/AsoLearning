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
import javax.servlet.http.HttpSession;

import jp.ac.asojuku.asolearning.dto.UserDto;
import jp.ac.asojuku.asolearning.exception.AsoLearningSystemErrException;
import jp.ac.asojuku.asolearning.param.RequestConst;

/**
 * ユーザー登録完了
 * @author nishino
 *
 */
@WebServlet(name="CreateUserCompleteServlet",urlPatterns={"/tc_insertUserComplete"})
public class CreateUserCompleteServlet extends BaseServlet {

	private final String DISPNO = "00804";
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

		UserDto dto = null;

		if( session != null ){
			dto = (UserDto)session.getAttribute(RequestConst.REQUEST_USER_DTO);
		}

		//	セッションに情報が無い場合はシステムエラー（検査）
		if( dto == null  ){
			throw new AsoLearningSystemErrException("セッションから課題情報が取得できません");
		}

		//削除
		session.removeAttribute(RequestConst.REQUEST_TASK_DTO);

		/////////////////////////////////////
		//画面遷移
		RequestDispatcher rd = req.getRequestDispatcher("view/tc_createUserFin.jsp");
		rd.forward(req, resp);
	}
}
