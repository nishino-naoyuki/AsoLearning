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

import jp.ac.asojuku.asolearning.bo.InfomationBo;
import jp.ac.asojuku.asolearning.bo.impl.InfomationBoImpl;
import jp.ac.asojuku.asolearning.dto.InfomationListDto;
import jp.ac.asojuku.asolearning.dto.LogonInfoDTO;
import jp.ac.asojuku.asolearning.exception.AsoLearningSystemErrException;
import jp.ac.asojuku.asolearning.param.RequestConst;

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
	protected void doGetMain(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, AsoLearningSystemErrException {

		////////////////////////////////////////
		//セッションからログイン情報を取得
		LogonInfoDTO loginInfo = getUserInfoDtoFromSession(req);

		InfomationBo infoBo = new InfomationBoImpl();

		InfomationListDto dto = infoBo.get(loginInfo);

		//リクエストにセット
		req.setAttribute(RequestConst.REQUEST_INFO_DTO, dto);

		// tc_dashboad.jspを表示
		RequestDispatcher rd = req.getRequestDispatcher("view/tc_dashboad.jsp");
		rd.forward(req, resp);
	}
}
