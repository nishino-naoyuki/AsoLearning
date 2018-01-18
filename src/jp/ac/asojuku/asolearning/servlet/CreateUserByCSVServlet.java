/**
 *
 */
package jp.ac.asojuku.asolearning.servlet;

import java.io.IOException;
import java.io.OutputStream;
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

import com.fasterxml.jackson.databind.ObjectMapper;

import jp.ac.asojuku.asolearning.bo.UserBo;
import jp.ac.asojuku.asolearning.bo.impl.UserBoImpl;
import jp.ac.asojuku.asolearning.config.AppSettingProperty;
import jp.ac.asojuku.asolearning.csv.model.UserCSV;
import jp.ac.asojuku.asolearning.dto.CSVProgressDto;
import jp.ac.asojuku.asolearning.err.ActionError;
import jp.ac.asojuku.asolearning.err.ActionErrors;
import jp.ac.asojuku.asolearning.exception.AsoLearningSystemErrException;
import jp.ac.asojuku.asolearning.param.SessionConst;
import jp.ac.asojuku.asolearning.util.FileUtils;

/**
 * CSV処理を行うAJAX
 * @author nishino
 *
 */
@WebServlet(name="CreateUserByCSVServlet",urlPatterns={"/userCSVProcess"})
@MultipartConfig(location="/tmp", maxFileSize=1048576)
public class CreateUserByCSVServlet extends BaseServlet {

	Logger logger = LoggerFactory.getLogger(CreateUserByCSVServlet.class);

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


        OutputStream out = null;
        try{
        	out = resp.getOutputStream();
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

			if( errors.isHasErr() ){
				outputErrorResult(out,resp);
				return;
			}

			//////////////////////////////////////////////
			//登録処理
			userBo.insertByCSV(userList, uuid, session, type);

			//////////////////////////////////////////////
	        //JSON出力処理を行う
			outputResult(out,session,resp,uuid);

			//セッションは不要になったので削除
			session.removeAttribute(uuid+SessionConst.SESSION_CSV_PROGRESS);

        }finally{
        	if(out != null){
        		out.close();
        	}
        }

	}

	private void outputResult(OutputStream out,HttpSession session,HttpServletResponse resp,String uuid) throws IOException{

		CSVProgressDto progress = (CSVProgressDto)session.getAttribute(uuid+SessionConst.SESSION_CSV_PROGRESS);

		ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(progress);

        logger.trace("jsonString:{}",jsonString);

        resp.setContentType("application/json; charset=utf-8");
        out.write(jsonString.getBytes());
        out.flush();

	}

	private void outputErrorResult(OutputStream out,HttpServletResponse resp) throws IOException{
		CSVProgressDto progress = new CSVProgressDto();
		StringBuffer sb = new StringBuffer();

		for( ActionError error : errors.getList() ){
			sb.append( error.getMessage() );
			sb.append("\n");
		}
		progress.setErrorMsg(sb.toString());

		ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(progress);

        logger.trace("jsonString:{}",jsonString);

        resp.setContentType("application/json; charset=utf-8");
        out.write(jsonString.getBytes());
        out.flush();
	}

	private String getUploadDir(String uuid) throws AsoLearningSystemErrException{
		String uploadDir = AppSettingProperty.getInstance().getUploadDirectory();

		return uploadDir+"/"+uuid;
	}

}
