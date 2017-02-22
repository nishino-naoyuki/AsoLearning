/**
 *
 */
package jp.ac.asojuku.asolearning.bo;

import java.util.List;

import javax.servlet.http.HttpSession;

import jp.ac.asojuku.asolearning.csv.model.UserCSV;
import jp.ac.asojuku.asolearning.dto.UserDto;
import jp.ac.asojuku.asolearning.err.ActionErrors;
import jp.ac.asojuku.asolearning.exception.AsoLearningSystemErrException;

/**
 * ユーザー情報を扱う
 * @author nishino
 *
 */
public interface UserBo {

	/**
	 * ユーザー情報の挿入
	 * @param userDto
	 * @throws AsoLearningSystemErrException
	 */
	public void insert(UserDto userDto) throws AsoLearningSystemErrException;

	/**
	 * CSVによるユーザーの登録
	 * @param userList
	 * @param uuid
	 * @param session
	 * @param type
	 * @throws AsoLearningSystemErrException
	 */
	public void insertByCSV(List<UserCSV> userList,String uuid,HttpSession session,String type) throws AsoLearningSystemErrException;

	/**
	 * CSVデータに誤りが無いかのチェック
	 * @param csvPath
	 * @param errors
	 * @param type
	 * @return
	 * @throws AsoLearningSystemErrException
	 */
	public List<UserCSV> checkForCSV(String csvPath,ActionErrors errors,String type) throws AsoLearningSystemErrException;
}
