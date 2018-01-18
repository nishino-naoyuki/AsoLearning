/**
 *
 */
package jp.ac.asojuku.asolearning.validator;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import jp.ac.asojuku.asolearning.config.AppSettingProperty;
import jp.ac.asojuku.asolearning.dto.CourseDto;
import jp.ac.asojuku.asolearning.err.ActionErrors;
import jp.ac.asojuku.asolearning.err.ErrorCode;
import jp.ac.asojuku.asolearning.exception.AsoLearningSystemErrException;
import jp.ac.asojuku.asolearning.param.RoleId;

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

	/**
	 * コースＩＤのチェック
	 * @param couseId
	 * @param list
	 * @param errors
	 * @throws AsoLearningSystemErrException
	 */
	public static void courseId(String couseId,List<CourseDto> list,ActionErrors errors ) throws AsoLearningSystemErrException{

		int intCourseId;
		try{
			intCourseId = Integer.parseInt(couseId);

			boolean find = false;

			for(CourseDto dto : list ){
				if( dto.getId() == intCourseId){
					find = true;
					break;
				}
			}

			if( !find ){
				errors.add(ErrorCode.ERR_MEMBER_ENTRY_MAILADDRESS);
			}

		}catch(NumberFormatException e){
			errors.add(ErrorCode.ERR_MEMBER_ENTRY_MAILADDRESS);
		}
	}

	/**
	 * 入学年度
	 * @param admissionYear
	 * @param errors
	 * @throws AsoLearningSystemErrException
	 */
	public static void admissionYear(String admissionYear,ActionErrors errors) throws AsoLearningSystemErrException{

		try{
			//数値チェック
			Integer.parseInt(admissionYear);

			//4桁以下の場合は、西暦じゃないと判断しエラーとする
			if( StringUtils.length(admissionYear) < 4){
				errors.add(ErrorCode.ERR_MEMBER_ENTRY_ADMISSIONYEAR_ERR);
			}

		}catch(NumberFormatException e){
			errors.add(ErrorCode.ERR_MEMBER_ENTRY_ADMISSIONYEAR);
		}
	}

	/**
	 * 卒業年度
	 * @param graduateYear
	 * @param errors
	 * @throws AsoLearningSystemErrException
	 */
	public static void graduateYear(String graduateYear,ActionErrors errors) throws AsoLearningSystemErrException{

		try{
			//数値チェック
			Integer.parseInt(graduateYear);

			//4桁以下の場合は、西暦じゃないと判断しエラーとする
			if( StringUtils.length(graduateYear) < 4){
				errors.add(ErrorCode.ERR_MEMBER_ENTRY_GRADUATE_ERR);
			}

		}catch(NumberFormatException e){
			errors.add(ErrorCode.ERR_MEMBER_ENTRY_GRADUATEYEAR);
		}
	}

	/**
	 * 退学年度
	 * @param giveupYear
	 * @param errors
	 * @throws AsoLearningSystemErrException
	 */
	public static void gibeupYear(String giveupYear,ActionErrors errors) throws AsoLearningSystemErrException{

		try{
			//数値チェック
			Integer.parseInt(giveupYear);

			//4桁以下の場合は、西暦じゃないと判断しエラーとする
			if( StringUtils.length(giveupYear) < 4){
				errors.add(ErrorCode.ERR_MEMBER_ENTRY_GIVEUP_ERR);
			}

		}catch(NumberFormatException e){
			errors.add(ErrorCode.ERR_MEMBER_ENTRY_GIVEUPYEAR);
		}
	}

	/**
	 * ロールＩＤ
	 * @param roleId
	 * @param errors
	 * @throws AsoLearningSystemErrException
	 */
	public static void roleId(String roleId,ActionErrors errors) throws AsoLearningSystemErrException{

		int introleId;
		try{
			introleId = Integer.parseInt(roleId);

			if( !RoleId.check(introleId) ){
				errors.add(ErrorCode.ERR_MEMBER_ENTRY_ROLEID_ERR);
			}

		}catch(NumberFormatException e){
			errors.add(ErrorCode.ERR_MEMBER_ENTRY_ROLEID_ERR);
		}
	}

	/**
	 * パスワード
	 * @param password
	 * @param errors
	 * @throws AsoLearningSystemErrException
	 */
	public static void password(String password,ActionErrors errors) throws AsoLearningSystemErrException{
		String policy = AppSettingProperty.getInstance().getPasswordPolicy();

		if( StringUtils.isNotEmpty(policy)){
			if(!password.matches(policy)){
				errors.add(ErrorCode.ERR_MEMBER_ENTRY_PASSWORD_POLICY);
			}

		}
	}
}
