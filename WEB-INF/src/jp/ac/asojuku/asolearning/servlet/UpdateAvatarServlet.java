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

import jp.ac.asojuku.asolearning.bo.AvatarBo;
import jp.ac.asojuku.asolearning.bo.impl.AvatarBoImpl;
import jp.ac.asojuku.asolearning.dto.AvatarPartsDto;
import jp.ac.asojuku.asolearning.exception.AsoLearningSystemErrException;
import jp.ac.asojuku.asolearning.param.RequestConst;

/**
 * アバター画面
 * @author nishino
 *
 */
@WebServlet(name="UpdateAvatarServlet",urlPatterns={"/updateavatar"})
public class UpdateAvatarServlet extends BaseServlet {

	Logger logger = LoggerFactory.getLogger(TaskServlet.class);

	private final String DISPNO = "01501";
	@Override
	protected String getDisplayNo() {
		return DISPNO;
	}

	@Override
	protected void doGetMain(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException, AsoLearningSystemErrException {

		doPostMain(req,resp);
	}

	@Override
	protected void doPostMain(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException, AsoLearningSystemErrException {

		//////////////////////////////
		//アバター取得
		AvatarBo avatarBo = new AvatarBoImpl();

		AvatarPartsDto avatorDto = avatarBo.getParts( getUserInfoDtoFromSession(req) );

		//////////////////////////////
		//画面転送
		req.setAttribute(RequestConst.REQUEST_AVATAR_DTO, avatorDto);
		RequestDispatcher rd = req.getRequestDispatcher("view/st_updateAvatar.jsp");
		rd.forward(req, resp);
	}
}
