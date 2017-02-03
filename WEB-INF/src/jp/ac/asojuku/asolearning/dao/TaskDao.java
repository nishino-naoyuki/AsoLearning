/**
 *
 */
package jp.ac.asojuku.asolearning.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jp.ac.asojuku.asolearning.entity.PublicStatusMasterEntity;
import jp.ac.asojuku.asolearning.entity.ResultTblEntity;
import jp.ac.asojuku.asolearning.entity.TaskPublicTblEntity;
import jp.ac.asojuku.asolearning.entity.TaskTblEntity;

/**
 * 課題DAO
 * @author nishino
 *
 */
public class TaskDao extends Dao {

	// ユーザーIDとパスワードを指定してユーザー情報を取得する
	private static final String TASK_LIST_SQL =
			"SELECT * FROM TASK_TBL t "
			+ "LEFT JOIN TASK_PUBLIC_TBL tp ON(t.TASK_ID = tp.TASK_ID) "
			+ "LEFT JOIN PUBLIC_STATUS_MASTER ps ON(tp.STATUS_ID = ps.STATUS_ID) "
			+ "LEFT JOIN RESULT_TBL r ON(t.TASK_ID = r.TASK_ID AND r.user_ID=?) "
			+ "WHERE tp.COURSE_ID=? AND tp.STATUS_ID IN(1,2) ";
	private static final int TASK_LIST_SQL_USER_IDX = 1;
	private static final int TASK_LIST_SQL_COURCE_IDX = 2;

	public List<TaskTblEntity> getTaskList(int studentId,int courseId,Integer offset,Integer count) throws SQLException{

		if( con == null ){
			return null;
		}
		List<TaskTblEntity> list = new ArrayList<TaskTblEntity>();

		PreparedStatement ps = null;
		ResultSet rs = null;
		TaskTblEntity entity = null;

        try {
    		// ステートメント生成
			ps = con.prepareStatement(TASK_LIST_SQL);

	        ps.setInt(TASK_LIST_SQL_USER_IDX, studentId);
	        ps.setInt(TASK_LIST_SQL_COURCE_IDX, courseId);

	        // SQLを実行
	        rs = ps.executeQuery();


	        //値を取り出す
	        while(rs.next()){
	        	entity = createTaskTblEntityFromResultSet(rs);
	        	list.add(entity);
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


		return list;
	}

	/**
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	private TaskTblEntity createTaskTblEntityFromResultSet(ResultSet rs) throws SQLException{
		TaskTblEntity entity = new TaskTblEntity();

		entity.setTaskId(rs.getInt("TASK_ID"));
		entity.setName(rs.getString("NAME"));
		entity.setTaskQuestion(rs.getString("TASK_QUESTION"));
		entity.setCreateUserId(rs.getInt("CREATE_USER_ID"));
		entity.setEntryDate(rs.getTimestamp("ENTRY_DATE"));
		entity.setUpdateTim(rs.getTimestamp("UPDATE_TIM"));
		entity.setTerminationDate(rs.getDate("termination_date"));

		//結果情報
		Set<ResultTblEntity> results = null;
		Integer totalScore = fixInt(rs.getInt("TOTAL_SCORE"),rs.wasNull());
		if( totalScore != null  ){
			results = new HashSet<ResultTblEntity>();
			ResultTblEntity ret = new ResultTblEntity();
			ret.setTotalScore(rs.getInt("TOTAL_SCORE"));
			results.add(ret);

		}
		entity.setResultTblSet(results);

		//公開情報
		Set<TaskPublicTblEntity> publics = new HashSet<TaskPublicTblEntity>();
		TaskPublicTblEntity p = new TaskPublicTblEntity();
		PublicStatusMasterEntity psm = new PublicStatusMasterEntity();
		psm.setStatusId(rs.getInt("STATUS_ID"));
		psm.setStatusName(rs.getString("STATUS_NAME"));
		p.setPublicStatusMaster(psm);
		publics.add(p);
		entity.setTaskPublicTblSet(publics);
		//entity.setTestcaseTableSet(Set<TestcaseTableEntity>);

		return entity;
	}

}
