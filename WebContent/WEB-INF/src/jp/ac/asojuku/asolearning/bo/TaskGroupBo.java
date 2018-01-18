/**
 *
 */
package jp.ac.asojuku.asolearning.bo;

import java.util.List;

import jp.ac.asojuku.asolearning.dto.TaskGroupDto;
import jp.ac.asojuku.asolearning.exception.AsoLearningSystemErrException;

/**
 * タスクグループを扱う
 * @author nishino
 *
 */
public interface TaskGroupBo {

	/**
	 * グループの一覧を取得する
	 *
	 * @param groupName
	 * @return
	 * @throws AsoLearningSystemErrException
	 */
	public List<TaskGroupDto> getTaskGroupList(String groupName) throws AsoLearningSystemErrException;

	/**
	 * 学科を指定してグループの一覧を取得する
	 * 指定した学科に対して公開している課題グループのみ取得
	 *
	 * @param courseId
	 * @return
	 * @throws AsoLearningSystemErrException
	 */
	public List<TaskGroupDto> getTaskGroupListByCourseId(Integer courseId) throws AsoLearningSystemErrException;
}
