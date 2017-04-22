/**
 *
 */
package jp.ac.asojuku.asolearning.bo;

import java.util.List;

import javax.servlet.http.HttpSession;

import jp.ac.asojuku.asolearning.condition.SearchUserCondition;
import jp.ac.asojuku.asolearning.csv.model.UserCSV;
import jp.ac.asojuku.asolearning.dto.LogonInfoDTO;
import jp.ac.asojuku.asolearning.dto.UserDetailDto;
import jp.ac.asojuku.asolearning.dto.UserDto;
import jp.ac.asojuku.asolearning.dto.UserSearchResultDto;
import jp.ac.asojuku.asolearning.err.ActionErrors;
import jp.ac.asojuku.asolearning.exception.AsoLearningSystemErrException;

/**
 * ユーザー情報を扱う
 * @author nishino
 *
 */
public interface UserBo {

	String createTaskUserCSV(SearchUserCondition cond) throws AsoLearningSystemErrException;
	/**
	 * ユーザーCSVを出力する
	 * @param userList
	 * @return
	 * @throws AsoLearningSystemErrException
	 */
	String createUserCSV(List<UserSearchResultDto> userList) throws AsoLearningSystemErrException;

	public void updatePassword(Integer userId,String password,String maileaddress) throws AsoLearningSystemErrException;
	public void updateNickName(Integer userId,String nickName,String maileaddress) throws AsoLearningSystemErrException;
	/**
	 * 詳細情報を取得する
	 *
	 * @param userId
	 * @return
	 * @throws AsoLearningSystemErrException
	 */
	public UserDetailDto detail(Integer userId) throws AsoLearningSystemErrException;
	/**
	 * 検索処理
	 *
	 * @param cond
	 * @return
	 * @throws AsoLearningSystemErrException
	 */
	List<UserSearchResultDto> search(SearchUserCondition cond)  throws AsoLearningSystemErrException;
	/**
	 * ユーザー情報の挿入
	 * @param userDto
	 * @throws AsoLearningSystemErrException
	 */
	public void insert(UserDto userDto,LogonInfoDTO loginInfo) throws AsoLearningSystemErrException;

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
