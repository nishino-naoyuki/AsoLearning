/**
 *
 */
package jp.ac.asojuku.asolearning.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;

import jp.ac.asojuku.asolearning.condition.SearchHistoryCondition;
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
			+ "(HISTORY_ID,USER_ID,ACTION_ID,ACTION_DATE) "
			+ "VALUES(null,?,?,CURRENT_TIMESTAMP)";

	// 履歴の登録
	private static final String HISTORY_SEARCH_SQL =
			 "SELECT * FROM HISTORY_TBL h ";
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
	public void insert(Integer userId,Integer AcctionId) throws SQLException{

		if( con == null ){
			return;
		}

		PreparedStatement ps = null;

        try {
        	ps = con.prepareStatement(HISTORY_INSERT_SQL);

        	//パラメータセット
        	ps.setInt(1, userId);
        	ps.setInt(2, AcctionId);

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
	public void search(SearchHistoryCondition cond) throws SQLException, ParseException{

		if( con == null ){
			return;
		}

		PreparedStatement ps = null;

        try {
        	StringBuffer sb = new StringBuffer(HISTORY_SEARCH_SQL);

        	sb.append(setWhere(cond));

        	ps = con.prepareStatement(sb.toString());

        	//パラメータセット
        	setParams(ps,cond);

			ps.executeUpdate();

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
