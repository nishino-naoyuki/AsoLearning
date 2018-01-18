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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jp.ac.asojuku.asolearning.bo.LoginBo;
import jp.ac.asojuku.asolearning.bo.impl.LoginBoImpl;
import jp.ac.asojuku.asolearning.config.MessageProperty;
import jp.ac.asojuku.asolearning.dto.LogonInfoDTO;
import jp.ac.asojuku.asolearning.exception.AccountLockedException;
import jp.ac.asojuku.asolearning.exception.AsoLearningSystemErrException;
import jp.ac.asojuku.asolearning.exception.LoginFailureException;
import jp.ac.asojuku.asolearning.param.RequestConst;
import jp.ac.asojuku.asolearning.param.RoleId;
import jp.ac.asojuku.asolearning.param.SessionConst;

/**
 * ログインサーブレット
 * @author nishino
 *
 */
@WebServlet(name="LoginServlet",urlPatterns={"/auth"})
public class LoginServlet extends BaseServlet {

	private final String DISPNO = "00001";
	@Override
	protected String getDisplayNo() {
		return DISPNO;
	}

	protected void doPostMain(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException, AsoLearningSystemErrException {
		Logger logger = LoggerFactory.getLogger(LoginServlet.class);
		LoginBo loginbo = new LoginBoImpl();

		//ログイン画面からユーザー情報とパスワードを取得する
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");

        //logger.trace("userName={} password={}",userName,password);

		LogonInfoDTO loginInfo;

		try {
			loginInfo = loginbo.login(userName,password);

			if( loginInfo == null ){
				//ログイン画面へエラーメッセージ通達
				fowardLoginErrDisp(request,resp,MessageProperty.LOGIN_ERR_LOGINERR);
				return;
			}

			logger.trace("login! userName={} ",userName);
			//ログイン情報を保存
			setLoginInfoToSession(request,loginInfo);
			//ログイン成功の場合はトップ画面へ戻る

			logger.info("ログインしました："+loginInfo.getName());
			if( RoleId.STUDENT.equals(loginInfo.getRoleId()) ){
				//画面転送（リダイレクト）
				resp.sendRedirect("st_dashboad");
			}else if( RoleId.TEACHER.equals(loginInfo.getRoleId()) ){
				//画面転送（リダイレクト）
				resp.sendRedirect("st_dashboad");
			}else{
				//画面転送（リダイレクト）
				resp.sendRedirect("st_dashboad");
			}

		} catch (LoginFailureException  e) {
			//ログイン画面へエラーメッセージ通達
			fowardLoginErrDisp(request,resp,MessageProperty.LOGIN_ERR_LOGINERR);
		} catch (AccountLockedException e) {
			//ログイン画面へエラーメッセージ通達
			fowardLoginErrDisp(request,resp,MessageProperty.LOGIN_LOCK);
		}


	}

	/**
	 * ログイン画面へ転送
	 * @param request
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 * @throws AsoLearningSystemErrException
	 */
	private void fowardLoginErrDisp(HttpServletRequest request, HttpServletResponse resp,String errMsgId) throws ServletException, IOException, AsoLearningSystemErrException{
		//エラーメッセージをセット
		String errMsg;

		errMsg = MessageProperty.getInstance().getProperty(errMsgId);

		request.setAttribute(RequestConst.LOGIN_ERR_MSG,errMsg );

		//画面転送
		RequestDispatcher rd = request.getRequestDispatcher(getJspDir()+"login.jsp");
		rd.forward(request, resp);

	}

	/**
	 * ログイン情報をセッションに保存する
	 * @param request
	 * @param loginInfo
	 */
	private void setLoginInfoToSession(HttpServletRequest request,LogonInfoDTO loginInfo){

		HttpSession session = request.getSession(false);

		if( session != null ){
			//セッションをいったん破棄
			session.invalidate();
		}

		//セッション再作成
		session = request.getSession(true);

		session.setAttribute(SessionConst.SESSION_LOGININFO, loginInfo);
	}

	@Override
	protected void doGetMain(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// login.jspを表示
		RequestDispatcher rd = req.getRequestDispatcher(getJspDir()+"st_taskList.jsp");
		rd.forward(req, resp);
	}

}
