/**
 *
 */
package jp.ac.asojuku.asolearning.servlet;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jp.ac.asojuku.asolearning.bo.AvatarBo;
import jp.ac.asojuku.asolearning.bo.impl.AvatarBoImpl;
import jp.ac.asojuku.asolearning.dto.AvatarSettingDto;
import jp.ac.asojuku.asolearning.exception.AsoLearningSystemErrException;
import jp.ac.asojuku.asolearning.param.AvatarKind;

/**
 * @author nishino
 *
 */
@WebServlet(name="UpdateAvatarServlet",urlPatterns={"/updateavatar"})
public class UpdateAvatarServlet extends BaseServlet {

	Logger logger = LoggerFactory.getLogger(UpdateAvatarServlet.class);

	private final String DISPNO = "01501";
	@Override
	protected String getDisplayNo() {
		return DISPNO;
	}

	@Override
	protected void doGetMain(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException, AsoLearningSystemErrException {

        OutputStream out = null;

        try{
			//////////////////////////////
			//アバターの設定値を取得する
			AvatarSettingDto avatarDto = new AvatarSettingDto();

			avatarDto.setAvatarDto(AvatarKind.BACK_HAIR, getIntParam("ava_backhair",req));
			avatarDto.setAvatarDto(AvatarKind.BODY, getIntParam("ava_body",req));
			avatarDto.setAvatarDto(AvatarKind.FACE, getIntParam("ava_face",req));
			avatarDto.setAvatarDto(AvatarKind.EAR, getIntParam("ava_ear",req));
			avatarDto.setAvatarDto(AvatarKind.MAYU, getIntParam("ava_mayu",req));
			avatarDto.setAvatarDto(AvatarKind.EYE, getIntParam("ava_eye",req));
			avatarDto.setAvatarDto(AvatarKind.NOSE, getIntParam("ava_nose",req));
			avatarDto.setAvatarDto(AvatarKind.MOUTH, getIntParam("ava_mouth",req));
			avatarDto.setAvatarDto(AvatarKind.FRONT_HAIR, getIntParam("ava_fronthair",req));
			avatarDto.setAvatarDto(AvatarKind.ACC1, getIntParam("ava_acc1",req));
			avatarDto.setAvatarDto(AvatarKind.ACC2, getIntParam("ava_acc2",req));

			//////////////////////////////
			//アバター設定
			AvatarBo avatarBo = new AvatarBoImpl();

			avatarBo.updateAvatar(getUserInfoDtoFromSession(req), avatarDto);

			//////////////////////////////////////////////
	        //出力処理を行う
			String errMsg = "処理が正常に終了しました";
	        resp.setContentType("text/plain; charset=utf-8");
	        out = resp.getOutputStream();
	        out.write(errMsg.getBytes());
	        out.flush();
        }finally{
        	if(out != null){
        		out.close();
        	}
        }
	}
}
