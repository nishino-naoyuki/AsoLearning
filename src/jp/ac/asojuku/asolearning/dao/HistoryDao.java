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
import jp.ac.asojuku.asolearning.entity.CourseMasterEntity;
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
			 + "LEFT JOIN ACTION_MASTER am ON(h.ACTION_ID=am.ACTION_ID) "
			 + "LEFT JOIN COURSE_MASTER cm ON(u.COURSE_ID=cm.COURSE_ID) ";
	private static final String HISTORY_SEARCH_WHERE1 = " h.USER_ID=? ";
	private static final String HISTORY_SEARCH_WHERE2 = " h.ACTION_ID=? ";
	private static final String HISTORY_SEARCH_WHERE3 = " h.ACTION_DATE >= ? ";
	private static final String HISTORY_SEARCH_WHERE4 = " h.ACTION_DATE <= ? ";
	private static final String HISTORY_SEARCH_WHERE5 = " u.COURSE_ID = ? ";
	private static final String HISTORY_SEARCH_WHERE6 = " u.ROLE_ID = ? ";
	private static final String HISTORY_SEARCH_WHERE7 = " u.MAILADRESS like ? ";
	private static final String HISTORY_SEARCH_ORDERBY = " ORDER BY h.ACTION_DATE ";
	private static final String HISTORY_SEARCH_LIMIT = " LIMIT ? OFFSET ? ";


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
	 * @param cond　検索条件
	 * @param offset　オフセット
	 * @param num　　取得数（取得件数が0以下の場合は全て取得する）
	 * @throws SQLException
	 * @throws ParseException
	 */
	public List<HistoryTblEntity> search(SearchHistoryCondition cond,int offset,int num) throws SQLException, ParseException{

		List<HistoryTblEntity> list =new ArrayList<HistoryTblEntity>();
		if( con == null ){
			return list;
		}

		PreparedStatement ps = null;
		ResultSet rs = null;

        try {
        	StringBuffer sb = new StringBuffer(HISTORY_SEARCH_SQL);

        	sb.append(setWhere(cond));
        	sb.append(HISTORY_SEARCH_ORDERBY);
        	if( num > 0 ){
        		sb.append(HISTORY_SEARCH_LIMIT);
        	}

        	ps = con.prepareStatement(sb.toString());

        	//パラメータセット
        	setParams(ps,cond,offset,num);

	        // SQLを実行
	        rs = ps.executeQuery();

	        //値を取り出す
	        while(rs.next()){
	        	HistoryTblEntity entity = new HistoryTblEntity();
	        	UserTblEntity userEntity = new UserTblEntity();
	        	ActionMasterEntity actionEntity = new ActionMasterEntity();
	        	CourseMasterEntity courseMaster = new CourseMasterEntity();

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

	    		courseMaster.setCourseId(rs.getInt("COURSE_ID"));
	    		courseMaster.setCourseName(rs.getString("COURSE_NAME"));


	    		actionEntity.setActionId(rs.getInt("ACTION_ID"));
	    		actionEntity.setActionName(rs.getString("ACTION_NAME"));

	    		userEntity.setCourseMaster(courseMaster);
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

	/**
	 * WHERE句の作成
	 * @param cond
	 * @return
	 */
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
		if( cond.getCourseId() != null){
			appendWhereWithAnd(sb,HISTORY_SEARCH_WHERE5);
		}
		if( cond.getRoleId() != null){
			appendWhereWithAnd(sb,HISTORY_SEARCH_WHERE6);
		}
		if( cond.getMail() != null){
			appendWhereWithAnd(sb,HISTORY_SEARCH_WHERE7);
		}

		if( sb.length() > 0 ){
			sb.insert(0, " WHERE ");
		}

		return sb.toString();
	}

	/**
	 * WHERE句に値を入れる
	 * @param ps
	 * @param cond
	 * @throws SQLException
	 * @throws ParseException
	 */
	private void setParams(PreparedStatement ps,SearchHistoryCondition cond,int offset,int num) throws SQLException, ParseException{
		int index = 1;

		if( cond.getUserId() != null){
			ps.setInt(index++, cond.getUserId());
		}
		if( cond.getActionId() != null){
			ps.setInt(index++, cond.getActionId().getId());
		}
		if( cond.getFromDate() != null){
			ps.setDate(index++, SqlDateUtil.getDateFrom(cond.getFromDate(), "yyyy/MM/dd"));
		}
		if( cond.getToDate() != null){
			ps.setDate(index++, SqlDateUtil.getDateFrom(cond.getToDate(), "yyyy/MM/dd"));
		}
		if( cond.getCourseId() != null){
			ps.setInt(index++, cond.getCourseId());
		}
		if( cond.getRoleId() != null){
			ps.setInt(index++, cond.getRoleId().getId());
		}
		if( cond.getMail() != null){
			ps.setString(index++, getLikeString(cond.getMail()));
		}

    	if( num > 0 ){
			//件数とオフセットをセット
			ps.setInt(index++, num);
			ps.setInt(index++, offset);
    	}
	}
}
