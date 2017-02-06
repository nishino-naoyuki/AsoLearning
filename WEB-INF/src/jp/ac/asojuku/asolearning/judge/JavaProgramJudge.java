/**
 *
 */
package jp.ac.asojuku.asolearning.judge;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jp.ac.asojuku.asolearning.config.AppSettingProperty;
import jp.ac.asojuku.asolearning.exception.AsoLearningSystemErrException;
import jp.ac.asojuku.asolearning.exception.IllegalJudgeFileException;
import jp.ac.asojuku.asolearning.json.JudgeResultJson;
import jp.ac.asojuku.asolearning.util.FileUtils;

/**
 * javaのプログラムの判定を行うクラス
 * @author nishino
 *
 */
public class JavaProgramJudge implements Judge {

	Logger logger = LoggerFactory.getLogger(JavaProgramJudge.class);
	private final String CHECK_EXT = "java";

	@Override
	public JudgeResultJson judge(String dirName, String fileName) throws IllegalJudgeFileException, AsoLearningSystemErrException {
		JudgeResultJson json = new JudgeResultJson();

		///////////////////////////////////////
		//ファイルの検査
		if( !FileUtils.checkFileExt(new File(dirName+"/"+fileName), CHECK_EXT)){
			throw new IllegalJudgeFileException();
		}

		int ret;

		try{
			///////////////////////////////////////
			//シェルの実行（コンパイルと実行と品質解析）
			String shellPath = AppSettingProperty.getInstance().getShellPath();
			ProcessBuilder pb = new ProcessBuilder(shellPath);
			Process process = pb.start();

			logger.trace("バッチ実行開始：");

			ret = process.waitFor();

			logger.trace("バッチ実行終了：" + ret);

			///////////////////////////////////////
			//ソースの品質情報をパース


		}catch (InterruptedException | IOException e) {

		}

		return json;
	}

}
