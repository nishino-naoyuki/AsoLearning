/**
 *
 */
package jp.ac.asojuku.asolearning.bo.impl;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jp.ac.asojuku.asolearning.bo.HistoryBo;
import jp.ac.asojuku.asolearning.condition.SearchHistoryCondition;
import jp.ac.asojuku.asolearning.dao.HistoryDao;
import jp.ac.asojuku.asolearning.dto.HistoryDto;
import jp.ac.asojuku.asolearning.entity.HistoryTblEntity;
import jp.ac.asojuku.asolearning.entity.UserTblEntity;
import jp.ac.asojuku.asolearning.exception.AsoLearningSystemErrException;
import jp.ac.asojuku.asolearning.exception.DBConnectException;
import jp.ac.asojuku.asolearning.util.DateUtil;
import jp.ac.asojuku.asolearning.util.Digest;

/**
 * @author nishino
 *
 */
public class HistoryBoImpl implements HistoryBo {
	Logger logger = LoggerFactory.getLogger(HistoryBoImpl.class);

	/* (非 Javadoc)
	 * @see jp.ac.asojuku.asolearning.bo.HistoryBo#insert(java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public void insert(Integer userId, Integer actionId,String message) throws AsoLearningSystemErrException {

		Logger logger = LoggerFactory.getLogger(HistoryBoImpl.class);

		HistoryDao dao = new HistoryDao();

		try {

			//DB接続
			dao.connect();

			//挿入
			dao.insert(userId, actionId,message);

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
	}

	@Override
	public List<HistoryDto> getList(SearchHistoryCondition condition,int offset,int num) throws AsoLearningSystemErrException {

		List<HistoryDto> list = new ArrayList<HistoryDto> ();
		HistoryDao dao = new HistoryDao();

		try {

			//DB接続
			dao.connect();

			//履歴リストを取得
			List<HistoryTblEntity> entityList = dao.search(condition,offset,num);

			//Entity -> Dto
			for( HistoryTblEntity entiy : entityList ){
				HistoryDto dto = new HistoryDto();
				UserTblEntity user = entiy.getUserTbl();

				dto.setActionName(entiy.getActionMaster().getActionName());
				dto.setMessage(entiy.getMessage());
				dto.setCourseName(entiy.getUserTbl().getCourseMaster().getCourseName());
				dto.setMailAddress(entiy.getUserTbl().getMailadress());
				dto.setUserName(user.getName());
				dto.setNickName( Digest.decNickName(user.getNickName(), user.getMailadress()) );
				dto.setActionDate(DateUtil.formattedDate(entiy.getActionDate(), "yyyy/MM/dd HH:mm:ss"));

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
		} catch (ParseException e) {
			//ログ出力
			logger.warn("パースエラー：",e);
			throw new AsoLearningSystemErrException(e);
		} finally{

			dao.close();
		}

		return list;
	}

}
