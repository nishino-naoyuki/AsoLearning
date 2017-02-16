/**
 *
 */
package jp.ac.asojuku.asolearning.validator;

import java.text.ParseException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import jp.ac.asojuku.asolearning.bo.TaskBo;
import jp.ac.asojuku.asolearning.bo.impl.TaskBoImpl;
import jp.ac.asojuku.asolearning.config.MessageProperty;
import jp.ac.asojuku.asolearning.dto.TaskPublicDto;
import jp.ac.asojuku.asolearning.dto.TaskTestCaseDto;
import jp.ac.asojuku.asolearning.err.ActionErrors;
import jp.ac.asojuku.asolearning.err.ErrorCode;
import jp.ac.asojuku.asolearning.exception.AsoLearningSystemErrException;

/**
 * 課題のエラーチェッククラス
 * @author nishino
 *
 */
public class TaskValidator {

	/**
	 * 課題名のチェック
	 * @param name
	 * @param errors
	 * @throws AsoLearningSystemErrException
	 */
	public static void taskName(String name,ActionErrors errors) throws AsoLearningSystemErrException{

		//必須
		if( StringUtils.isEmpty(name) ){
			errors.add(ErrorCode.ERR_TASK_NAME_EMPTY);
		}
		//最大文字数
		if( StringUtils.length(name) > 200){
			errors.add(ErrorCode.ERR_TASK_NAME_LENGTH);
		}
		//名前のダブりチェック
		TaskBo taskBo = new TaskBoImpl();
		if( taskBo.getTaskDetailForName(name) != null ){
			errors.add(ErrorCode.ERR_TASK_DUPLICATE_ERR);
		}
	}

	/**
	 * 質問文
	 * @param question
	 * @param errors
	 * @throws AsoLearningSystemErrException
	 */
	public static void question(String question,ActionErrors errors) throws AsoLearningSystemErrException{

		//必須
		if( StringUtils.isEmpty(question) ){
			errors.add(ErrorCode.ERR_TASK_Q_EMPTY);
		}
		//最大文字数
		if( StringUtils.length(question) > 3000){
			errors.add(ErrorCode.ERR_TASK_Q_LENGTH);
		}
	}

	/**
	 * @param list
	 * @param errors
	 * @throws AsoLearningSystemErrException
	 */
	public static void publicStateList(List<TaskPublicDto> list,ActionErrors errors) throws AsoLearningSystemErrException{

		for(TaskPublicDto taskPub : list ){
			publicState(taskPub,errors);
		}
	}

	/**
	 * @param taskPub
	 * @param errors
	 * @throws AsoLearningSystemErrException
	 */
	private static void publicState(TaskPublicDto taskPub,ActionErrors errors) throws AsoLearningSystemErrException{


		//公開日チェック
		if( StringUtils.isNotEmpty(taskPub.getPublicDatetime()) ){
			try {
			    DateUtils.parseDateStrictly(taskPub.getPublicDatetime(), new String[] {"yyyy/MM/dd"});
			} catch (ParseException e) {
				String errMsg = MessageProperty.getInstance().getErrorMsgFromErrCode(ErrorCode.ERR_TASK_PDATE_ERR);
				errors.add(ErrorCode.ERR_TASK_PDATE_ERR,taskPub.getCourseName()+":"+errMsg);
			}
		}

		//締切日チェック
		if( StringUtils.isNotEmpty(taskPub.getEndDatetime()) ){
			try {
			    DateUtils.parseDateStrictly(taskPub.getEndDatetime(), new String[] {"yyyy/MM/dd"});
			} catch (ParseException e) {
				String errMsg = MessageProperty.getInstance().getErrorMsgFromErrCode(ErrorCode.ERR_TASK_EDATE_ERR);
				errors.add(ErrorCode.ERR_TASK_EDATE_ERR,taskPub.getCourseName()+":"+errMsg);
			}
		}
	}

	/**
	 * テストケースリストのエラーチェック
	 * @param testCaseList
	 * @param errors
	 * @throws AsoLearningSystemErrException
	 */
	public static void testcaseList(List<TaskTestCaseDto> testCaseList,ActionErrors errors) throws AsoLearningSystemErrException{

		int totalAllmost = 0;

		for( TaskTestCaseDto testCase : testCaseList){

			testcase(testCase,errors);
			if( testCase.getAllmostOfMarks() != null ){
				totalAllmost += testCase.getAllmostOfMarks();
			}
		}

		//合計50点になった？
		if( totalAllmost != 50 ){
			errors.add(ErrorCode.ERR_TASK_ALLMOST_ERR);
		}
	}

	/**
	 * テストケースのエラーチェック
	 * @param testCase
	 * @param errors
	 * @throws AsoLearningSystemErrException
	 */
	private static void testcase(TaskTestCaseDto testCase,ActionErrors errors) throws AsoLearningSystemErrException{

		if( StringUtils.isNotEmpty(testCase.getInputFileName()) &&
				StringUtils.isEmpty(testCase.getOutputFileName())){
			//入力ファイル名のみの指定はNG
			errors.add(ErrorCode.ERR_TASK_INPUTF_ERR);
		}

		if( testCase.getAllmostOfMarks() != null &&
				StringUtils.isEmpty(testCase.getOutputFileName())){
			//配点は指定したけど、正解ファイルが無い
			errors.add(ErrorCode.ERR_TASK_OUTPUTF_ERR);
		}
	}
}
