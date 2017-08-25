/**
 *
 */
package jp.ac.asojuku.asolearning.bo.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jp.ac.asojuku.asolearning.bo.InfomationBo;
import jp.ac.asojuku.asolearning.condition.SearchInfomationCondition;
import jp.ac.asojuku.asolearning.config.MessageProperty;
import jp.ac.asojuku.asolearning.dao.InfomationDao;
import jp.ac.asojuku.asolearning.dto.InfomationListDto;
import jp.ac.asojuku.asolearning.dto.InfomationSearchResultDto;
import jp.ac.asojuku.asolearning.dto.LogonInfoDTO;
import jp.ac.asojuku.asolearning.entity.InfoPublicTblEntity;
import jp.ac.asojuku.asolearning.entity.InfomationTblEntity;
import jp.ac.asojuku.asolearning.entity.ResultTblEntity;
import jp.ac.asojuku.asolearning.entity.TaskTblEntity;
import jp.ac.asojuku.asolearning.exception.AsoLearningSystemErrException;
import jp.ac.asojuku.asolearning.exception.DBConnectException;
import jp.ac.asojuku.asolearning.param.RoleId;
import jp.ac.asojuku.asolearning.util.DateUtil;
import jp.ac.asojuku.asolearning.util.Digest;

/**
 * @author nishino
 *
 */
public class InfomationBoImpl implements InfomationBo {
	Logger logger = LoggerFactory.getLogger(InfomationBoImpl.class);


	public List<InfomationSearchResultDto> search(SearchInfomationCondition cond) throws AsoLearningSystemErrException{

		List<InfomationSearchResultDto> list = new ArrayList<InfomationSearchResultDto>();

		InfomationDao dao = new InfomationDao();

		try {

			//DB接続
			dao.connect();

			List<InfomationTblEntity> entityList = dao.searchInfo(cond);

			for( InfomationTblEntity entity : entityList){
				InfomationSearchResultDto dto = getFromEnity(entity);
				list.add(dto);
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

		return list;
	}

	private InfomationSearchResultDto getFromEnity(InfomationTblEntity entity) throws AsoLearningSystemErrException{
		InfomationSearchResultDto dto = new InfomationSearchResultDto();

		dto.setInfomationId(entity.getInfomationId());
		dto.setMaileAddress(entity.getUserEntity().getMailadress());
		dto.setNickName(Digest.decNickName(entity.getUserEntity().getNickName(), entity.getUserEntity().getMailadress()));
		dto.setTitle(entity.getTitle());

		StringBuilder sb = new StringBuilder();

		Date fromDate = new Date();
		Date toDate = new Date();
		for(InfoPublicTblEntity pubEntity : entity.getInfoPublicTblSet() ){
			if( pubEntity.getStatusId() == 1 ){
				fromDate = pubEntity.getPublicDatetime();
				toDate = pubEntity.getEndDatetime();
				sb.append(pubEntity.getCourseMaster().getCourseName()).append(",");
			}

		}
		dto.setPublickCourse(sb.toString());
		dto.setTermFrom( DateUtil.formattedDate(fromDate, "yyyy/MM/dd") );
		dto.setTermTo( DateUtil.formattedDate(toDate, "yyyy/MM/dd") );

		return dto;
	}

	/* (非 Javadoc)
	 * @see jp.ac.asojuku.asolearning.bo.InfomationBo#get(jp.ac.asojuku.asolearning.dto.LogonInfoDTO)
	 */
	@Override
	public InfomationListDto get(LogonInfoDTO logon) throws AsoLearningSystemErrException {

		InfomationListDto dto = new InfomationListDto();
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
			/////////////////////////////
			//最近追加されたコメント
			List<ResultTblEntity> resultList = dao.getUpdateComment(logon.getUserId());
			for( ResultTblEntity result : resultList){
				TaskTblEntity task = result.getTaskTbl();
				dto.addInfoList(
					String.format(MessageProperty.getInstance().getProperty(MessageProperty.INFO_RECENT_COMMENT),task.getTaskId(),task.getName())
					);
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
