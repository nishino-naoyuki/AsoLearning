package jp.ac.asojuku.asolearning.bo.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jp.ac.asojuku.asolearning.bo.TaskGroupBo;
import jp.ac.asojuku.asolearning.dao.TaskGroupDao;
import jp.ac.asojuku.asolearning.dto.TaskGroupDto;
import jp.ac.asojuku.asolearning.entity.TaskGroupTblEntity;
import jp.ac.asojuku.asolearning.exception.AsoLearningSystemErrException;
import jp.ac.asojuku.asolearning.exception.DBConnectException;

public class TaskGroupBoImpl implements TaskGroupBo {
	Logger logger = LoggerFactory.getLogger(TaskGroupBoImpl.class);

	/* (非 Javadoc)
	 * @see jp.ac.asojuku.asolearning.bo.TaskGroupBo#getTaskGroupList(java.lang.String)
	 */
	public List<TaskGroupDto> getTaskGroupList(String groupName) throws AsoLearningSystemErrException{

		List<TaskGroupDto> dtoList = new ArrayList<TaskGroupDto>();

		TaskGroupDao dao = new TaskGroupDao();

		try {

			//DB接続
			dao.connect();

			//課題グループリスト情報を取得
			List<TaskGroupTblEntity> entityList =	dao.getTaskGroupListBy(groupName);


			//Entity→DTO変換
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

	private List<TaskGroupDto> getEntityListToDtoList(List<TaskGroupTblEntity> entityList){
		List<TaskGroupDto> dtoList = new ArrayList<TaskGroupDto>();

		for(TaskGroupTblEntity entity : entityList){
			TaskGroupDto dto = new TaskGroupDto();

			dto.setId(entity.getTaskGroupId());
			dto.setName(entity.getTaskGroupName());

			dtoList.add(dto);
		}

		return dtoList;
	}

	/* (非 Javadoc)
	 * @see jp.ac.asojuku.asolearning.bo.TaskGroupBo#getTaskGroupListByCourseId(java.lang.Integer)
	 */
	@Override
	public List<TaskGroupDto> getTaskGroupListByCourseId(Integer courseId) throws AsoLearningSystemErrException {

		List<TaskGroupDto> dtoList = new ArrayList<TaskGroupDto>();

		TaskGroupDao dao = new TaskGroupDao();

		try {
			//DB接続
			dao.connect();

			//課題グループリスト情報を取得
			List<TaskGroupTblEntity> entityList =
					dao.getTaskGroupListByCourseId(courseId);

			//Entity→DTO変換
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
}
