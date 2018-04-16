/**
 *
 */
package jp.ac.asojuku.asolearning.bo;

import java.util.List;

import jp.ac.asojuku.asolearning.condition.SearchTaskCondition;
import jp.ac.asojuku.asolearning.dto.LogonInfoDTO;
import jp.ac.asojuku.asolearning.dto.TaskDto;
import jp.ac.asojuku.asolearning.dto.TaskPublicDto;
import jp.ac.asojuku.asolearning.exception.AsoLearningSystemErrException;
import jp.ac.asojuku.asolearning.json.JudgeResultJson;
import jp.ac.asojuku.asolearning.json.TaskSearchResultJson;

/**
 * 課題インターフェース
 * @author nishino
 *
 */
public interface TaskBo {

	/**
	 * 課題の一括更新
	 * @param taskIdList
	 * @param taskPublicList
	 * @throws AsoLearningSystemErrException
	 */
	public void updatePublicState(List<String> taskIdList,List<TaskPublicDto> taskPublicList) throws AsoLearningSystemErrException;
	/**
	 * 課題の検索処理
	 *
	 * @param condition
	 * @return
	 * @throws AsoLearningSystemErrException
	 */
	public List<TaskSearchResultJson> search(SearchTaskCondition condition) throws AsoLearningSystemErrException;

	/**
	 * 新規追加
	 *
	 * @param user
	 * @param dto
	 * @throws AsoLearningSystemErrException
	 */
	public void insert(LogonInfoDTO user,
			TaskDto dto) throws AsoLearningSystemErrException;

	/**
	 * 更新
	 *
	 * @param user
	 * @param dto
	 * @param testCaseList
	 * @param taskPublicList
	 * @throws AsoLearningSystemErrException
	 */
	public void update(LogonInfoDTO user,
			TaskDto dto) throws AsoLearningSystemErrException;

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

	/**
	 * 課題の詳細情報を表示する
	 * @param String name
	 * @return
	 * @throws AsoLearningSystemErrException
	 */
	public TaskDto getTaskDetailForName(String name) throws AsoLearningSystemErrException;

	/**
	 * 学科を指定して課題一覧を取得する
	 * （その学科で表示する課題のみ表示する）
	 * @param couseId
	 * @param grade
	 * @return
	 * @throws AsoLearningSystemErrException
	 */
	public List<TaskDto> getTaskListByCouseId(Integer couseId,Integer grade) throws AsoLearningSystemErrException;

	/**
	 * 課題グループのIDを指定して課題一覧を取得する
	 * （その学科で表示する課題のみ表示する）
	 * @param couseId
	 * @return
	 * @throws AsoLearningSystemErrException
	 */
	public List<TaskDto> getTaskListByTaskGrpId(Integer taskGrpId) throws AsoLearningSystemErrException;

	/**
	 * 課題をIDを指定して取得する
	 *
	 * @param taskId
	 * @param user
	 * @return
	 * @throws AsoLearningSystemErrException
	 */
	TaskDto getTaskDetailById(Integer taskId, LogonInfoDTO user) throws AsoLearningSystemErrException;

}
