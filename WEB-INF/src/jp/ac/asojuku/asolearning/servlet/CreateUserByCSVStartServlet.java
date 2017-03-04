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

import jp.ac.asojuku.asolearning.exception.AsoLearningSystemErrException;

/**
 * CSV登録画面表示
 *
 * @author nishino
 *
 */
@WebServlet(name="CreateUserByCSVStartServlet",urlPatterns={"/csvEntry"})
public class CreateUserByCSVStartServlet extends BaseServlet {

	private final String DISPNO = "01001";
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

		//画面遷移
		RequestDispatcher rd = req.getRequestDispatcher("view/tc_createUserCSV.jsp");
		rd.forward(req, resp);

	}
	/* (非 Javadoc)
	 * @see jp.ac.asojuku.asolearning.servlet.BaseServlet#doPostMain(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doPostMain(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException, AsoLearningSystemErrException {

		doGetMain(req, resp);
	}

}
