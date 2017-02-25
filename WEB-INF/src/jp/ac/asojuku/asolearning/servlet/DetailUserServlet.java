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

import jp.ac.asojuku.asolearning.bo.UserBo;
import jp.ac.asojuku.asolearning.bo.impl.UserBoImpl;
import jp.ac.asojuku.asolearning.dto.UserDetailDto;
import jp.ac.asojuku.asolearning.exception.AsoLearningSystemErrException;
import jp.ac.asojuku.asolearning.param.RequestConst;

/**
 * @author nishino
 *
 */
@WebServlet(name="DetailUserServlet",urlPatterns={"/userDetail"})
public class DetailUserServlet extends BaseServlet {

	private final String DISPNO = "00901";
	@Override
	protected String getDisplayNo() {
		return DISPNO;
	}

	/* (非 Javadoc)
	 * @see jp.ac.asojuku.asolearning.servlet.BaseServlet#doPostMain(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doGetMain(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException, AsoLearningSystemErrException {

		/////////////////////////////////
		//ユーザーIDを取得
		Integer userId = this.getIntParam("userId", req);

		/////////////////////////////////
		//ユーザー情報を取得
		UserBo userBo = new UserBoImpl();

		UserDetailDto userDetail = userBo.detail(userId);
		req.setAttribute(RequestConst.REQUEST_USER_DETAIL, userDetail);

		/////////////////////////////////
		//画面遷移
		RequestDispatcher rd = null;
		rd = req.getRequestDispatcher("view/tc_detailUser.jsp");
		rd.forward(req, resp);


	}
}
