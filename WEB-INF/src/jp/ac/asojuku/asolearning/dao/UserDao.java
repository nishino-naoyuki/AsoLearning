/**
 *
 */
package jp.ac.asojuku.asolearning.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jp.ac.asojuku.asolearning.condition.SearchUserCondition;
import jp.ac.asojuku.asolearning.entity.CourseMasterEntity;
import jp.ac.asojuku.asolearning.entity.ResultTblEntity;
import jp.ac.asojuku.asolearning.entity.RoleMasterEntity;
import jp.ac.asojuku.asolearning.entity.TaskTblEntity;
import jp.ac.asojuku.asolearning.entity.UserTblEntity;

/**
 * ユーザーテーブルのDAO
 * @author nishino
 *
 */
public class UserDao extends Dao {
	Logger logger = LoggerFactory.getLogger(UserDao.class);


	private static final String MEMBER_DETAIL_SQL =
			"SELECT * FROM USER_TBL u "
			+ "LEFT JOIN COURSE_MASTER c ON(c.COURSE_ID = u.COURSE_ID) "
			+ "LEFT JOIN ROLE_MASTER r ON(r.ROLE_ID = u.ROLE_ID) "
			+ "LEFT JOIN RESULT_TBL ret ON(ret.USER_ID = u.USER_ID) "
			+ "LEFT JOIN TASK_TBL t ON(t.TASK_ID = ret.TASK_ID) "
			+ "WHERE u.GRADUATE_YEAR is null AND u.GIVE_UP_YEAR is null AND u.USER_ID=?";

	private static final String MEMBER_SEARCH_SQL =
			"SELECT * FROM USER_TBL u "
			+ "LEFT JOIN COURSE_MASTER c ON(c.COURSE_ID = u.COURSE_ID) "
			+ "LEFT JOIN ROLE_MASTER r ON(r.ROLE_ID = u.ROLE_ID) "
			+ "LEFT JOIN RESULT_TBL ret ON(ret.USER_ID = u.USER_ID) "
			+ "LEFT JOIN TASK_TBL t ON(t.TASK_ID = ret.TASK_ID) "
			+ "WHERE u.GRADUATE_YEAR is null AND u.GIVE_UP_YEAR is null ";
	private static final String MEMBER_SEARCH_COND1 = " u.NAME LIKE ? ";
	private static final String MEMBER_SEARCH_COND2 = " u.MAILADRESS LIKE ? ";
	private static final String MEMBER_SEARCH_COND3 = " u.COURSE_ID = ? ";
	private static final String MEMBER_SEARCH_COND4 = " u.ROLE_ID = ? ";
	private static final String MEMBER_SEARCH_COND5 = " ret.HANDED = 1 ";
	private static final String MEMBER_SEARCH_COND5_NULL1 = " (ret.HANDED = 0 OR not exists(select USER_ID from RESULT_TBL r2 where r2.USER_ID=u.USER_ID)) ";
	private static final String MEMBER_SEARCH_COND5_NULL2 = " (ret.HANDED = 0 OR not exists(select USER_ID from RESULT_TBL r2 where t.TASK_ID=? and r2.USER_ID=u.USER_ID)) ";
	private static final String MEMBER_SEARCH_COND6 = " t.TASK_ID = ? ";
	private static final String MEMBER_SEARCH_ORDER = " ORDER BY u.USER_ID,u.COURSE_ID,t.TASK_ID";

	// ユーザーIDとパスワードを指定してユーザー情報を取得する
	private static final String MEMBER_INFO_BY_UP_SQL =
			"SELECT * FROM USER_TBL u "
			+ "LEFT JOIN COURSE_MASTER c ON(c.COURSE_ID = u.COURSE_ID) "
			+ "LEFT JOIN ROLE_MASTER r ON(r.ROLE_ID = u.ROLE_ID) "
			+ "WHERE u.MAILADRESS=? AND u.PASSWORD=? "
			+ "AND u.GRADUATE_YEAR is null AND u.GIVE_UP_YEAR is null";
	private static final int MEMBER_INFO_BY_UP_NAME_IDX = 1;
	private static final int MEMBER_INFO_BY_UP_PASS_IDX = 2;

	// ユーザーIDとパスワードを指定してユーザー情報を取得する
	private static final String MEMBER_INFO_BY_UP_SQL2 =
			"SELECT * FROM USER_TBL u "
			+ "LEFT JOIN COURSE_MASTER c ON(c.COURSE_ID = u.COURSE_ID) "
			+ "LEFT JOIN ROLE_MASTER r ON(r.ROLE_ID = u.ROLE_ID) "
			+ "WHERE u.MAILADRESS=? "
			+ "AND u.GRADUATE_YEAR is null AND u.GIVE_UP_YEAR is null";

	// ユーザーの登録
	private static final String MEMBER_ENTR_SQL =
			 "INSERT INTO USER_TBL "
			+ "(USER_ID,MAILADRESS,PASSWORD,NAME,NICK_NAME,ACCOUNT_EXPRY_DATE,PASSWORD_EXPIRYDATE,COURSE_ID,ROLE_ID,IS_FIRST_FLG"
			+ ",CERTIFY_ERR_CNT,IS_LOCK_FLG,ADMISSION_YEAR,GRADUATE_YEAR,REPEAT_YEAR_COUNT,GIVE_UP_YEAR,ENTRY_DATE,UPDATE_DATE) "
			+ "VALUES(null,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP)";

	// ユーザーの更新
	private static final String MEMBER_UPDATE_SQL =
			 "UPDATE USER_TBL SET "
			+ "MAILADRESS=?,"
			+ "PASSWORD=?,"
			+ "NAME=?,"
			+ "NICK_NAME=?,"
			+ "ACCOUNT_EXPRY_DATE=?,"
			+ "PASSWORD_EXPIRYDATE=?,"
			+ "COURSE_ID=?,"
			+ "ROLE_ID=?,"
			+ "IS_FIRST_FLG=?,"
			+ "CERTIFY_ERR_CNT=?,"
			+ "IS_LOCK_FLG=?,"
			+ "ADMISSION_YEAR=?,"
			+ "GRADUATE_YEAR=?,"
			+ "REPEAT_YEAR_COUNT=?,"
			+ "GIVE_UP_YEAR=?,"
			+ "UPDATE_DATE=CURRENT_TIMESTAMP "
			+ "WHERE USER_ID=?";

	// ユーザーの更新
	private static final String MEMBER_PASSWORD_UPDATE_SQL =
			 "UPDATE USER_TBL SET "
			+ "PASSWORD=?,"
			+ "UPDATE_DATE=CURRENT_TIMESTAMP "
			+ "WHERE USER_ID=?";

	// ユーザーの更新
	private static final String MEMBER_NICKNAME_UPDATE_SQL =
			 "UPDATE USER_TBL SET "
			+ "NICK_NAME=?,"
			+ "UPDATE_DATE=CURRENT_TIMESTAMP "
			+ "WHERE USER_ID=?";

	// ユーザーの更新（パスワード失敗回数）
	private static final String MEMBER_PASSERR_UPDATE_SQL =
			 "UPDATE USER_TBL SET "
			+ "CERTIFY_ERR_CNT=?,"
			+ "UPDATE_DATE=CURRENT_TIMESTAMP "
			+ "WHERE USER_ID=?";

	// ユーザーの更新（ロックフラグ）
	private static final String MEMBER_LOCKFLG_UPDATE_SQL =
			 "UPDATE USER_TBL SET "
			+ "IS_LOCK_FLG=?,"
			+ "UPDATE_DATE=CURRENT_TIMESTAMP "
			+ "WHERE USER_ID=?";

	// ユーザーの更新（パスワード有効期限）
	private static final String MEMBER_PASSLIMIT_UPDATE_SQL =
			 "UPDATE USER_TBL SET "
			+ "PASSWORD_EXPIRYDATE=?,"
			+ "UPDATE_DATE=CURRENT_TIMESTAMP "
			+ "WHERE USER_ID=?";


	public UserDao() {
	}
	public UserDao(Connection con) {
		super(con);
	}

	/**
	 * パスワードの有効期限を更新
	 * @param userId
	 * @param limitDate
	 * @throws SQLException
	 */
	public void updatePassLimit(Integer userId,Date limitDate) throws SQLException{

		if( con == null ){
			return;
		}

		PreparedStatement ps = null;

        try {
        	ps = con.prepareStatement(MEMBER_PASSLIMIT_UPDATE_SQL);

        	//パラメータセット
        	ps.setDate(1, parseSQLLDateFromUtilData(limitDate));	//PASSWORD_EXPIRYDATE
        	ps.setInt(2, userId);

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
	 * アカウントロックのフラグを更新
	 * @param userId
	 * @param lockFlg
	 * @throws SQLException
	 */
	public void updateLockFlg(Integer userId,Integer lockFlg) throws SQLException{

		if( con == null ){
			return;
		}

		PreparedStatement ps = null;

        try {
        	ps = con.prepareStatement(MEMBER_LOCKFLG_UPDATE_SQL);

        	//パラメータセット
        	ps.setInt(1, lockFlg);	//CERTIFY_ERR_CNT
        	ps.setInt(2, userId);

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
	 * 認証失敗回数を更新
	 * @param userId
	 * @param count
	 * @throws SQLException
	 */
	public void updateCertErrCnt(Integer userId,Integer count) throws SQLException{

		if( con == null ){
			return;
		}

		PreparedStatement ps = null;

        try {
        	ps = con.prepareStatement(MEMBER_PASSERR_UPDATE_SQL);

        	//パラメータセット
        	ps.setInt(1, count);	//CERTIFY_ERR_CNT
        	ps.setInt(2, userId);

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
	 * パスワードの更新
	 * @param userId
	 * @param hashedPass
	 * @throws SQLException
	 */
	public void updatePassword(Integer userId,String hashedPass) throws SQLException{

		if( con == null ){
			return;
		}

		PreparedStatement ps = null;

        try {
        	ps = con.prepareStatement(MEMBER_PASSWORD_UPDATE_SQL);

        	//パラメータセット
        	ps.setString(1, hashedPass);	//PASSWORD
        	ps.setInt(2, userId);

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
	 * ニックネームの更新
	 * @param userId
	 * @param nickName
	 * @throws SQLException
	 */
	public void updateNickName(Integer userId,String nickName) throws SQLException{

		if( con == null ){
			return;
		}

		PreparedStatement ps = null;

        try {
        	ps = con.prepareStatement(MEMBER_NICKNAME_UPDATE_SQL);

        	//パラメータセット
        	ps.setString(1, nickName);	//nickName
        	ps.setInt(2, userId);

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
	 * ユーザー情報を取得する
	 * @param userId
	 * @return
	 * @throws SQLException
	 */
	public UserTblEntity detail(Integer userId) throws SQLException{

		if( con == null ){
			return null;
		}

		PreparedStatement ps = null;
		ResultSet rs = null;
		UserTblEntity entity = null;

        try {
        	StringBuffer sb = new StringBuffer(MEMBER_DETAIL_SQL);

    		// ステートメント生成
			ps = con.prepareStatement(sb.toString());

			ps.setInt(1, userId);

	        // SQLを実行
	        rs = ps.executeQuery();

	        //値を取り出す
	        while(rs.next()){
	        	if( entity == null ){
		        	entity = createUserTblEntityFromResultSet(rs);
	        	}
	        	entity.addResultTbl(createResultTblEntity(rs));
	        }


		} catch (SQLException e) {
			//例外発生時はログを出力し、上位へそのままスロー
			throw e;

		} finally {
			safeClose(ps,rs);
		}

		return entity;
	}

	public List<UserTblEntity> search(SearchUserCondition cond) throws SQLException{
		List<UserTblEntity> list = new ArrayList<>();

		if( con == null ){
			return null;
		}

		PreparedStatement ps = null;
		ResultSet rs = null;
		UserTblEntity entity = null;

        try {
        	StringBuffer sb = new StringBuffer(MEMBER_SEARCH_SQL);
        	sb.append(getWhereString(cond));
        	sb.append(MEMBER_SEARCH_ORDER);

    		// ステートメント生成
			ps = con.prepareStatement(sb.toString());

			setWhereParameter(cond,ps);

	        // SQLを実行
	        rs = ps.executeQuery();

	        int wkUserId = -1;
	        int userId;
	        //値を取り出す
	        while(rs.next()){
	        	userId = rs.getInt("USER_ID");
	        	if( userId != wkUserId ){
	        		if( entity != null ){
	        			list.add(entity);
	        		}
		        	entity = createUserTblEntityFromResultSet(rs);
		        	wkUserId = userId;
	        	}
	        	entity.addResultTbl(createResultTblEntity(rs));
	        }

    		if( entity != null ){
    			list.add(entity);
    		}

		} catch (SQLException e) {
			//例外発生時はログを出力し、上位へそのままスロー
			throw e;

		} finally {
			safeClose(ps,rs);
		}

		return list;
	}

	/**
	 * @param cond
	 * @param ps
	 * @throws SQLException
	 */
	private void setWhereParameter(SearchUserCondition cond,PreparedStatement ps) throws SQLException{
		int index = 1;

		if( StringUtils.isNotEmpty(cond.getName()) ){
			ps.setString(index++, getLikeString(cond.getName()));

		}
		if( StringUtils.isNotEmpty(cond.getMailaddress()) ){
			ps.setString(index++, getLikeString(cond.getMailaddress()));
		}
		if(cond.getCourseId() != null){
			ps.setInt(index++, cond.getCourseId());
		}
		if(cond.getRoleId() != null){
			ps.setInt(index++, cond.getRoleId());
		}
		if(cond.getHanded() != null){
			if( cond.getHanded() == 0 && cond.getTaskId() != null ){
				ps.setInt(index++, cond.getTaskId());
			}
		}
		//if(cond.getTaskId() != null){
		//	ps.setInt(index++, cond.getTaskId());
		//}
	}

	/**
	 * WHERE分をつくる
	 * @param cond
	 * @return
	 */
	private String getWhereString(SearchUserCondition cond){

		StringBuffer sb = new StringBuffer();

		if( StringUtils.isNotEmpty(cond.getName()) ){
			appendWhereWithAnd(sb,MEMBER_SEARCH_COND1);

		}
		if( StringUtils.isNotEmpty(cond.getMailaddress()) ){
			appendWhereWithAnd(sb,MEMBER_SEARCH_COND2);
		}
		if(cond.getCourseId() != null){
			appendWhereWithAnd(sb,MEMBER_SEARCH_COND3);
		}
		if(cond.getRoleId() != null){
			appendWhereWithAnd(sb,MEMBER_SEARCH_COND4);
		}
		if(cond.getHanded() != null){
			//提出済みかどうかは、判定が複雑
			if( cond.getHanded() == 1 ){
				//提出済みはRESULT_TBLのHANDEDが１であればOK
				appendWhereWithAnd(sb,MEMBER_SEARCH_COND5);
			}else{
				//未提出はRESULT_TBLが存在するかどうかを見る
				//されあに課題IDが指定されている場合は、課題IDを一緒に見る
				if( cond.getTaskId() == null){
					appendWhereWithAnd(sb,MEMBER_SEARCH_COND5_NULL1);
				}else{
					appendWhereWithAnd(sb,MEMBER_SEARCH_COND5_NULL2);
				}
			}
		}
		//if(cond.getTaskId() != null){
		//	appendWhereWithAnd(sb,MEMBER_SEARCH_COND6);
		//}

		if( sb.length() > 0 ){
			sb.insert(0, " AND ");
		}
		return sb.toString();
	}

	/**
	 * ユーザー情報の更新
	 * @param userEntity
	 * @param hashedPass
	 * @throws SQLException
	 */
	public void update(UserTblEntity userEntity,String hashedPass) throws SQLException{

		if( con == null ){
			return;
		}

		PreparedStatement ps = null;

        try {
        	ps = con.prepareStatement(MEMBER_UPDATE_SQL);

        	//パラメータセット
        	ps.setString(1, userEntity.getMailadress());	//MAILADRESS
        	ps.setString(2, hashedPass);	//PASSWORD
        	ps.setString(3, userEntity.getName());	//NAME
        	ps.setString(4, userEntity.getNickName());	//NICK_NAME
			if( userEntity.getAccountExpryDate() != null){
				ps.setDate(5, parseSQLLDateFromUtilData(userEntity.getAccountExpryDate()) );
			}else{
				ps.setNull(5, java.sql.Types.DATE);
			}
			if( userEntity.getPasswordExpirydate() != null){
				ps.setDate(6, parseSQLLDateFromUtilData(userEntity.getPasswordExpirydate()) );
			}else{
				ps.setNull(6, java.sql.Types.DATE);
			}
			ps.setInt(7, userEntity.getCourseMaster().getCourseId());	//COURSE_ID
			ps.setInt(8, userEntity.getRoleMaster().getRoleId());		//ROLE_ID
			ps.setInt(9, userEntity.getIsFirstFlg());	//IS_FIRST_FLG
			ps.setInt(10, 0);	//CERTIFY_ERR_CNT
			ps.setInt(11, 0);	//IS_LOCK_FLG
			if( userEntity.getAdmissionYear() != null){
				ps.setInt(12, userEntity.getAdmissionYear());	//ADMISSION_YEAR
			}else{
				ps.setNull(12, java.sql.Types.INTEGER);	//ADMISSION_YEAR

			}
			if( userEntity.getGraduateYear() != null){
				ps.setInt(13, userEntity.getGraduateYear());	//GRADUATE_YEAR
			}else{
				ps.setNull(13, java.sql.Types.INTEGER);	//GRADUATE_YEAR
			}
			if( userEntity.getRepeatYearCount() != null){
				ps.setInt(14, userEntity.getRepeatYearCount());	//GRADUATE_YEAR
			}else{
				ps.setInt(14, 0);	//REPEAT_YEAR_COUNT

			}
			if( userEntity.getGiveUpYear() != null){
				ps.setInt(15, userEntity.getGiveUpYear());	//GIVE_UP_YEAR
			}else{
				ps.setNull(15, java.sql.Types.INTEGER);	//GIVE_UP_YEAR
			}
			ps.setInt(16, userEntity.getUserId());	//USER_ID

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
	 * ユーザー情報一見挿入
	 * @param userEntity
	 * @param hashedPass
	 * @throws SQLException
	 */
	public void insert(UserTblEntity userEntity,String hashedPass) throws SQLException{

		if( con == null ){
			return;
		}

		PreparedStatement ps = null;

        try {
        	ps = con.prepareStatement(MEMBER_ENTR_SQL);

        	//パラメータセット
        	ps.setString(1, userEntity.getMailadress());
        	ps.setString(2, hashedPass);
        	ps.setString(3, userEntity.getName());
        	ps.setString(4, userEntity.getNickName());
			if( userEntity.getAccountExpryDate() != null){
				ps.setDate(5, parseSQLLDateFromUtilData(userEntity.getAccountExpryDate()) );
			}else{
				ps.setNull(5, java.sql.Types.DATE);
			}
			if( userEntity.getPasswordExpirydate() != null){
				ps.setDate(6, parseSQLLDateFromUtilData(userEntity.getPasswordExpirydate()) );
			}else{
				ps.setNull(6, java.sql.Types.DATE);
			}
			ps.setInt(7, userEntity.getCourseMaster().getCourseId());
			ps.setInt(8, userEntity.getRoleMaster().getRoleId());
			ps.setInt(9, 1);
			ps.setInt(10, 0);	//CERTIFY_ERR_CNT
			ps.setInt(11, 0);	//IS_LOCK_FLG
			if( userEntity.getAdmissionYear() != null){
				ps.setInt(12, userEntity.getAdmissionYear());	//ADMISSION_YEAR
			}else{
				ps.setNull(12, java.sql.Types.INTEGER);	//ADMISSION_YEAR

			}
			if( userEntity.getGraduateYear() != null){
				ps.setInt(13, userEntity.getGraduateYear());	//GRADUATE_YEAR
			}else{
				ps.setNull(13, java.sql.Types.INTEGER);	//GRADUATE_YEAR
			}
			if( userEntity.getRepeatYearCount() != null){
				ps.setInt(14, userEntity.getRepeatYearCount());	//GRADUATE_YEAR
			}else{
				ps.setInt(14, 0);	//REPEAT_YEAR_COUNT
			}
			if( userEntity.getGiveUpYear() != null){
				ps.setInt(15, userEntity.getGiveUpYear());	//GIVE_UP_YEAR
			}else{
				ps.setNull(15, java.sql.Types.INTEGER);	//GIVE_UP_YEAR
			}

			ps.executeUpdate();

		} catch (SQLException e) {
			//例外発生時はログを出力し、上位へそのままスロー
			logger.warn("SQLException:",e);
			throw e;
		} finally {
			safeClose(ps,null);
		}
	}

	public UserTblEntity getUserInfoByUserPassword(String userName,String password) throws SQLException{

		if( con == null ){
			return null;
		}

		PreparedStatement ps = null;
		ResultSet rs = null;
		UserTblEntity entity = null;

        try {
    		// ステートメント生成
			ps = con.prepareStatement(MEMBER_INFO_BY_UP_SQL);

	        ps.setString(MEMBER_INFO_BY_UP_NAME_IDX, userName);
	        ps.setString(MEMBER_INFO_BY_UP_PASS_IDX, password);

	        // SQLを実行
	        rs = ps.executeQuery();


	        //値を取り出す
	        while(rs.next()){
	        	entity = createUserTblEntityFromResultSet(rs);
	        }

		} catch (SQLException e) {
			//例外発生時はログを出力し、上位へそのままスロー
			throw e;

		} finally {
			safeClose(ps,rs);
		}

		return entity;
	}

	public UserTblEntity getUserInfoByMailAddress(String maileAdress) throws SQLException{

		if( con == null ){
			return null;
		}

		PreparedStatement ps = null;
		ResultSet rs = null;
		UserTblEntity entity = null;

        try {
    		// ステートメント生成
			ps = con.prepareStatement(MEMBER_INFO_BY_UP_SQL2);

	        ps.setString(MEMBER_INFO_BY_UP_NAME_IDX, maileAdress);

	        // SQLを実行
	        rs = ps.executeQuery();


	        //値を取り出す
	        while(rs.next()){
	        	entity = createUserTblEntityFromResultSet(rs);
	        }

		} catch (SQLException e) {
			//例外発生時はログを出力し、上位へそのままスロー
			throw e;

		} finally {
			safeClose(ps,rs);
		}

		return entity;
	}
	/**
	 * データベースの結果からUserTblEntityを取得する
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	private UserTblEntity createUserTblEntityFromResultSet(ResultSet rs) throws SQLException {
		UserTblEntity entity = new UserTblEntity();

		entity.setUserId(rs.getInt("USER_ID"));
		entity.setMailadress(rs.getString("MAILADRESS"));
		entity.setPassword(rs.getString("PASSWORD"));
		entity.setName(rs.getString("NAME"));
		entity.setNickName(rs.getString("NICK_NAME"));
		entity.setAccountExpryDate(rs.getDate("ACCOUNT_EXPRY_DATE"));
		entity.setPasswordExpirydate(rs.getDate("PASSWORD_EXPIRYDATE"));
		entity.setIsFirstFlg(rs.getInt("IS_FIRST_FLG"));
		entity.setCertifyErrCnt(rs.getInt("CERTIFY_ERR_CNT"));
		entity.setIsLockFlg(rs.getInt("IS_LOCK_FLG"));
		entity.setEntryDate(rs.getTimestamp("ENTRY_DATE"));
		entity.setUpdateDate(rs.getTimestamp("UPDATE_DATE"));
		Integer admissionYear = fixInt(rs.getInt("ADMISSION_YEAR"),rs.wasNull());
		entity.setAdmissionYear(admissionYear);
		entity.setRepeatYearCount(rs.getInt("REPEAT_YEAR_COUNT"));
		Integer graduateYear = fixInt(rs.getInt("GRADUATE_YEAR"),rs.wasNull());
		entity.setGraduateYear(graduateYear);
		Integer giveupYear = fixInt(rs.getInt("GIVE_UP_YEAR"),rs.wasNull());
		entity.setGiveUpYear(giveupYear);

		CourseMasterEntity cm = new CourseMasterEntity();
		cm.setCourseId(rs.getInt("COURSE_ID"));
		cm.setCourseName(rs.getString("COURSE_NAME"));
		entity.setCourseMaster(cm);

		RoleMasterEntity rm = new RoleMasterEntity();
		rm.setRoleId(rs.getInt("ROLE_ID"));
		rm.setRoleName(rs.getString("ROLE_NAME"));
		entity.setRoleMaster(rm);
		// entity.setHistoryTblSet(Set<HistoryTblEntity>);
		// entity.setResultTblSet(Set<ResultTblEntity>);

		return entity;
	}

	/**
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	private ResultTblEntity createResultTblEntity(ResultSet rs) throws SQLException{

		ResultTblEntity retEntity = new ResultTblEntity();

		retEntity.setResultId(rs.getInt("RESULT_ID"));
		retEntity.setTotalScore(rs.getFloat("TOTAL_SCORE"));
		retEntity.setHanded(rs.getInt("HANDED"));
		retEntity.setHandedTimestamp(rs.getTimestamp("HANDED_TIMESTAMP"));

		TaskTblEntity taskEntity = new TaskTblEntity();

		taskEntity.setTaskId(rs.getInt("TASK_ID"));
		taskEntity.setName(rs.getString("NAME"));
		taskEntity.setTaskQuestion(rs.getString("TASK_QUESTION"));
		taskEntity.setCreateUserId(rs.getInt("CREATE_USER_ID"));
		taskEntity.setEntryDate(rs.getTimestamp("ENTRY_DATE"));
		taskEntity.setUpdateTim(rs.getTimestamp("UPDATE_TIM"));
		taskEntity.setTerminationDate(rs.getDate("termination_date"));

		retEntity.setTaskTbl(taskEntity);

		return retEntity;
	}
}
