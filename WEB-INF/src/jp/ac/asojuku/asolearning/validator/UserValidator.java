/**
 *
 */
package jp.ac.asojuku.asolearning.validator;

import org.apache.commons.lang3.StringUtils;

import jp.ac.asojuku.asolearning.err.ActionErrors;
import jp.ac.asojuku.asolearning.err.ErrorCode;
import jp.ac.asojuku.asolearning.exception.AsoLearningSystemErrException;

/**
 * ユーザー情報のチェック
 * @author nishino
 *
 */
public class UserValidator {

	/**
	 * ユーザー名のチェック
	 * 今のところ文字種は何でもOKとする
	 *
	 * @param name
	 * @param errors
	 * @throws AsoLearningSystemErrException
	 */
	public static void useName(String name,ActionErrors errors) throws AsoLearningSystemErrException{

		//必須
		if( StringUtils.isEmpty(name) ){
			errors.add(ErrorCode.ERR_MEMBER_ENTRY_NAME_ISNEED);
		}
		//最大文字数
		if( StringUtils.length(name) > 100){
			errors.add(ErrorCode.ERR_MEMBER_ENTRY_NAME);
		}
	}

	/**
	 * ニックネーム
	 *
	 * @param name
	 * @param errors
	 * @throws AsoLearningSystemErrException
	 */
	public static void useNickName(String nickname,ActionErrors errors) throws AsoLearningSystemErrException{

		//必須
		if( StringUtils.isEmpty(nickname) ){
			errors.add(ErrorCode.ERR_MEMBER_ENTRY_NICKNAME_ISNEED);
		}
		//最大文字数
		if( StringUtils.length(nickname) > 100){
			errors.add(ErrorCode.ERR_MEMBER_ENTRY_NICKNAME);
		}
	}
	/**
	 * メールアドレスのチェック
	 *
	 * @param mailAddress
	 * @param errors
	 * @throws AsoLearningSystemErrException
	 */
	public static void mailAddress(String mailAddress,ActionErrors errors) throws AsoLearningSystemErrException{
		String mailFormat = "^[a-zA-Z0-9!#$%&'_`/=~\\*\\+\\-\\?\\^\\{\\|\\}]+(\\.[a-zA-Z0-9!#$%&'_`/=~\\*\\+\\-\\?\\^\\{\\|\\}]+)*+(.*)@[a-zA-Z0-9][a-zA-Z0-9\\-]*(\\.[a-zA-Z0-9\\-]+)+$";

		//必須
		if( StringUtils.isEmpty(mailAddress) ){
			errors.add(ErrorCode.ERR_MEMBER_ENTRY_MAILADDRESS_ISNEED);
		}
		if (!mailAddress.matches(mailFormat)) {
			errors.add(ErrorCode.ERR_MEMBER_ENTRY_MAILADDRESS);
		}
		//最大文字数
		if( StringUtils.length(mailAddress) > 256){
			errors.add(ErrorCode.ERR_MEMBER_ENTRY_MAILADDRESS);
		}
	}
}
