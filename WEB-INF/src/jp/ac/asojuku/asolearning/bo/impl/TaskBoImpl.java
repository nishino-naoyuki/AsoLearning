package jp.ac.asojuku.asolearning.bo.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jp.ac.asojuku.asolearning.bo.TaskBo;
import jp.ac.asojuku.asolearning.config.MessageProperty;
import jp.ac.asojuku.asolearning.dao.TaskDao;
import jp.ac.asojuku.asolearning.dto.LogonInfoDTO;
import jp.ac.asojuku.asolearning.dto.TaskDto;
import jp.ac.asojuku.asolearning.dto.TaskResultDto;
import jp.ac.asojuku.asolearning.entity.ResultTblEntity;
import jp.ac.asojuku.asolearning.entity.TaskPublicTblEntity;
import jp.ac.asojuku.asolearning.entity.TaskTblEntity;
import jp.ac.asojuku.asolearning.err.ErrorCode;
import jp.ac.asojuku.asolearning.exception.AsoLearningSystemErrException;
import jp.ac.asojuku.asolearning.exception.DBConnectException;
import jp.ac.asojuku.asolearning.exception.IllegalJudgeFileException;
import jp.ac.asojuku.asolearning.json.JudgeResultJson;
import jp.ac.asojuku.asolearning.judge.Judge;
import jp.ac.asojuku.asolearning.judge.JudgeFactory;
import jp.ac.asojuku.asolearning.param.TaskPublicStateId;

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
				json = judge.judge(entity,dirName, fileName);
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

}
