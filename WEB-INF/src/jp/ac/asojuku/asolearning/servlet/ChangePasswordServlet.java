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

import org.apache.commons.lang3.StringUtils;

import jp.ac.asojuku.asolearning.bo.UserBo;
import jp.ac.asojuku.asolearning.bo.impl.UserBoImpl;
import jp.ac.asojuku.asolearning.dto.LogonInfoDTO;
import jp.ac.asojuku.asolearning.err.ActionErrors;
import jp.ac.asojuku.asolearning.err.ErrorCode;
import jp.ac.asojuku.asolearning.exception.AsoLearningSystemErrException;
import jp.ac.asojuku.asolearning.param.RequestConst;
import jp.ac.asojuku.asolearning.validator.UserValidator;

/**
 * @author nishino
 *
 */
@WebServlet(name="ChangePasswordServlet",urlPatterns={"/pchange"})
public class ChangePasswordServlet extends BaseServlet {
	private ActionErrors errors;

	private final String DISPNO = "00402";
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
		String pass1 = req.getParameter("password1");
		String pass2 = req.getParameter("password2");

		//////////////////////////////////
		//エラーチェック
		UserValidator.password(pass1, errors);
		if( !StringUtils.equals(pass1, pass2) ){
			//一致しない場合はエラー
			errors.add(ErrorCode.ERR_MEMBER_ENTRY_PASSWORD_NOTMATCH);
		}

		if( errors.isHasErr() ){
			//画面遷移
			req.setAttribute(RequestConst.REQUEST_ERRORS, errors);
			RequestDispatcher rd = req.getRequestDispatcher("view/st_passChange.jsp");
			rd.forward(req, resp);
			return;
		}
		//////////////////////////////////
		//ログイン情報取得
		LogonInfoDTO logonInfo = getUserInfoDtoFromSession(req);

		//////////////////////////////////
		//更新
		UserBo userdo = new UserBoImpl();

		userdo.updatePassword(logonInfo.getUserId(), pass1, logonInfo.getMailAddress());


		RequestDispatcher rd = req.getRequestDispatcher("view/st_passChangeFin.jsp");
		rd.forward(req, resp);
	}

}
