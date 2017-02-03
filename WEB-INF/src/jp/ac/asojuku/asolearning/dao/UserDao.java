/**
 *
 */
package jp.ac.asojuku.asolearning.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jp.ac.asojuku.asolearning.entity.CourseMasterEntity;
import jp.ac.asojuku.asolearning.entity.RoleMasterEntity;
import jp.ac.asojuku.asolearning.entity.UserTblEntity;

/**
 * ユーザーテーブルのDAO
 * @author nishino
 *
 */
public class UserDao extends Dao {

	// ユーザーIDとパスワードを指定してユーザー情報を取得する
	private static final String MEMBER_INFO_BY_UP_SQL =
			"SELECT * FROM user_tbl u "
			+ "LEFT JOIN COURSE_MASTER c ON(c.COURSE_ID = u.COURSE_ID) "
			+ "LEFT JOIN ROLE_MASTER r ON(r.ROLE_ID = u.ROLE_ID) "
			+ "WHERE u.MAILADRESS=? AND u.PASSWORD=?";
	private static final int MEMBER_INFO_BY_UP_NAME_IDX = 1;
	private static final int MEMBER_INFO_BY_UP_PASS_IDX = 2;

	// ユーザーIDとパスワードを指定してユーザー情報を取得する
	private static final String MEMBER_INFO_BY_UP_SQL2 =
			"SELECT * FROM user_tbl u "
			+ "LEFT JOIN COURSE_MASTER c ON(c.COURSE_ID = u.COURSE_ID) "
			+ "LEFT JOIN ROLE_MASTER r ON(r.ROLE_ID = u.ROLE_ID) "
			+ "WHERE u.MAILADRESS=?";

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
        	try {
		        // 接続を閉じる
	        	if( rs != null ){
					rs.close();
	        	}
	        	if( ps != null ){
		        	ps.close();
	        	}
			} catch (SQLException e) {
				;	//closeの失敗は無視
			}
		}

		return entity;
	}

	public UserTblEntity getUserInfoByUserName(String userName) throws SQLException{

		if( con == null ){
			return null;
		}

		PreparedStatement ps = null;
		ResultSet rs = null;
		UserTblEntity entity = null;

        try {
    		// ステートメント生成
			ps = con.prepareStatement(MEMBER_INFO_BY_UP_SQL2);

	        ps.setString(MEMBER_INFO_BY_UP_NAME_IDX, userName);

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
        	try {
		        // 接続を閉じる
	        	if( rs != null ){
					rs.close();
	        	}
	        	if( ps != null ){
		        	ps.close();
	        	}
			} catch (SQLException e) {
				;	//closeの失敗は無視
			}
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
