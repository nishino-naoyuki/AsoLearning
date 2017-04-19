/**
 *
 */
package jp.ac.asojuku.asolearning.bo;

import jp.ac.asojuku.asolearning.dto.AvatarPartsDto;
import jp.ac.asojuku.asolearning.dto.LogonInfoDTO;
import jp.ac.asojuku.asolearning.exception.AsoLearningSystemErrException;

/**
 * アバターID
 * @author nishino
 *
 */
public interface AvatarBo {

	AvatarPartsDto getParts(LogonInfoDTO userInfo) throws AsoLearningSystemErrException;
}
