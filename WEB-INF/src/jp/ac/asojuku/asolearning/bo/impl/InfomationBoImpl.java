/**
 *
 */
package jp.ac.asojuku.asolearning.bo.impl;

import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jp.ac.asojuku.asolearning.bo.InfomationBo;
import jp.ac.asojuku.asolearning.config.MessageProperty;
import jp.ac.asojuku.asolearning.dao.InfomationDao;
import jp.ac.asojuku.asolearning.dto.InfomationDto;
import jp.ac.asojuku.asolearning.dto.LogonInfoDTO;
import jp.ac.asojuku.asolearning.entity.TaskTblEntity;
import jp.ac.asojuku.asolearning.exception.AsoLearningSystemErrException;
import jp.ac.asojuku.asolearning.exception.DBConnectException;
import jp.ac.asojuku.asolearning.param.RoleId;

/**
 * @author nishino
 *
 */
public class InfomationBoImpl implements InfomationBo {
	Logger logger = LoggerFactory.getLogger(InfomationBoImpl.class);

	/* (非 Javadoc)
	 * @see jp.ac.asojuku.asolearning.bo.InfomationBo#get(jp.ac.asojuku.asolearning.dto.LogonInfoDTO)
	 */
	@Override
	public InfomationDto get(LogonInfoDTO logon) throws AsoLearningSystemErrException {

		InfomationDto dto = new InfomationDto();
		InfomationDao dao = new InfomationDao();

		try {

			//DB接続
			dao.connect();

			Integer courseId = (RoleId.STUDENT.equals(logon.getRoleId()) ? logon.getCourseId():null);
			/////////////////////////////
			//最近作った課題
			List<TaskTblEntity> createList = dao.getCreateRecentList(courseId);
			for( TaskTblEntity task : createList){
				dto.addInfoList(
					String.format(MessageProperty.getInstance().getProperty(MessageProperty.INFO_RECENT_CREATE),task.getName())
					);
			}
			/////////////////////////////
			//最近更新した課題
			List<TaskTblEntity> updateList = dao.getUpdateRecentList(courseId);
			for( TaskTblEntity task : updateList){
				dto.addInfoList(
					String.format(MessageProperty.getInstance().getProperty(MessageProperty.INFO_RECENT_UPDATE),task.getName())
					);
			}
			if( RoleId.STUDENT.equals(logon.getRoleId()) ){
				/////////////////////////////
				//未提出課題
				List<TaskTblEntity> notHandedTaskList = dao.getNearEndDateList(logon.getUserId(),logon.getCourseId());
				for( TaskTblEntity task : notHandedTaskList){
					dto.addInfoList(
						String.format(MessageProperty.getInstance().getProperty(MessageProperty.INFO_RECENT_END),task.getName())
						);
				}
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

		return dto;
	}

}
