/**
 *
 */
package jp.ac.asojuku.asolearning.judge;

import java.io.File;

import jp.ac.asojuku.asolearning.exception.IllegalJudgeFileException;
import jp.ac.asojuku.asolearning.util.FileUtils;

/**
 * javaのプログラムの判定を行うクラス
 * @author nishino
 *
 */
public class JavaProgramJudge implements Judge {
	private final String CHECK_EXT = "java";

	@Override
	public void judge(String dirName, String fileName) throws IllegalJudgeFileException {

		///////////////////////////////////////
		//ファイルの検査
		if( !FileUtils.checkFileExt(new File(dirName+"/"+fileName), CHECK_EXT)){
			throw new IllegalJudgeFileException();
		}

		///////////////////////////////////////
		//コンパイルを行う

		///////////////////////////////////////
		//ソースの品質を判定

		///////////////////////////////////////
		//実行を行う
	}

}
