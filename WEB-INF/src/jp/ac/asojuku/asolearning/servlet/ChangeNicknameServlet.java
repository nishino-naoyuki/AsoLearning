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

import jp.ac.asojuku.asolearning.bo.UserBo;
import jp.ac.asojuku.asolearning.bo.impl.UserBoImpl;
import jp.ac.asojuku.asolearning.dto.LogonInfoDTO;
import jp.ac.asojuku.asolearning.err.ActionErrors;
import jp.ac.asojuku.asolearning.exception.AsoLearningSystemErrException;
import jp.ac.asojuku.asolearning.param.RequestConst;
import jp.ac.asojuku.asolearning.param.SessionConst;
import jp.ac.asojuku.asolearning.validator.UserValidator;

/**
 * @author nishino
 *
 */
@WebServlet(name="ChangeNicknameServlet",urlPatterns={"/nicknamechange"})
public class ChangeNicknameServlet extends BaseServlet {
	private ActionErrors errors;

	private final String DISPNO = "00302";
	@Override
	protected String getDisplayNo() {
		return DISPNO;
	}
	/* (非 Javadoc)
	 * @see jp.ac.asojuku.asolearning.servlet.BaseServlet#doPostMain(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doPostMain(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException, AsoLearningSystemErrException {

		errors = new ActionErrors();

		///////////////////////////////////
		//パラメータの取得
		String nickname = req.getParameter("nickname");

		//////////////////////////////////
		//エラーチェック
		UserValidator.useNickName(nickname, errors);

		if( errors.isHasErr() ){
			//画面遷移
			req.setAttribute(RequestConst.REQUEST_ERRORS, errors);
			RequestDispatcher rd = req.getRequestDispatcher("view/st_nicknameChange.jsp");
			rd.forward(req, resp);
			return;
		}
		//////////////////////////////////
		//ログイン情報取得
		LogonInfoDTO logonInfo = getUserInfoDtoFromSession(req);

		//////////////////////////////////
		//更新
		UserBo userdo = new UserBoImpl();

		userdo.updateNickName(logonInfo.getUserId(), nickname, logonInfo.getMailAddress());

		//////////////////////////////////
		//セッションの情報を更新
		logonInfo.setNickName(nickname);
		HttpSession session = req.getSession(false);
		session.setAttribute(SessionConst.SESSION_LOGININFO, logonInfo);

		RequestDispatcher rd = req.getRequestDispatcher("view/st_nicknameChangeFin.jsp");
		rd.forward(req, resp);
	}

}
