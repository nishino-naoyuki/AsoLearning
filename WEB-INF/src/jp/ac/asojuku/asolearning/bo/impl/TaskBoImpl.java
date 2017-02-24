package jp.ac.asojuku.asolearning.bo.impl;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jp.ac.asojuku.asolearning.bo.TaskBo;
import jp.ac.asojuku.asolearning.condition.TaskSearchContidion;
import jp.ac.asojuku.asolearning.config.MessageProperty;
import jp.ac.asojuku.asolearning.dao.ResultDao;
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
import jp.ac.asojuku.asolearning.json.TaskSearchResultJson;
import jp.ac.asojuku.asolearning.judge.Judge;
import jp.ac.asojuku.asolearning.judge.JudgeFactory;
import jp.ac.asojuku.asolearning.param.RoleId;
import jp.ac.asojuku.asolearning.param.TaskPublicStateId;
import jp.ac.asojuku.asolearning.util.DataUtil;
import jp.ac.asojuku.asolearning.util.Digest;
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
		//公開設定
		for( TaskPublicTblEntity publicEntity : entity.getTaskPublicTblSet()){

			if( TaskPublicStateId.PUBLIC_MUST.equals(publicEntity.getPublicStatusMaster().getStatusId()) ){
				dto.setRequiredFlg(true);
			}else{
				dto.setRequiredFlg(false);
			}

			if( publicEntity.getEndDatetime() != null ){
				dto.setTerminationDate(new SimpleDateFormat("yyyy/MM/dd").format(publicEntity.getEndDatetime()));
			}else{
				dto.setTerminationDate(null);
			}

			TaskPublicDto pdto = new TaskPublicDto();

			pdto.setCourseId(publicEntity.getCourseMaster().getCourseId());
			pdto.setCourseName(publicEntity.getCourseMaster().getCourseName());
			pdto.setEndDatetime( DataUtil.formattedDate(publicEntity.getEndDatetime(), "yyyy/MM/dd") );
			pdto.setPublicDatetime(  DataUtil.formattedDate(publicEntity.getPublicDatetime(), "yyyy/MM/dd"));

			dto.addTaskPublicList(pdto);
		}

		//テストケース
		for( TestcaseTableEntity testcaseEnity : entity.getTestcaseTableSet()){
			TaskTestCaseDto ttc = new TaskTestCaseDto();

			ttc.setAllmostOfMarks(testcaseEnity.getAllmostOfMarks());
			ttc.setInputFileName(testcaseEnity.getInputFileName());
			ttc.setOutputFileName(testcaseEnity.getOutputFileName());
			ttc.setTestcaseId(testcaseEnity.getTestcaseId());

			dto.addTaskTestCaseDtoList(ttc);
		}

		//点数と提出フラグ
		TaskResultDto result = null;

		if( entity.getResultTblSet().size() >0  ){
			result = new TaskResultDto();
			ResultTblEntity rte = entity.getResultTblSet().iterator().next();
			result.setTotal(rte.getTotalScore());
			result.setHanded((rte.getHanded() == 1 ? true:false));
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

			ResultDao retDao = new ResultDao(dao.getConnection());

			//課題リスト情報を取得
			TaskTblEntity entity =
					dao.getTaskDetal(user.getUserId(), user.getCourseId(), taskId);
			if( entity == null ){
				//ここで、EntityがNULLということは、課題IDが改ざんされたか、途中で設定が帰られたか
				//戻値理にNULLを入れて上位に伝える
				logger.warn("課題IDが不正です。このユーザー("+user.getUserId()+")がアクセスできない課題です");
				throw new AsoLearningSystemErrException("課題IDが不正です。");
			}

			Integer rank = retDao.getRankingForUser(user.getUserId(), taskId);

			//会員テーブル→ログイン情報
			dto = getEntityToDto(entity);
			//ランクをセット
			dto.setRank(rank);

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
	public void insert(LogonInfoDTO user, TaskDto dto) throws AsoLearningSystemErrException {


		if( user == null || dto == null  ){
			return;
		}

		TaskDao dao = new TaskDao();

		try {

			//DB接続
			dao.connect();

			TaskTblEntity entity = getTaskTblEntity(dto,dto.getTaskTestCaseDtoList(),dto.getTaskPublicList());
			//課題リスト情報を取得
			dao.insert(user.getUserId(), entity);

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
	public TaskDto getTaskDetailById(Integer taskId,LogonInfoDTO user) throws AsoLearningSystemErrException {

		TaskDto dto = new TaskDto();
		TaskDao dao = new TaskDao();

		//IDのみを指定して課題を取得できるのは先生か管理者だけ（編集者）
		if( RoleId.STUDENT.equals( user.getRoleId() ) ){
			//生徒はIDだけでは取得させない。
			return null;
		}

		try {

			//DB接続
			dao.connect();

			//課題リスト情報を取得
			TaskTblEntity entity =
					dao.getTaskDetal(taskId);


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


	@Override
	public List<TaskDto> getTaskListByCouseId(Integer couseId) throws AsoLearningSystemErrException {

		List<TaskDto> dtoList = new ArrayList<TaskDto>();

		TaskDao dao = new TaskDao();

		try {

			//DB接続
			dao.connect();

			//課題リスト情報を取得
			List<TaskTblEntity> entityList =
					dao.getTaskListByCouseId(couseId);


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

	@Override
	public List<TaskSearchResultJson> search(TaskSearchContidion condition) throws AsoLearningSystemErrException {

		List<TaskSearchResultJson> jsonList = new ArrayList<TaskSearchResultJson>();

		TaskDao dao = new TaskDao();

		try {

			//DB接続
			dao.connect();

			//課題リスト情報を取得
			List<TaskTblEntity> entityList =
					dao.getTaskListBy(condition);

			//JSONへ変換
			for(TaskTblEntity taskEnity : entityList){
				jsonList.add(getTaskSearchResultJsonFromEntity(taskEnity));
			}


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

		return jsonList;
	}

	/**
	 * @param entity
	 * @return
	 * @throws AsoLearningSystemErrException
	 */
	TaskSearchResultJson getTaskSearchResultJsonFromEntity(TaskTblEntity entity) throws AsoLearningSystemErrException{
		TaskSearchResultJson json = new TaskSearchResultJson();

		json.taskId = entity.getTaskId();
		json.taskName = entity.getName();
		json.creator = Digest.decNickName(entity.getCreateUserNickName(), entity.getMailAddress()) ;
		json.limit =
			(entity.getTerminationDate() != null ? DataUtil.formattedDate(entity.getTerminationDate(), "yyyy/MM/dd"):"");

		StringBuffer sb = new StringBuffer();
		for(TaskPublicTblEntity taskPublic: entity.getTaskPublicTblSet() ){
			String statusName = taskPublic.getPublicStatusMaster().getStatusName();

			if( sb.length() > 0 ){
				sb.append(",");
			}
			sb.append(statusName.substring(0, 1));
			sb.append(":");
			sb.append(taskPublic.getCourseMaster().getCourseName());

		}
		json.targetCourseList = sb.toString();

		return json;
	}

	@Override
	public void update(LogonInfoDTO user, TaskDto dto) throws AsoLearningSystemErrException {

		if( user == null || dto == null ){
			return;
		}

		TaskDao dao = new TaskDao();

		try {

			//DB接続
			dao.connect();

			TaskTblEntity entity = getTaskTblEntity(dto,dto.getTaskTestCaseDtoList(),dto.getTaskPublicList());
			//課題リスト情報を取得
			dao.update(user.getUserId(), entity);

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
}
