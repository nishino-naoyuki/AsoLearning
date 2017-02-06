/**
 *
 */
package jp.ac.asojuku.asolearning.servlet;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jp.ac.asojuku.asolearning.bo.TaskBo;
import jp.ac.asojuku.asolearning.bo.impl.TaskBoImpl;
import jp.ac.asojuku.asolearning.dto.LogonInfoDTO;
import jp.ac.asojuku.asolearning.exception.AsoLearningSystemErrException;
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

	private final String DISPNO = "display00102";
	@Override
	protected String getDisplayNo() {
		return DISPNO;
	}

	@Override
	protected void doPostMain(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException, AsoLearningSystemErrException {

		//////////////////////////////////////////////
		//アップロードされたファイルを取得
		Part part = req.getPart("file");
        String name = this.getFileName(part);


		//////////////////////////////////////////////
        //ファイル名を決定する
		//セッションからログイン情報を取得
		LogonInfoDTO loginInfo = getUserInfoDtoFromSession(req);

		//ファイル名を決定
		String fname = getJudgeFileName(loginInfo,name);

        part.write(getServletContext().getRealPath("/WEB-INF/uploaded") + "/" + fname);

        logger.trace("fileuploaded! user={} file={}",loginInfo.getName(),fname);

		//////////////////////////////////////////////
        //判定処理を行う
        TaskBo taskBo = new TaskBoImpl();

        taskBo.judgeTask("/WEB-INF/uploaded", fname);

        OutputStream out = null;

        resp.setContentType("application/json; charset=utf-8");
        out = resp.getOutputStream();
        out.write("[{\"test\":\"aaaa\"}]".getBytes());
        out.flush();

       out.close();

        //resp.setContentType("application/json; charset=utf-8");
        ///resp.setHeader("Access-Control-Allow-Origin", "http://localhost"); //ここは個人のサーバ環境によって異なる．
		//PrintWriter out = resp.getWriter();
		//int numbers[] = {15,16,23,29,37,39};
		// out.println("[{test:\"aaaa\"}]");

	}

	/**
	 * アップロードされたファイル名をヘッダ情報より取得する
	 * @param part
	 * @return
	 */
	private String getFileName(Part part) {
        String name = null;
        for (String dispotion : part.getHeader("Content-Disposition").split(";")) {
            if (dispotion.trim().startsWith("filename")) {
                name = dispotion.substring(dispotion.indexOf("=") + 1).replace("\"", "").trim();
                name = name.substring(name.lastIndexOf("\\") + 1);
                break;
            }
        }
        return name;
    }

	/**
	 * アップロードファイル名は以下のフォーマットとする
	 * ユーザー名+アップロードファイル名+現在時刻（ミリ秒まで）
	 *
	 * @param loginInfo
	 * @param fileName
	 * @return
	 */
	private String getJudgeFileName(LogonInfoDTO loginInfo,String fileName){
		String judgeFileName = "";

		String timeStr =
				TimestampUtil.formattedTimestamp(TimestampUtil.current(), "yyyyMMddHHmmssSSS");

		return loginInfo.getName()+timeStr+"_"+fileName;
	}
}
