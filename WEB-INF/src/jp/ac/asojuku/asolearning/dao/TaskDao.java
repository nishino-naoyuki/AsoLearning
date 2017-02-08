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
import jp.ac.asojuku.asolearning.entity.TestcaseTableEntity;

/**
 * 課題DAO
 * @author nishino
 *
 */
public class TaskDao extends Dao {

	//ユーザーを指定して、課題一覧を取得するSQL
	private static final String TASK_LIST_SQL =
			"SELECT * FROM TASK_TBL t "
			+ "LEFT JOIN TASK_PUBLIC_TBL tp ON(t.TASK_ID = tp.TASK_ID) "
			+ "LEFT JOIN PUBLIC_STATUS_MASTER ps ON(tp.STATUS_ID = ps.STATUS_ID) "
			+ "LEFT JOIN TESTCASE_TABLE tc ON(tc.TASK_ID = tc.TASK_ID) "
			+ "LEFT JOIN RESULT_TBL r ON(t.TASK_ID = r.TASK_ID AND r.user_ID=?) "
			+ "WHERE tp.COURSE_ID=? AND tp.STATUS_ID IN(1,2) ";
	private static final int TASK_LIST_SQL_USER_IDX = 1;
	private static final int TASK_LIST_SQL_COURCE_IDX = 2;

	//ユーザーと課題IDを指定して、課題を取得するSQL
	private static final String TASK_DETAIL_SQL =
			"SELECT * FROM TASK_TBL t "
			+ "LEFT JOIN TASK_PUBLIC_TBL tp ON(t.TASK_ID = tp.TASK_ID) "
			+ "LEFT JOIN PUBLIC_STATUS_MASTER ps ON(tp.STATUS_ID = ps.STATUS_ID) "
			+ "LEFT JOIN TESTCASE_TABLE tc ON(tc.TASK_ID = tc.TASK_ID) "
			+ "LEFT JOIN RESULT_TBL r ON(t.TASK_ID = r.TASK_ID AND r.user_ID=?) "
			+ "WHERE tp.COURSE_ID=? AND tp.STATUS_ID IN(1,2) AND t.TASK_ID = ? ";
	private static final int TASK_DETAIL_SQL_USER_IDX = 1;
	private static final int TASK_DETAIL_SQL_COURCE_IDX = 2;
	private static final int TASK_DETAIL_SQL_TASKID_IDX = 3;

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
	        int taskId = -1;
	        int wkTaskId = 0;
	        boolean bufferFlg = false;

	        while(rs.next()){
	        	wkTaskId = rs.getInt("TASK_ID");

	        	//同じテストIDの間はテストケースのみため込む
	        	if( taskId == wkTaskId ){
	        		bufferFlg = true;
	        	}else{
	        		//テストケースが変わったら、テスト情報をセットする
	        		if( bufferFlg == true ){
	        			list.add(entity);
	        			bufferFlg = false;
	        		}
	        		entity = createTaskTblEntityFromResultSet(rs);
	        		taskId = wkTaskId;
	        	}
	        	//テストケースを追加
	        	addTestCaseEntity(entity,rs);
	        }

	        //最後の１件はループの外で登録する
    		list.add(entity);

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
	 * 課題情報の取得
	 *
	 * @param studentId
	 * @param courseId
	 * @param taskid
	 * @return
	 * @throws SQLException
	 */
	public TaskTblEntity getTaskDetal(int studentId,int courseId,int taskid) throws SQLException{

		if( con == null ){
			return null;
		}
		TaskTblEntity taskDetail = new TaskTblEntity();

		PreparedStatement ps = null;
		ResultSet rs = null;

        try {
    		// ステートメント生成
			ps = con.prepareStatement(TASK_DETAIL_SQL);

	        ps.setInt(TASK_DETAIL_SQL_USER_IDX, studentId);
	        ps.setInt(TASK_DETAIL_SQL_COURCE_IDX, courseId);
	        ps.setInt(TASK_DETAIL_SQL_TASKID_IDX, taskid);

	        // SQLを実行
	        rs = ps.executeQuery();


	        //値を取り出す
	        while(rs.next()){
	        	taskDetail = createTaskTblEntityFromResultSet(rs);
	        	//テストケースを追加
	        	addTestCaseEntity(taskDetail,rs);
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


		return taskDetail;
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

		//テストケース

		//entity.setTestcaseTableSet(Set<TestcaseTableEntity>);

		return entity;
	}

	/**
	 * テストケースの追加
	 * @param entity
	 * @param rs
	 * @throws SQLException
	 */
	private void addTestCaseEntity(TaskTblEntity entity,ResultSet rs) throws SQLException{

		TestcaseTableEntity testcase = new TestcaseTableEntity();

		Integer totalScore = fixInt(rs.getInt("TESTCASE_ID"),rs.wasNull());
		if(totalScore != null ){
			testcase.setTestcaseId(totalScore);
			testcase.setAllmostOfMarks(rs.getInt("ALLMOST_OF_MARKS"));
			testcase.setOutputFileName(rs.getString("OUTPUT_FILE_NAME"));
			testcase.setInputFileName(rs.getString("INPUT_FILE_NAME"));

			entity.addTestcaseTable(testcase);
		}
	}

}
