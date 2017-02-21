/**
 *
 */
package jp.ac.asojuku.asolearning.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jp.ac.asojuku.asolearning.bo.UserBo;
import jp.ac.asojuku.asolearning.bo.impl.UserBoImpl;
import jp.ac.asojuku.asolearning.config.AppSettingProperty;
import jp.ac.asojuku.asolearning.csv.model.UserCSV;
import jp.ac.asojuku.asolearning.err.ActionErrors;
import jp.ac.asojuku.asolearning.exception.AsoLearningSystemErrException;
import jp.ac.asojuku.asolearning.util.FileUtils;

/**
 * CSV処理を行うAJAX
 * @author nishino
 *
 */
@WebServlet(name="CreateUserCSVServlet",urlPatterns={"/userCSVProcess"})
@MultipartConfig(location="/tmp", maxFileSize=1048576)
public class CreateUserCSVServlet extends BaseServlet {

	Logger logger = LoggerFactory.getLogger(CreateUserCSVServlet.class);

	private final String DISPNO = "01001";
	@Override
	protected String getDisplayNo() {
		return DISPNO;
	}
	private ActionErrors errors;

	/* (非 Javadoc)
	 * @see jp.ac.asojuku.asolearning.servlet.BaseServlet#doPostMain(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doPostMain(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException, AsoLearningSystemErrException {

		errors = new ActionErrors();
		////////////////////////////////////
		//パラメータ取得
		String type = getStringParamFromPart(req,"rdoType");
		String uuid = getStringParamFromPart(req,"uuid");

		//////////////////////////////////////////////
		//アップロードされたファイルを取得
		Part part = req.getPart("file");
        String name = this.getFileName(part);

        //アップロードフォルダを作成
        String dir = getUploadDir(uuid);
		FileUtils.makeDir(dir);
		String path = dir + "/" + name;

        part.write(path);

        logger.trace("CSV fileuploaded! file={} type={}",path,type);

		//////////////////////////////////////////////
		//エラーチェック＆リスト取得
        UserBo userBo = new UserBoImpl();

        List<UserCSV> userList = userBo.checkForCSV(path, errors, type);
		HttpSession session = req.getSession(false);

		//////////////////////////////////////////////
		//登録処理

	}

	private String getUploadDir(String uuid) throws AsoLearningSystemErrException{
		String uploadDir = AppSettingProperty.getInstance().getUploadDirectory();

		return uploadDir+"/"+uuid;
	}

}
