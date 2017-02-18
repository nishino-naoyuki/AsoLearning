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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jp.ac.asojuku.asolearning.exception.AsoLearningSystemErrException;

/**
 * ランキングサーブレット
 * @author nishino
 *
 */
@WebServlet(name="RankingServlet",urlPatterns={"/ranking"})
public class RankingServlet extends BaseServlet {

	Logger logger = LoggerFactory.getLogger(RankingServlet.class);

	private final String DISPNO = "00201";
	@Override
	protected String getDisplayNo() {
		return DISPNO;
	}
	@Override
	protected void doGetMain(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException, AsoLearningSystemErrException {

		//////////////////////////////
		//パラメータ取得（ページング）

		//////////////////////////////
		//学科一覧を取得

		//////////////////////////////
		//ランキング情報を取得

		//////////////////////////////
		//画面転送
		RequestDispatcher rd = req.getRequestDispatcher("view/st_ranking.jsp");
		rd.forward(req, resp);
	}
	@Override
	protected void doPostMain(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException, AsoLearningSystemErrException {

		doGetMain(req, resp);
	}


}
