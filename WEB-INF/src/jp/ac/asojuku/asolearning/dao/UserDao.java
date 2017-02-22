/**
 *
 */
package jp.ac.asojuku.asolearning.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jp.ac.asojuku.asolearning.entity.CourseMasterEntity;
import jp.ac.asojuku.asolearning.entity.RoleMasterEntity;
import jp.ac.asojuku.asolearning.entity.UserTblEntity;

/**
 * ユーザーテーブルのDAO
 * @author nishino
 *
 */
public class UserDao extends Dao {
	Logger logger = LoggerFactory.getLogger(UserDao.class);

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




	public UserDao() {
	}
	public UserDao(Connection con) {
		super(con);
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
}
