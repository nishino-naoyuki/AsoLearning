/**
 *
 */
package jp.ac.asojuku.asolearning.judge;

import jp.ac.asojuku.asolearning.exception.IllegalJudgeFileException;

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
	 */
	public void judge(String dirName, String fileName) throws IllegalJudgeFileException;
}
