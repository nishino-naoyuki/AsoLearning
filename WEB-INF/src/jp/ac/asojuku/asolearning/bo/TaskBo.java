/**
 *
 */
package jp.ac.asojuku.asolearning.bo;

import java.util.List;

import jp.ac.asojuku.asolearning.dto.LogonInfoDTO;
import jp.ac.asojuku.asolearning.dto.TaskDto;
import jp.ac.asojuku.asolearning.exception.AsoLearningSystemErrException;

/**
 * 課題インターフェース
 * @author nishino
 *
 */
public interface TaskBo {

	/**
	 * 判定処理を行う
	 * @param dirName
	 * @param fileName
	 * @throws AsoLearningSystemErrException
	 */
	public void judgeTask(String dirName,String fileName) throws AsoLearningSystemErrException;

	/**
	 * 課題の一覧を取得する
	 * @param user　表示対象のユーザー情報
	 * @return
	 * @throws AsoLearningSystemErrException
	 */
	public List<TaskDto> getTaskListForUser(LogonInfoDTO user) throws AsoLearningSystemErrException;

	/**
	 * 課題の詳細情報を表示する
	 * @param taskId
	 * @param user
	 * @return
	 * @throws AsoLearningSystemErrException
	 */
	public TaskDto getTaskDetailForUser(Integer taskId,LogonInfoDTO user) throws AsoLearningSystemErrException;
}
