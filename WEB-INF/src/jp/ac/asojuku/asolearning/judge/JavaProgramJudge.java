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
			String resultDir = dirName+"/result";//AppSettingProperty.getInstance().getResultDirectory();
			String classDir = dirName+"/classes";
			//必要なフォルダを作成
			makeDirs(classDir,resultDir);

			//実行クラス名（＝ファイル名の拡張子を除いたもの）を取得
			String className = FileUtils.getPreffix(fileName);

			ProcessBuilder pb = new ProcessBuilder(shellPath,dirName,fileName,resultDir,className);
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

	private void makeDirs(String classDir,String resultDir){

		//classとresultのフォルダ名を作成する
		FileUtils.makeDir(classDir);
		FileUtils.makeDir(resultDir);

	}

}
