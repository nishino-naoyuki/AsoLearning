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
@WebServlet(name="LoginStartServlet",urlPatterns={"/login"})
public class LoginStartServlet extends BaseServlet {

	@Override
	protected void doGetMain(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// login.jspを表示
		RequestDispatcher rd = req.getRequestDispatcher("view/login.jsp");
		rd.forward(req, resp);
	}

}
