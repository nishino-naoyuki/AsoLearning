/**
 *
 */
package jp.ac.asojuku.asolearning.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.ac.asojuku.asolearning.exception.AsoLearningSystemErrException;

/**
 * @author nishino
 *
 */
@WebServlet(name="CreateUserCSVServlet",urlPatterns={"/userCSVProcess"})
public class CreateUserCSVServlet extends BaseServlet {

	private final String DISPNO = "01001";
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

		doGetMain(req, resp);
	}
}
