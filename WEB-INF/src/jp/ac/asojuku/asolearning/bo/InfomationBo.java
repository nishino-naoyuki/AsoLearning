/**
 *
 */
package jp.ac.asojuku.asolearning.bo;

import jp.ac.asojuku.asolearning.dto.InfomationDto;
import jp.ac.asojuku.asolearning.dto.LogonInfoDTO;
import jp.ac.asojuku.asolearning.exception.AsoLearningSystemErrException;

/**
 * お知らせ情報BO
 * @author nishino
 *
 */
public interface InfomationBo {

	InfomationDto get(LogonInfoDTO logon) throws AsoLearningSystemErrException;
}
