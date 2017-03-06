/**
 *
 */
package jp.ac.asojuku.asolearning.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import jp.ac.asojuku.asolearning.condition.SearchHistoryCondition;
import jp.ac.asojuku.asolearning.entity.ActionMasterEntity;
import jp.ac.asojuku.asolearning.entity.HistoryTblEntity;
import jp.ac.asojuku.asolearning.entity.UserTblEntity;
import jp.ac.asojuku.asolearning.util.SqlDateUtil;

/**
 * @author nishino
 *
 */
public class HistoryDao extends Dao {

	public HistoryDao() {
	}
	public HistoryDao(Connection con) {
		super(con);
	}

	// 履歴の登録
	private static final String HISTORY_INSERT_SQL =
			 "INSERT INTO HISTORY_TBL "
			+ "(HISTORY_ID,USER_ID,ACTION_ID,MESSAGE,ACTION_DATE) "
			+ "VALUES(null,?,?,?,CURRENT_TIMESTAMP)";

	// 履歴の登録
	private static final String HISTORY_SEARCH_SQL =
			   "SELECT * FROM HISTORY_TBL h "
			 + "LEFT JOIN USER_TBL u ON(h.USER_ID=u.USER_ID)"
			 + "LEFT JOIN ACTION_MASTER am (h.ACTION_ID=u.ACTION_ID) ";
	private static final String HISTORY_SEARCH_WHERE1 = " h.USER_ID=? ";
	private static final String HISTORY_SEARCH_WHERE2 = " h.ACTION_ID=? ";
	private static final String HISTORY_SEARCH_WHERE3 = " h.ACTION_DATE > ? ";
	private static final String HISTORY_SEARCH_WHERE4 = " h.ACTION_DATE < ? ";


	/**
	 * 履歴の挿入
	 * @param userId
	 * @param AcctionId
	 * @throws SQLException
	 */
	public void insert(Integer userId,Integer AcctionId,String message) throws SQLException{

		if( con == null ){
			return;
		}

		PreparedStatement ps = null;

        try {
        	ps = con.prepareStatement(HISTORY_INSERT_SQL);

        	//パラメータセット
        	ps.setInt(1, userId);
        	ps.setInt(2, AcctionId);
        	ps.setString(3, message);

			ps.executeUpdate();

		} catch (SQLException e) {
			//例外発生時はログを出力し、上位へそのままスロー
			logger.warn("SQLException:",e);
			throw e;
		} finally {
			safeClose(ps,null);
		}
	}

	/**
	 * 履歴の検索
	 * @param cond
	 * @throws SQLException
	 * @throws ParseException
	 */
	public List<HistoryTblEntity> search(SearchHistoryCondition cond) throws SQLException, ParseException{

		List<HistoryTblEntity> list =new ArrayList<HistoryTblEntity>();
		if( con == null ){
			return list;
		}

		PreparedStatement ps = null;
		ResultSet rs = null;

        try {
        	StringBuffer sb = new StringBuffer(HISTORY_SEARCH_SQL);

        	sb.append(setWhere(cond));

        	ps = con.prepareStatement(sb.toString());

        	//パラメータセット
        	setParams(ps,cond);

	        // SQLを実行
	        rs = ps.executeQuery();

	        //値を取り出す
	        while(rs.next()){
	        	HistoryTblEntity entity = new HistoryTblEntity();
	        	UserTblEntity userEntity = new UserTblEntity();
	        	ActionMasterEntity actionEntity = new ActionMasterEntity();

	        	userEntity.setUserId(rs.getInt("USER_ID"));
	        	userEntity.setMailadress(rs.getString("MAILADRESS"));
	        	userEntity.setPassword(rs.getString("PASSWORD"));
	        	userEntity.setName(rs.getString("NAME"));
	        	userEntity.setNickName(rs.getString("NICK_NAME"));
	        	userEntity.setAccountExpryDate(rs.getDate("ACCOUNT_EXPRY_DATE"));
	        	userEntity.setPasswordExpirydate(rs.getDate("PASSWORD_EXPIRYDATE"));
	        	userEntity.setIsFirstFlg(rs.getInt("IS_FIRST_FLG"));
	        	userEntity.setCertifyErrCnt(rs.getInt("CERTIFY_ERR_CNT"));
	    		userEntity.setIsLockFlg(rs.getInt("IS_LOCK_FLG"));
	    		userEntity.setEntryDate(rs.getTimestamp("ENTRY_DATE"));
	    		userEntity.setRemark(rs.getString("REMARK"));
	    		userEntity.setUpdateDate(rs.getTimestamp("UPDATE_DATE"));
	    		Integer admissionYear = fixInt(rs.getInt("ADMISSION_YEAR"),rs.wasNull());
	    		userEntity.setAdmissionYear(admissionYear);
	    		userEntity.setRepeatYearCount(rs.getInt("REPEAT_YEAR_COUNT"));
	    		Integer graduateYear = fixInt(rs.getInt("GRADUATE_YEAR"),rs.wasNull());
	    		userEntity.setGraduateYear(graduateYear);
	    		Integer giveupYear = fixInt(rs.getInt("GIVE_UP_YEAR"),rs.wasNull());
	    		userEntity.setGiveUpYear(giveupYear);

	    		actionEntity.setActionId(rs.getInt("ACTION_ID"));
	    		actionEntity.setActionName(rs.getString("ACTION_NAME"));

	    		entity.setUserTbl(userEntity);
	    		entity.setActionMaster(actionEntity);
	    		entity.setActionDate(rs.getTimestamp("ACTION_DATE"));
	    		entity.setMessage(rs.getString("MESSAGE"));

	    		list.add(entity);
	        }

		} catch (SQLException e) {
			//例外発生時はログを出力し、上位へそのままスロー
			logger.warn("SQLException:",e);
			throw e;
		} catch (ParseException e) {
			//例外発生時はログを出力し、上位へそのままスロー
			logger.warn("ParseException:",e);
			throw e;
		} finally {
			safeClose(ps,null);
		}

        return list;
	}

	private String setWhere(SearchHistoryCondition cond){
		StringBuffer sb = new StringBuffer();

		if( cond.getUserId() != null){
			appendWhereWithAnd(sb,HISTORY_SEARCH_WHERE1);
		}
		if( cond.getActionId() != null){
			appendWhereWithAnd(sb,HISTORY_SEARCH_WHERE2);
		}
		if( cond.getFromDate() != null){
			appendWhereWithAnd(sb,HISTORY_SEARCH_WHERE3);
		}
		if( cond.getToDate() != null){
			appendWhereWithAnd(sb,HISTORY_SEARCH_WHERE4);
		}

		if( sb.length() > 0 ){
			sb.append(" WHERE ");
		}

		return sb.toString();
	}

	private void setParams(PreparedStatement ps,SearchHistoryCondition cond) throws SQLException, ParseException{
		int index = 1;

		if( cond.getUserId() != null){
			ps.setInt(index++, cond.getUserId());
		}
		if( cond.getActionId() != null){
			ps.setInt(index++, cond.getActionId());
		}
		if( cond.getFromDate() != null){
			ps.setDate(index++, SqlDateUtil.getDateFrom(cond.getFromDate(), "yyyy/MM/dd"));
		}
		if( cond.getToDate() != null){
			ps.setDate(index++, SqlDateUtil.getDateFrom(cond.getToDate(), "yyyy/MM/dd"));
		}
	}
}
