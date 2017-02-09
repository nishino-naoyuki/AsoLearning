package jp.ac.asojuku.asolearning.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jp.ac.asojuku.asolearning.dto.LogonInfoDTO;
import jp.ac.asojuku.asolearning.exception.AsoLearningSystemErrException;
import jp.ac.asojuku.asolearning.param.SessionConst;

public abstract class BaseServlet extends HttpServlet {

	/* (非 Javadoc)
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		//////////////////////////
		//TODO:権限チェック

		try {
			doGetMain(req,resp);
		} catch (AsoLearningSystemErrException e) {
			fowardSystemError(req,resp,e);
		}
	}

	/* (非 Javadoc)
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		//////////////////////////
		//TODO:権限チェック

		try {
			doPostMain(req,resp);
		} catch (AsoLearningSystemErrException e) {
			fowardSystemError(req,resp,e);
		}
	}

	/**
	 * システムエラー画面を表示する
	 * @param req
	 * @param resp
	 * @param e
	 * @throws ServletException
	 * @throws IOException
	 */
	private void fowardSystemError(HttpServletRequest req,HttpServletResponse resp,AsoLearningSystemErrException e) throws ServletException, IOException{

		Logger logger = LoggerFactory.getLogger(this.getClass().getName());

		logger.error("システムエラーが発生しました:",e);
		RequestDispatcher rd = req.getRequestDispatcher("view/error/systemerror.jsp");
		rd.forward(req, resp);
	}


	protected void doGetMain(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException,AsoLearningSystemErrException
	{
		//TODO:405エラーチック名画面へ遷移
	}

	protected void doPostMain(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException,AsoLearningSystemErrException
	{
		//TODO:405エラーチック名画面へ遷移

	}

	/**
	 * セッションからログイン情報を取得する
	 * 存在しない場合はNULLが返る
	 * @param req
	 * @return
	 */
	protected LogonInfoDTO getUserInfoDtoFromSession(HttpServletRequest req){

		//セッションからログイン情報を取得
		HttpSession session = req.getSession(false);

		if( session == null ){
			return null;
		}

		LogonInfoDTO loginInfo =
				(LogonInfoDTO)session.getAttribute(SessionConst.SESSION_LOGININFO);

		return loginInfo;
	}

	protected abstract String getDisplayNo();
}
