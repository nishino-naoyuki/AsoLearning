/**
 *
 */
package jp.ac.asojuku.asolearning.bo;

import jp.ac.asojuku.asolearning.dto.TaskResultDetailDto;
import jp.ac.asojuku.asolearning.exception.AsoLearningSystemErrException;

/**
 * 課題の結果BO
 *
 * @author nishino
 *
 */
public interface ResultBo {

	TaskResultDetailDto getResultDetail(int taskId,int userId) throws AsoLearningSystemErrException;
}
