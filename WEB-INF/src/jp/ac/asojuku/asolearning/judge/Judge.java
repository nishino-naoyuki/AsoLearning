/**
 *
 */
package jp.ac.asojuku.asolearning.judge;

import jp.ac.asojuku.asolearning.exception.AsoLearningSystemErrException;
import jp.ac.asojuku.asolearning.exception.IllegalJudgeFileException;
import jp.ac.asojuku.asolearning.json.JudgeResultJson;

/**
 * 判定クラスのインターフェース
 * @author nishino
 *
 */
public interface Judge {

	/**
	 * 判定処理
	 * @param dirName
	 * @param fileName
	 * @throws IllegalJudgeFileException
	 * @throws AsoLearningSystemErrException
	 */
	public JudgeResultJson judge(String dirName, String fileName) throws IllegalJudgeFileException, AsoLearningSystemErrException;
}
