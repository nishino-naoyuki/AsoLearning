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

	public List<TaskDto> getTaskListForUser(LogonInfoDTO user) throws AsoLearningSystemErrException;
}
