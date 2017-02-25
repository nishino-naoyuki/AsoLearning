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
 * パスワード変更画面表示
 * @author nishino
 *
 */
@WebServlet(name="ChangeNicknameStartServlet",urlPatterns={"/nicknamechangeinput"})
public class ChangeNicknameStartServlet extends BaseServlet {

	private final String DISPNO = "00301";
	@Override
	protected String getDisplayNo() {
		return DISPNO;
	}

	@Override
	protected void doGetMain(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		RequestDispatcher rd = req.getRequestDispatcher("view/st_nicknameChange.jsp");
		rd.forward(req, resp);
	}

}
