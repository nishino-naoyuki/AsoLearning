package jp.ac.asojuku.asolearning.bo.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jp.ac.asojuku.asolearning.bo.TaskBo;
import jp.ac.asojuku.asolearning.dao.TaskDao;
import jp.ac.asojuku.asolearning.dto.LogonInfoDTO;
import jp.ac.asojuku.asolearning.dto.TaskDto;
import jp.ac.asojuku.asolearning.dto.TaskResultDto;
import jp.ac.asojuku.asolearning.entity.ResultTblEntity;
import jp.ac.asojuku.asolearning.entity.TaskPublicTblEntity;
import jp.ac.asojuku.asolearning.entity.TaskTblEntity;
import jp.ac.asojuku.asolearning.exception.AsoLearningSystemErrException;
import jp.ac.asojuku.asolearning.exception.DBConnectException;
import jp.ac.asojuku.asolearning.param.TaskPublicStateId;

public class TaskBoImpl implements TaskBo {

	Logger logger = LoggerFactory.getLogger(TaskBoImpl.class);

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
			dtoList = EntityToDto(entityList);

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
	 * リスト情報をDTOに入れなおす
	 * @param entity
	 * @return
	 */
	private List<TaskDto> EntityToDto(List<TaskTblEntity> entityList){

		if( entityList == null ){
			return null;
		}

		List<TaskDto> dtoList = new ArrayList<TaskDto>();

		for( TaskTblEntity task : entityList){
			TaskDto dto = new TaskDto();

			dto.setTaskId(task.getTaskId());
			dto.setTaskName(task.getName());
			//必須かどうかのフラグ
			TaskPublicTblEntity tpe = task.getTaskPublicTblSet().iterator().next();
			if( tpe != null ){
				if( TaskPublicStateId.PUBLIC_MUST.equals(tpe.getPublicStatusMaster().getStatusId()) ){
					dto.setRequiredFlg(true);
				}else{
					dto.setRequiredFlg(false);
				}
			}
			//点数
			TaskResultDto result = null;

			if( task.getResultTblSet() != null ){
				result = new TaskResultDto();
				ResultTblEntity rte = task.getResultTblSet().iterator().next();
				result.setTotal(rte.getTotalScore());
			}
			dto.setResult(result);

			dtoList.add(dto);
		}

		return dtoList;
	}

}
