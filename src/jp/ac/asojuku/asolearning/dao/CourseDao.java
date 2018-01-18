/**
 *
 */
package jp.ac.asojuku.asolearning.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.ac.asojuku.asolearning.entity.CourseMasterEntity;

/**
 * 学科データ
 * @author nishino
 *
 */
public class CourseDao extends Dao {

	// ユーザーIDとパスワードを指定してユーザー情報を取得する
	private static final String COURSE_LIST_SQL =
			"SELECT * FROM COURSE_MASTER";

	public CourseDao() {
	}
	public CourseDao(Connection con) {
		super(con);
	}

	public List<CourseMasterEntity> getCourseList() throws SQLException{

		if( con == null ){
			return null;
		}

		PreparedStatement ps = null;
		ResultSet rs = null;
		List<CourseMasterEntity> courseList = new ArrayList<CourseMasterEntity>();

        try {
    		// ステートメント生成
			ps = con.prepareStatement(COURSE_LIST_SQL);

	        // SQLを実行
	        rs = ps.executeQuery();

	        //値を取り出す
	        while(rs.next()){
	        	CourseMasterEntity entity = new CourseMasterEntity();

	        	entity.setCourseId(rs.getInt("COURSE_ID"));
	        	entity.setCourseName(rs.getString("COURSE_NAME"));

	        	courseList.add(entity);
	        }

		} catch (SQLException e) {
			//例外発生時はログを出力し、上位へそのままスロー
			throw e;

		} finally {
			safeClose(ps,rs);
		}

		return courseList;
	}

}
