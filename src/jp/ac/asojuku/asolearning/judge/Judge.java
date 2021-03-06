/**
 *
 */
package jp.ac.asojuku.asolearning.judge;

import java.sql.Connection;

import jp.ac.asojuku.asolearning.entity.TaskTblEntity;
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
	 *
	 * @param taskEntity 課題情報
	 * @param dirName 課題の提出フォルダ
	 * @param fileName 課題の提出ファイルID
	 * @param userId ユーザーID
	 * @param con 接続済みのコネクション
	 * @return
	 * @throws IllegalJudgeFileException
	 * @throws AsoLearningSystemErrException
	 */
	public JudgeResultJson judge(TaskTblEntity taskEntity,String dirName, String fileName,int userId,Connection con) throws IllegalJudgeFileException, AsoLearningSystemErrException;
}
