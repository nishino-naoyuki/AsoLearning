package jp.ac.asojuku.asolearning.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jp.ac.asojuku.asolearning.exception.AsoLearningSystemErrException;

public class BaseServlet extends HttpServlet {

	/* (非 Javadoc)
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

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
		RequestDispatcher rd = req.getRequestDispatcher("view/systemerror.jsp");
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
}
