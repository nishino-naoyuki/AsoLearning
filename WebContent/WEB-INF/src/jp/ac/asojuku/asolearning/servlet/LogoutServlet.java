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

import jp.ac.asojuku.asolearning.config.MessageProperty;
import jp.ac.asojuku.asolearning.exception.AsoLearningSystemErrException;
import jp.ac.asojuku.asolearning.param.RequestConst;

/**
 * ログアウト処理
 * @author nishino
 *
 */
@WebServlet(name="LogoutServlet",urlPatterns={"/logout"})
public class LogoutServlet extends BaseServlet {

	private final String DISPNO = "00001";
	@Override
	protected String getDisplayNo() {
		return DISPNO;
	}
	@Override
	protected void doGetMain(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException, AsoLearningSystemErrException {

		HttpSession session = req.getSession(false);

		if( session != null ){
			//セッションの破棄
			session.invalidate();
		}

		fowardLoginDisp(req,resp);
	}

	/**
	 * ログイン画面へ転送
	 * @param request
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 * @throws AsoLearningSystemErrException
	 */
	private void fowardLoginDisp(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException, AsoLearningSystemErrException{
		//エラーメッセージをセット
		String errMsg;

		errMsg = MessageProperty.getInstance().getProperty(MessageProperty.LOGOUT_MSG);

		request.setAttribute(RequestConst.LOGIN_ERR_MSG,errMsg );

		//画面転送
		RequestDispatcher rd = request.getRequestDispatcher(getJspDir()+"login.jsp");
		rd.forward(request, resp);

	}
}
