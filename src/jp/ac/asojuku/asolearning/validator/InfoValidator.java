package jp.ac.asojuku.asolearning.validator;

import java.text.ParseException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import jp.ac.asojuku.asolearning.config.MessageProperty;
import jp.ac.asojuku.asolearning.dto.InfoPublicDto;
import jp.ac.asojuku.asolearning.err.ActionErrors;
import jp.ac.asojuku.asolearning.err.ErrorCode;
import jp.ac.asojuku.asolearning.exception.AsoLearningSystemErrException;

public class InfoValidator {

	/**
	 * タイトルのチェック
	 * @param title
	 * @param errors
	 * @throws AsoLearningSystemErrException
	 */
	public static void title(String title,ActionErrors errors) throws AsoLearningSystemErrException{

		if( StringUtils.length(title) ==0 || StringUtils.length(title) > 60 ){
			errors.add(ErrorCode.ERR_INFO_TITLE_LEN);
		}
	}

	/**
	 * メッセージのチェック
	 * @param message
	 * @param errors
	 * @throws AsoLearningSystemErrException
	 */
	public static void contents(String message,ActionErrors errors) throws AsoLearningSystemErrException{

		if( StringUtils.length(message) ==0 || StringUtils.length(message) > 100 ){
			errors.add(ErrorCode.ERR_INFO_MSG_LEN);
		}
	}

	/**
	 * @param list
	 * @param errors
	 * @throws AsoLearningSystemErrException
	 */
	public static void publicStateList(List<InfoPublicDto> list,ActionErrors errors) throws AsoLearningSystemErrException{

		for(InfoPublicDto infoPub : list ){
			InfoValidator.publicState(infoPub,errors);
		}
	}
	/**
	 * @param taskPub
	 * @param errors
	 * @throws AsoLearningSystemErrException
	 */
	private static void publicState(InfoPublicDto infoPub,ActionErrors errors) throws AsoLearningSystemErrException{


		//公開日チェック
		if( StringUtils.isNotEmpty(infoPub.getPublicDatetime()) ){
			try {
			    DateUtils.parseDateStrictly(infoPub.getPublicDatetime(), new String[] {"yyyy/MM/dd"});
			} catch (ParseException e) {
				String errMsg = MessageProperty.getInstance().getErrorMsgFromErrCode(ErrorCode.ERR_TASK_PDATE_ERR);
				errors.add(ErrorCode.ERR_TASK_PDATE_ERR,infoPub.getCourseName()+":"+errMsg);
			}
		}

		//締切日チェック
		if( StringUtils.isNotEmpty(infoPub.getEndDatetime()) ){
			try {
			    DateUtils.parseDateStrictly(infoPub.getEndDatetime(), new String[] {"yyyy/MM/dd"});
			} catch (ParseException e) {
				String errMsg = MessageProperty.getInstance().getErrorMsgFromErrCode(ErrorCode.ERR_TASK_EDATE_ERR);
				errors.add(ErrorCode.ERR_TASK_EDATE_ERR,infoPub.getCourseName()+":"+errMsg);
			}
		}
	}
}
