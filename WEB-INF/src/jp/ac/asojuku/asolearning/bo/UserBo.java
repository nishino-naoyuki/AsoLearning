/**
 *
 */
package jp.ac.asojuku.asolearning.bo;

import jp.ac.asojuku.asolearning.dto.UserDto;
import jp.ac.asojuku.asolearning.exception.AsoLearningSystemErrException;

/**
 * ユーザー情報を扱う
 * @author nishino
 *
 */
public interface UserBo {

	public void insert(UserDto userDto) throws AsoLearningSystemErrException;

	public void insertByCSV(String csvPath) throws AsoLearningSystemErrException;
}
