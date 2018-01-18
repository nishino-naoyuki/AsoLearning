/**
 *
 */
package jp.ac.asojuku.asolearning.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.ac.asojuku.asolearning.bo.UserBo;
import jp.ac.asojuku.asolearning.bo.impl.UserBoImpl;
import jp.ac.asojuku.asolearning.dto.LogonInfoDTO;
import jp.ac.asojuku.asolearning.dto.UserDto;
import jp.ac.asojuku.asolearning.exception.AsoLearningSystemErrException;
import jp.ac.asojuku.asolearning.param.RequestConst;

/**
 * ユーザーの登録処理「
 * @author nishino
 *
 */
@WebServlet(name="CreateUserInsertServlet",urlPatterns={"/tc_insertUser"})
public class CreateUserInsertServlet extends BaseServlet {

	private final String DISPNO = "00803";
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

		////////////////////////////////////
		//セッションから情報取得
		HttpSession session = req.getSession(false);

		UserDto dto = null;

		if( session != null ){
			dto = (UserDto)session.getAttribute(RequestConst.REQUEST_USER_DTO);
		}

		//	セッションに情報が無い場合はシステムエラー
		if( dto == null ){
			throw new AsoLearningSystemErrException("セッションから課題情報が取得できません");
		}

		////////////////////////////////////
		//セッションからログイン情報取得
		LogonInfoDTO loginInfo = getUserInfoDtoFromSession(req);

		////////////////////////////////////
		//DBへセット
		UserBo task = new UserBoImpl();

		task.insert(dto,loginInfo);


		////////////////////////////////////
		//完了画面減りダイレクト（セッション情報はリダイレクト先で消す）
		resp.sendRedirect("tc_insertUserComplete");
	}

}
