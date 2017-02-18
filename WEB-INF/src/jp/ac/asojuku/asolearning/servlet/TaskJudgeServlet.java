/**
 *
 */
package jp.ac.asojuku.asolearning.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import jp.ac.asojuku.asolearning.bo.TaskBo;
import jp.ac.asojuku.asolearning.bo.impl.TaskBoImpl;
import jp.ac.asojuku.asolearning.config.AppSettingProperty;
import jp.ac.asojuku.asolearning.dto.LogonInfoDTO;
import jp.ac.asojuku.asolearning.exception.AsoLearningSystemErrException;
import jp.ac.asojuku.asolearning.json.JudgeResultJson;
import jp.ac.asojuku.asolearning.util.FileUtils;
import jp.ac.asojuku.asolearning.util.TimestampUtil;

/**
 * 課題判定処理
 * @author nishino
 *
 */
@WebServlet(name="TaskJudgeServlet",urlPatterns={"/judgetask"})
@MultipartConfig(location="/tmp", maxFileSize=1048576)
public class TaskJudgeServlet extends BaseServlet {

	Logger logger = LoggerFactory.getLogger(LoginServlet.class);

	private final String DISPNO = "00102";
	@Override
	protected String getDisplayNo() {
		return DISPNO;
	}

	@Override
	protected void doPostMain(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException, AsoLearningSystemErrException {

        OutputStream out = null;
        try{
			//////////////////////////////////////////////
			//課題IDを取得
	        Integer taskId = getTaskId(req);
			//////////////////////////////////////////////
			//アップロードされたファイルを取得
			Part part = req.getPart("file");
	        String name = this.getFileName(part);

			//////////////////////////////////////////////
	        //ファイル名を決定する
			//セッションからログイン情報を取得
			LogonInfoDTO loginInfo = getUserInfoDtoFromSession(req);

			//ファイル名を決定
			String dir = getJudgeDirName(loginInfo);
			//フォルダを作成する
			FileUtils.makeDir(dir);

	        part.write(dir + "/" + name);

	        logger.trace("fileuploaded! user={} file={}",loginInfo.getName(),dir + "/" +name);

			//////////////////////////////////////////////
	        //判定処理を行う
	        TaskBo taskBo = new TaskBoImpl();

	        JudgeResultJson json = taskBo.judgeTask(taskId,loginInfo,dir, name);
	        ObjectMapper mapper = new ObjectMapper();
	        String jsonString = mapper.writeValueAsString(json);

	        logger.trace("jsonString:{}",jsonString);

			//////////////////////////////////////////////
	        //JSON出力処理を行う
	        resp.setContentType("application/json; charset=utf-8");
	        out = resp.getOutputStream();
	        out.write(jsonString.getBytes());
	        out.flush();
		} catch (IllegalStateException e) {
			logger.error("IllegalStateException：",e);
		} catch (ServletException e) {
			logger.error("ServletException：",e);
        }finally{
        	if(out != null){
        		out.close();
        	}
        }

        //resp.setContentType("application/json; charset=utf-8");
        ///resp.setHeader("Access-Control-Allow-Origin", "http://localhost"); //ここは個人のサーバ環境によって異なる．
		//PrintWriter out = resp.getWriter();
		//int numbers[] = {15,16,23,29,37,39};
		// out.println("[{test:\"aaaa\"}]");

	}

	/**
	 * アップロードディレクトリ名は以下のフォーマットとする
	 * ユーザー名+アップロードファイル名+現在時刻（ミリ秒まで）
	 *
	 * @param loginInfo
	 * @param fileName
	 * @return
	 * @throws AsoLearningSystemErrException
	 */
	private String getJudgeDirName(LogonInfoDTO loginInfo) throws AsoLearningSystemErrException{
		String uploadDir = AppSettingProperty.getInstance().getUploadDirectory();

		String timeStr =
				TimestampUtil.formattedTimestamp(TimestampUtil.current(), "yyyyMMddHHmmssSSS");

		return uploadDir +"/"+ loginInfo.getName()+"/"+timeStr;
	}

	/**
	 * 課題IDを取得する
	 * @param req
	 * @return
	 * @throws AsoLearningSystemErrException
	 * @throws ServletException
	 * @throws IOException
	 * @throws IllegalStateException
	 */
	private Integer getTaskId(HttpServletRequest req) throws AsoLearningSystemErrException, IllegalStateException, IOException, ServletException{
		Integer taskId = null;

		Part part;

		part = req.getPart("taskid");
		String contentType = part.getContentType();

        if ( contentType == null) {
            try(InputStream inputStream = part.getInputStream()) {
                BufferedReader bufReader = new BufferedReader(new InputStreamReader(inputStream));
                taskId =
                		Integer.parseInt( bufReader.lines().collect(Collectors.joining()));

    		}catch( NumberFormatException e){
    			logger.trace("taskIdが指定されていません",e);
    			taskId = null;
            } catch (IOException e) {
            	throw new AsoLearningSystemErrException(e);
            }
        }



		return taskId;
	}

}
