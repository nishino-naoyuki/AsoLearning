/**
 *
 */
package jp.ac.asojuku.asolearning.bo;

import jp.ac.asojuku.asolearning.dto.AvatarPartsDto;
import jp.ac.asojuku.asolearning.dto.AvatarSettingDto;
import jp.ac.asojuku.asolearning.dto.LogonInfoDTO;
import jp.ac.asojuku.asolearning.exception.AsoLearningSystemErrException;

/**
 * アバターID
 * @author nishino
 *
 */
public interface AvatarBo {


	/**
	 * アバターのパーツを取得する
	 * @param userInfo
	 * @return
	 * @throws AsoLearningSystemErrException
	 */
	AvatarPartsDto getParts(LogonInfoDTO userInfo) throws AsoLearningSystemErrException;

	/**
	 * アバター情報の更新を行う
	 * @param userInfo
	 * @param avatarDto
	 * @throws AsoLearningSystemErrException
	 */
	void updateAvatar(LogonInfoDTO userInfo,AvatarSettingDto avatarDto) throws AsoLearningSystemErrException;
}
