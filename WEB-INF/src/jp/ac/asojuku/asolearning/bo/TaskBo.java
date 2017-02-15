/**
 *
 */
package jp.ac.asojuku.asolearning.bo;

import java.util.List;

import jp.ac.asojuku.asolearning.dto.LogonInfoDTO;
import jp.ac.asojuku.asolearning.dto.TaskDto;
import jp.ac.asojuku.asolearning.dto.TaskPublicDto;
import jp.ac.asojuku.asolearning.dto.TaskTestCaseDto;
import jp.ac.asojuku.asolearning.exception.AsoLearningSystemErrException;
import jp.ac.asojuku.asolearning.json.JudgeResultJson;

/**
 * 課題インターフェース
 * @author nishino
 *
 */
public interface TaskBo {

	/**
	 * 新規追加
	 *
	 * @param user
	 * @param dto
	 * @param testCaseList
	 * @param taskPublicList
	 * @throws AsoLearningSystemErrException
	 */
	public void insert(LogonInfoDTO user,
			TaskDto dto,
			List<TaskTestCaseDto> testCaseList,
			List<TaskPublicDto> taskPublicList) throws AsoLearningSystemErrException;

	/**
	 * 判定処理を行う
	 * @param taskId
	 * @param user
	 * @param dirName
	 * @param fileName
	 * @return
	 * @throws AsoLearningSystemErrException
	 */
	public JudgeResultJson judgeTask(Integer taskId, LogonInfoDTO user,String dirName,String fileName) throws AsoLearningSystemErrException;

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
