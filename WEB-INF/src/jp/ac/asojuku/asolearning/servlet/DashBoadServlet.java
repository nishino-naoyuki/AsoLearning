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

/**
 * @author nishino
 *
 */
@WebServlet(name="DashBoadServlet",urlPatterns={"/st_dashboad"})
public class DashBoadServlet extends BaseServlet {

	private final String DISPNO = "00001";
	@Override
	protected String getDisplayNo() {
		return DISPNO;
	}

	@Override
	protected void doGetMain(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// tc_dashboad.jspを表示
		RequestDispatcher rd = req.getRequestDispatcher("view/tc_dashboad.jsp");
		rd.forward(req, resp);
	}
}
