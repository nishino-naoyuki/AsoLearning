/**
 *
 */
package jp.ac.asojuku.asolearning.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import jp.ac.asojuku.asolearning.entity.PublicStatusMasterEntity;
import jp.ac.asojuku.asolearning.entity.ResultTblEntity;
import jp.ac.asojuku.asolearning.entity.TaskPublicTblEntity;
import jp.ac.asojuku.asolearning.entity.TaskTblEntity;
import jp.ac.asojuku.asolearning.entity.TestcaseTableEntity;
import jp.ac.asojuku.asolearning.entity.UserTblEntity;

/**
 * 課題DAO
 * @author nishino
 *
 */
public class TaskDao extends Dao {

	//ユーザーを指定して、課題一覧を取得するSQL
	private static final String TASK_COURSE_LIST_SQL =
			"SELECT * FROM TASK_TBL t "
			+ "LEFT JOIN TASK_PUBLIC_TBL tp ON(t.TASK_ID = tp.TASK_ID) "
			+ "LEFT JOIN PUBLIC_STATUS_MASTER ps ON(tp.STATUS_ID = ps.STATUS_ID) "
			+ "WHERE tp.STATUS_ID IN(1,2) ";
	private static final String TASK_COURSE_LIST_WHERE = " tp.COURSE_ID=? ";
	private static final String TASK_COURSE_LIST_ORDERBY =  " ORDER BY t.TASK_ID";
	private static final int TASK_COURSE_LIST_SQL_USER_IDX = 1;

	//ユーザーを指定して、課題一覧を取得するSQL
	private static final String TASK_LIST_SQL =
			"SELECT * FROM TASK_TBL t "
			+ "LEFT JOIN TASK_PUBLIC_TBL tp ON(t.TASK_ID = tp.TASK_ID) "
			+ "LEFT JOIN PUBLIC_STATUS_MASTER ps ON(tp.STATUS_ID = ps.STATUS_ID) "
			+ "LEFT JOIN TESTCASE_TABLE tc ON(tc.TASK_ID = tc.TASK_ID) "
			+ "LEFT JOIN RESULT_TBL r ON(t.TASK_ID = r.TASK_ID AND r.user_ID=?) "
			+ "WHERE tp.COURSE_ID=? AND tp.STATUS_ID IN(1,2) "
			+ "ORDER BY t.TASK_ID";
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
	//挿入SQL
	private static final String TASK_INSERT_SQL =
			"INSERT INTO TASK_TBL "
			+ "(TASK_ID,NAME,TASK_QUESTION,CREATE_USER_ID,ENTRY_DATE,UPDATE_TIM) "
			+ "VALUES(null,?,?,?,CURRENT_DATE,CURRENT_DATE) ";
	private static final String TASKTESTCASE_INSERT_SQL =
			"INSERT INTO TESTCASE_TABLE "
			+ "(TASK_ID,TESTCASE_ID,ALLMOST_OF_MARKS,OUTPUT_FILE_NAME,INPUT_FILE_NAME) "
			+ "VALUES(?,?,?,?,?) ";
	private static final String TASKPUBLIC_INSERT_SQL =
			"INSERT INTO TASK_PUBLIC_TBL "
			+ "(TASK_ID,COURSE_ID,STATUS_ID,PUBLIC_DATETIME,END_DATETIME) "
			+ "VALUES(?,?,?,?,?) ";

	private static final String TASK_NAME_SQL =
			"SELECT * FROM TASK_TBL t "
			+ "LEFT JOIN TASK_PUBLIC_TBL tp ON(t.TASK_ID = tp.TASK_ID) "
			+ "LEFT JOIN PUBLIC_STATUS_MASTER ps ON(tp.STATUS_ID = ps.STATUS_ID) "
			+ "LEFT JOIN TESTCASE_TABLE tc ON(tc.TASK_ID = tc.TASK_ID) "
			+ "LEFT JOIN RESULT_TBL r ON(t.TASK_ID = r.TASK_ID) "
			+ "WHERE t.NAME=?  ";
	private static final int TASK_NAME_SQL_NAMEIDX = 1;



	public TaskDao() {
	}
	public TaskDao(Connection con) {
		super(con);
	}

	/**
	 * 学科を指定して、課題の一覧を取得する
	 * 指定が無い場合は全部の課題を取得する
	 *
	 * @param courseId
	 * @return
	 * @throws SQLException
	 */
	public List<TaskTblEntity> getTaskListByCouseId(Integer courseId) throws SQLException{

		if( con == null ){
			return null;
		}
		List<TaskTblEntity> list = new ArrayList<TaskTblEntity>();

		PreparedStatement ps = null;
		ResultSet rs = null;
		TaskTblEntity entity = null;

        try {
    		// ステートメント生成
        	StringBuffer sb = new StringBuffer(TASK_COURSE_LIST_SQL);

        	if( courseId != null ){
        		sb.append(" AND ");
        		sb.append(TASK_COURSE_LIST_WHERE);
        	}
        	sb.append(TASK_COURSE_LIST_ORDERBY);

			ps = con.prepareStatement(sb.toString());

        	if( courseId != null ){
        		ps.setInt(TASK_COURSE_LIST_SQL_USER_IDX, courseId);
        	}

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
	        		entity = createTaskTblEntityFromResultSet(rs,false);
	        		taskId = wkTaskId;
	        	}
	        }

	        //最後の１件はループの外で登録する
    		list.add(entity);

		} catch (SQLException e) {
			//例外発生時はログを出力し、上位へそのままスロー
			throw e;

		} finally {
			safeClose(ps,rs);
		}


		return list;
	}
	/**
	 * 課題を
	 *
	 * @param name
	 * @return
	 * @throws SQLException
	 */
	public TaskTblEntity getTaskDetal(String name) throws SQLException{

		if( con == null ){
			return null;
		}

		PreparedStatement ps = null;
		ResultSet rs = null;
		TaskTblEntity entity = null;
        try {
    		// ステートメント生成
			ps = con.prepareStatement(TASK_NAME_SQL);

	        ps.setString(TASK_NAME_SQL_NAMEIDX, name);

	        // SQLを実行
	        rs = ps.executeQuery();


	        //値を取り出す
	        while(rs.next()){
	        	entity = createTaskTblEntityFromResultSet(rs,true);
	        	//テストケースを追加
	        	addTestCaseEntity(entity,rs);
	        }

	        return entity;
		} catch (SQLException e) {
			//例外発生時はログを出力し、上位へそのままスロー
			throw e;

		} finally {
			safeClose(ps,rs);
		}
	}

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
	        		entity = createTaskTblEntityFromResultSet(rs,true);
	        		taskId = wkTaskId;
	        	}
	        	//テストケースを追加
	        	addTestCaseEntity(entity,rs);
	        }

	        //最後の１件はループの外で登録する
	        if(entity != null){
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
	        	taskDetail = createTaskTblEntityFromResultSet(rs,true);
	        	//テストケースを追加
	        	addTestCaseEntity(taskDetail,rs);
	        }

		} catch (SQLException e) {
			//例外発生時はログを出力し、上位へそのままスロー
			throw e;

		} finally {
			safeClose(ps,rs);
		}


		return taskDetail;
	}
	/**
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	private TaskTblEntity createTaskTblEntityFromResultSet(ResultSet rs,boolean isNeedResult) throws SQLException{
		TaskTblEntity entity = new TaskTblEntity();

		entity.setTaskId(rs.getInt("TASK_ID"));
		entity.setName(rs.getString("NAME"));
		entity.setTaskQuestion(rs.getString("TASK_QUESTION"));
		entity.setCreateUserId(rs.getInt("CREATE_USER_ID"));
		entity.setEntryDate(rs.getTimestamp("ENTRY_DATE"));
		entity.setUpdateTim(rs.getTimestamp("UPDATE_TIM"));
		entity.setTerminationDate(rs.getDate("termination_date"));

		Set<ResultTblEntity> results = null;
		if( isNeedResult ){
			//結果情報
			Integer totalScore = fixInt(rs.getInt("TOTAL_SCORE"),rs.wasNull());
			if( totalScore != null  ){
				results = new HashSet<ResultTblEntity>();
				ResultTblEntity ret = new ResultTblEntity();
				UserTblEntity user = new UserTblEntity();
				ret.setResultId(rs.getInt("RESULT_ID"));
				ret.setTotalScore(rs.getFloat("TOTAL_SCORE"));
				ret.setHanded(rs.getInt("HANDED"));
				user.setUserId(rs.getInt("USER_ID"));
				ret.setUserTbl(user);
				results.add(ret);

			}
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

		Integer testCaseId = fixInt(rs.getInt("TESTCASE_ID"),rs.wasNull());
		if(testCaseId != null ){
			testcase.setTestcaseId(testCaseId);
			testcase.setAllmostOfMarks(rs.getInt("ALLMOST_OF_MARKS"));
			testcase.setOutputFileName(rs.getString("OUTPUT_FILE_NAME"));
			testcase.setInputFileName(rs.getString("INPUT_FILE_NAME"));

			entity.addTestcaseTable(testcase);
		}
	}

	/**
	 * 課題の挿入
	 * @param entity
	 * @throws SQLException
	 */
	public void insert(String userId,TaskTblEntity entity) throws SQLException{

		if( con == null ){
			return;
		}

		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		PreparedStatement ps3 = null;

        try {
        	this.beginTranzaction();

        	////////////////////////////
        	//TASK_TBL
        	ps1 = con.prepareStatement(TASK_INSERT_SQL);

        	ps1.setString(1, entity.getName());
        	ps1.setString(2, entity.getTaskQuestion());
        	ps1.setString(3, userId);

        	ps1.executeUpdate();

			int taskId = getLastInsertid("TASK_TBL");

        	////////////////////////////
        	//TASKTESTCASE_INSERT_SQL
			Set<TestcaseTableEntity> testCaseSet = entity.getTestcaseTableSet();

			ps2 = con.prepareStatement(TASKTESTCASE_INSERT_SQL);

			int caseId = 1;
			for(TestcaseTableEntity testCase : testCaseSet){

				ps2.setInt(1, taskId);
				ps2.setInt(2, caseId);
				ps2.setInt(3, testCase.getAllmostOfMarks());
				ps2.setString(4, testCase.getOutputFileName());
				if( StringUtils.isNotEmpty(testCase.getInputFileName())){
					ps2.setString(5, testCase.getInputFileName());
				}else{
					ps2.setNull(5, java.sql.Types.DATE);
				}

				ps2.addBatch();
				caseId++;
			}

			ps2.executeBatch();
			//System.out.println("ret2"+ret2.length);

        	////////////////////////////
        	//TASK_PUBLIC_TBL
			Set<TaskPublicTblEntity> taskPublicSet = entity.getTaskPublicTblSet();

			ps3 = con.prepareStatement(TASKPUBLIC_INSERT_SQL);

			for(TaskPublicTblEntity pub : taskPublicSet){

				ps3.setInt(1, taskId);
				ps3.setInt(2, pub.getCourseMaster().getCourseId());
				ps3.setInt(3, pub.getPublicStatusMaster().getStatusId());
				if( pub.getPublicDatetime() != null){
					ps3.setDate(4, parseSQLLDateFromUtilData(pub.getPublicDatetime()) );
				}else{
					ps3.setNull(4, java.sql.Types.DATE);
				}
				if( pub.getEndDatetime() != null){
					ps3.setDate(5, parseSQLLDateFromUtilData(pub.getEndDatetime()) );
				}else{
					ps3.setNull(5, java.sql.Types.DATE);
				}

				ps3.addBatch();
			}
			ps3.executeBatch();
			//System.out.println("ret3"+ret3.length);

	        //コミット
	        this.commit();

		} catch (SQLException e) {
			//例外発生時はログを出力し、上位へそのままスロー
			this.rollback();
			throw e;
		} finally {
			safeClose(ps1,null);
			safeClose(ps2,null);
			safeClose(ps3,null);
		}
	}

}
