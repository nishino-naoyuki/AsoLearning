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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jp.ac.asojuku.asolearning.bo.UserBo;
import jp.ac.asojuku.asolearning.bo.impl.UserBoImpl;
import jp.ac.asojuku.asolearning.err.ActionErrors;
import jp.ac.asojuku.asolearning.err.ErrorCode;
import jp.ac.asojuku.asolearning.exception.AsoLearningSystemErrException;
import jp.ac.asojuku.asolearning.exception.MailNotFoundException;
import jp.ac.asojuku.asolearning.param.RequestConst;
import jp.ac.asojuku.asolearning.validator.UserValidator;

/**
 * @author nishino
 *
 */
@WebServlet(name="ResetPasswordServlet",urlPatterns={"/preset"})
public class ResetPasswordServlet extends BaseServlet {
	private ActionErrors errors;
	Logger logger = LoggerFactory.getLogger(ResetPasswordServlet.class);

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
		String mailaddress = req.getParameter("mailaddress");
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
			RequestDispatcher rd = req.getRequestDispatcher(getJspDir()+"st_passReset.jsp");
			rd.forward(req, resp);
			return;
		}

		//////////////////////////////////
		//更新
		try{
			UserBo userdo = new UserBoImpl();

			userdo.updatePassword( pass1, mailaddress);

		}catch(MailNotFoundException e){
			logger.warn("メールアレスが見つかりません："+mailaddress);
			errors.add(ErrorCode.ERR_MEMBER_ENTRY_MAILNOTFOUND_ERR);
			req.setAttribute(RequestConst.REQUEST_ERRORS, errors);
			RequestDispatcher rd = req.getRequestDispatcher(getJspDir()+"st_passReset.jsp");
			rd.forward(req, resp);
			return;
		}


		RequestDispatcher rd = req.getRequestDispatcher(getJspDir()+"st_passChangeFin.jsp");
		rd.forward(req, resp);
	}

}
