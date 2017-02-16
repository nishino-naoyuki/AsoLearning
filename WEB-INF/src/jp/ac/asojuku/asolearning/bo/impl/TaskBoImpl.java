package jp.ac.asojuku.asolearning.bo.impl;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jp.ac.asojuku.asolearning.bo.TaskBo;
import jp.ac.asojuku.asolearning.config.MessageProperty;
import jp.ac.asojuku.asolearning.dao.TaskDao;
import jp.ac.asojuku.asolearning.dto.LogonInfoDTO;
import jp.ac.asojuku.asolearning.dto.TaskDto;
import jp.ac.asojuku.asolearning.dto.TaskPublicDto;
import jp.ac.asojuku.asolearning.dto.TaskResultDto;
import jp.ac.asojuku.asolearning.dto.TaskTestCaseDto;
import jp.ac.asojuku.asolearning.entity.CourseMasterEntity;
import jp.ac.asojuku.asolearning.entity.PublicStatusMasterEntity;
import jp.ac.asojuku.asolearning.entity.ResultTblEntity;
import jp.ac.asojuku.asolearning.entity.TaskPublicTblEntity;
import jp.ac.asojuku.asolearning.entity.TaskTblEntity;
import jp.ac.asojuku.asolearning.entity.TestcaseTableEntity;
import jp.ac.asojuku.asolearning.err.ErrorCode;
import jp.ac.asojuku.asolearning.exception.AsoLearningSystemErrException;
import jp.ac.asojuku.asolearning.exception.DBConnectException;
import jp.ac.asojuku.asolearning.exception.IllegalJudgeFileException;
import jp.ac.asojuku.asolearning.json.JudgeResultJson;
import jp.ac.asojuku.asolearning.judge.Judge;
import jp.ac.asojuku.asolearning.judge.JudgeFactory;
import jp.ac.asojuku.asolearning.param.TaskPublicStateId;
import jp.ac.asojuku.asolearning.util.SqlDateUtil;

public class TaskBoImpl implements TaskBo {

	Logger logger = LoggerFactory.getLogger(TaskBoImpl.class);

	/* (非 Javadoc)
	 * 受け取った課題IDが、正常なものかをチェックしてから課題のチェックを行う
	 * @see jp.ac.asojuku.asolearning.bo.TaskBo#judgeTask(java.lang.Integer, jp.ac.asojuku.asolearning.dto.LogonInfoDTO, java.lang.String, java.lang.String)
	 */
	@Override
	public JudgeResultJson judgeTask(Integer taskId, LogonInfoDTO user,String dirName, String fileName) throws AsoLearningSystemErrException {

		JudgeResultJson json = new JudgeResultJson();
		Judge judge = JudgeFactory.getInstance();

		TaskDao dao = new TaskDao();

		try {

			//DB接続
			dao.connect();

			//課題リスト情報を取得
			TaskTblEntity entity =
					dao.getTaskDetal(user.getUserId(), user.getCourseId(), taskId);

			if( entity == null ){
				//ここで、EntityがNULLということは、課題IDが改ざんされたか、途中で設定が帰られたか
				//戻値理にNULLを入れて上位に伝える
				logger.warn("課題IDが不正です。このユーザー("+user.getUserId()+")がアクセスできない課題です");
				json.errorMsg =
						MessageProperty.getInstance().getErrorMsgFromErrCode(ErrorCode.ERR_TASK_ID_ERROR);
			}else{
				/////////////////////////////
				//判定処理呼び出し
				json = judge.judge(entity,dirName, fileName,user.getUserId(),dao.getConnection());
			}

		} catch (IllegalJudgeFileException e) {
			//拡張子が不正
			logger.warn("ファイルの拡張子が不正です：",e);
			json.errorMsg =
					MessageProperty.getInstance().getErrorMsgFromErrCode(ErrorCode.ERR_TASK_EXT_ERROR);

		} catch (DBConnectException e) {
			//ログ出力
			logger.warn("DB接続エラー：",e);
			throw new AsoLearningSystemErrException(e);

		} catch (SQLException e) {
			//ログ出力
			logger.warn("SQLエラー：",e);
			throw new AsoLearningSystemErrException(e);
		} finally{

			dao.close();
		}

		return json;
	}

	@Override
	public List<TaskDto> getTaskListForUser(LogonInfoDTO user) throws AsoLearningSystemErrException {

		List<TaskDto> dtoList = new ArrayList<TaskDto>();

		TaskDao dao = new TaskDao();

		try {

			//DB接続
			dao.connect();

			//課題リスト情報を取得
			List<TaskTblEntity> entityList =
					dao.getTaskList(user.getUserId(), user.getCourseId(), 0, 10);


			//会員テーブル→ログイン情報
			dtoList = getEntityListToDtoList(entityList);

		} catch (DBConnectException e) {
			//ログ出力
			logger.warn("DB接続エラー：",e);
			throw new AsoLearningSystemErrException(e);

		} catch (SQLException e) {
			//ログ出力
			logger.warn("SQLエラー：",e);
			throw new AsoLearningSystemErrException(e);
		} finally{

			dao.close();
		}

		return dtoList;
	}

	/**
	 * 課題エンティティから課題DTOを作る
	 *
	 * @param entity
	 * @return
	 */
	private TaskDto getEntityToDto(TaskTblEntity entity){

		if( entity == null ){
			return null;
		}

		TaskDto dto = new TaskDto();

		dto.setTaskId(entity.getTaskId());
		dto.setTaskName(entity.getName());
		dto.setQuestion(entity.getTaskQuestion());
		//必須かどうかのフラグ
		TaskPublicTblEntity tpe = entity.getTaskPublicTblSet().iterator().next();
		if( tpe != null ){
			if( TaskPublicStateId.PUBLIC_MUST.equals(tpe.getPublicStatusMaster().getStatusId()) ){
				dto.setRequiredFlg(true);
			}else{
				dto.setRequiredFlg(false);
			}

			if( tpe.getEndDatetime() != null ){
				dto.setTerminationDate(new SimpleDateFormat("yyyy/MM/dd").format(tpe.getEndDatetime()));
			}else{
				dto.setTerminationDate(null);
			}

		}
		//点数
		TaskResultDto result = null;

		if( entity.getResultTblSet() != null ){
			result = new TaskResultDto();
			ResultTblEntity rte = entity.getResultTblSet().iterator().next();
			result.setTotal(rte.getTotalScore());
		}
		dto.setResult(result);

		return dto;
	}
	/**
	 * リスト情報をDTOに入れなおす
	 * @param entity
	 * @return
	 */
	private List<TaskDto> getEntityListToDtoList(List<TaskTblEntity> entityList){

		if( entityList == null ){
			return null;
		}

		List<TaskDto> dtoList = new ArrayList<TaskDto>();

		for( TaskTblEntity task : entityList){
			TaskDto dto = getEntityToDto(task);

			dtoList.add(dto);
		}

		return dtoList;
	}

	/* (非 Javadoc)
	 * @see jp.ac.asojuku.asolearning.bo.TaskBo#getTaskDetailForUser(java.lang.Integer, jp.ac.asojuku.asolearning.dto.LogonInfoDTO)
	 */
	@Override
	public TaskDto getTaskDetailForUser(Integer taskId, LogonInfoDTO user) throws AsoLearningSystemErrException {

		if( taskId == null ){
			return null;
		}
		TaskDto dto = new TaskDto();
		TaskDao dao = new TaskDao();

		try {

			//DB接続
			dao.connect();

			//課題リスト情報を取得
			TaskTblEntity entity =
					dao.getTaskDetal(user.getUserId(), user.getCourseId(), taskId);


			//会員テーブル→ログイン情報
			dto = getEntityToDto(entity);

		} catch (DBConnectException e) {
			//ログ出力
			logger.warn("DB接続エラー：",e);
			throw new AsoLearningSystemErrException(e);

		} catch (SQLException e) {
			//ログ出力
			logger.warn("SQLエラー：",e);
			throw new AsoLearningSystemErrException(e);
		} finally{

			dao.close();
		}

		return dto;
	}

	@Override
	public void insert(LogonInfoDTO user, TaskDto dto, List<TaskTestCaseDto> testCaseList,
			List<TaskPublicDto> taskPublicList) throws AsoLearningSystemErrException {


		if( user == null || dto == null ||
				testCaseList == null || taskPublicList == null ){
			return;
		}

		TaskDao dao = new TaskDao();

		try {

			//DB接続
			dao.connect();

			TaskTblEntity entity = getTaskTblEntity(dto,testCaseList,taskPublicList);
			//課題リスト情報を取得
			dao.insert(user.getName(), entity);

		} catch (DBConnectException e) {
			//ログ出力
			logger.warn("DB接続エラー：",e);
			throw new AsoLearningSystemErrException(e);

		} catch (SQLException e) {
			//ログ出力
			logger.warn("SQLエラー：",e);
			throw new AsoLearningSystemErrException(e);
		} catch (ParseException e) {
			logger.warn("パースエラー：",e);
			throw new AsoLearningSystemErrException(e);
		} finally{

			dao.close();
		}

	}

	/**
	 * 課題エンティティを作成
	 *
	 *
	 * @param dto
	 * @param testCaseList
	 * @param taskPublicList
	 * @return
	 * @throws ParseException
	 */
	private TaskTblEntity getTaskTblEntity(
			TaskDto dto,
			List<TaskTestCaseDto> testCaseList,
			List<TaskPublicDto> taskPublicList) throws ParseException{
		TaskTblEntity entity = new TaskTblEntity();

		//基本情報
		entity.setName(dto.getTaskName());
		entity.setTaskQuestion(dto.getQuestion());
		entity.setTaskId(dto.getTaskId());

		//テストケース情報
		for( TaskTestCaseDto testcaseDto : testCaseList){
			TestcaseTableEntity testcase = new TestcaseTableEntity();

			if( testcaseDto.getAllmostOfMarks() != null ){
				testcase.setTestcaseId(testcaseDto.getTestcaseId());
				testcase.setInputFileName(testcaseDto.getInputFileName());
				testcase.setOutputFileName(testcaseDto.getOutputFileName());
				testcase.setAllmostOfMarks(testcaseDto.getAllmostOfMarks());

				entity.addTestcaseTable(testcase);
			}
		}

		//公開情報
		for( TaskPublicDto publicDto : taskPublicList){
			TaskPublicTblEntity testPublic = new TaskPublicTblEntity();
			CourseMasterEntity courseMaster = new CourseMasterEntity();
			PublicStatusMasterEntity publicStatus = new PublicStatusMasterEntity();

			courseMaster.setCourseId(publicDto.getCourseId());
			courseMaster.setCourseName(publicDto.getCourseName());
			publicStatus.setStatusId(publicDto.getStatus().getId());

			testPublic.setCourseMaster(courseMaster);
			testPublic.setPublicStatusMaster(publicStatus);
			testPublic.setPublicDatetime(SqlDateUtil.getDateFrom(publicDto.getPublicDatetime(), "yyyy/MM/dd"));
			testPublic.setEndDatetime(SqlDateUtil.getDateFrom(publicDto.getEndDatetime(), "yyyy/MM/dd"));

			entity.addTaskPublicTbl(testPublic);
		}
		return entity;
	}

	@Override
	public TaskDto getTaskDetailForName(String name) throws AsoLearningSystemErrException {

		TaskDto dto = new TaskDto();
		TaskDao dao = new TaskDao();

		try {

			//DB接続
			dao.connect();

			//課題リスト情報を取得
			TaskTblEntity entity =
					dao.getTaskDetal(name);


			//会員テーブル→ログイン情報
			dto = getEntityToDto(entity);

		} catch (DBConnectException e) {
			//ログ出力
			logger.warn("DB接続エラー：",e);
			throw new AsoLearningSystemErrException(e);

		} catch (SQLException e) {
			//ログ出力
			logger.warn("SQLエラー：",e);
			throw new AsoLearningSystemErrException(e);
		} finally{

			dao.close();
		}

		return dto;
	}
}
