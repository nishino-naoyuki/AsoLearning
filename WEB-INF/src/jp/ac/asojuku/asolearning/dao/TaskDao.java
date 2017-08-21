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
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import jp.ac.asojuku.asolearning.condition.SearchTaskCondition;
import jp.ac.asojuku.asolearning.entity.CourseMasterEntity;
import jp.ac.asojuku.asolearning.entity.PublicStatusMasterEntity;
import jp.ac.asojuku.asolearning.entity.ResultTblEntity;
import jp.ac.asojuku.asolearning.entity.TaskGroupTblEntity;
import jp.ac.asojuku.asolearning.entity.TaskPublicTblEntity;
import jp.ac.asojuku.asolearning.entity.TaskTblEntity;
import jp.ac.asojuku.asolearning.entity.TestcaseTableEntity;
import jp.ac.asojuku.asolearning.entity.UserTblEntity;
import jp.ac.asojuku.asolearning.param.RoleId;

/**
 * 課題DAO
 * @author nishino
 *
 */
public class TaskDao extends Dao {

	//検索条件を指定して課題一覧を取得
	private static final String TASK_LIST_COND_SQL =
			"SELECT * FROM TASK_TBL t "
			+ "LEFT JOIN TASK_GROUP_TBL tg ON(t.TASK_GROUP_ID = tg.TASK_GROUP_ID) "
			+ "LEFT JOIN TASK_PUBLIC_TBL tp ON(t.TASK_ID = tp.TASK_ID) "
			+ "LEFT JOIN PUBLIC_STATUS_MASTER ps ON(tp.STATUS_ID = ps.STATUS_ID) "
			+ "LEFT JOIN USER_TBL u ON(u.USER_ID=t.CREATE_USER_ID) "
			+ "LEFT JOIN COURSE_MASTER cm ON(cm.COURSE_ID=tp.COURSE_ID) ";
	private static final String TASK_LIST_WHERE_TASKNAME = "t.name LIKE ?";
	private static final String TASK_LIST_WHERE_CREATOR = "u.MAILADRESS LIKE ?";
	private static final String TASK_LIST_WHERE_GROUPNAME = "tg.TASK_GROUP_NAME LIKE ?";
	private static final String TASK_LIST_WHERE_GROUPID = "tg.TASK_GROUP_ID = ?";
	private static final String TASK_LIST_WHERE_COURSE = "tp.COURSE_ID=? AND tp.STATUS_ID in (1,2)";
	private static final String TASK_LIST_ORDERBY = " ORDER BY t.NAME,t.TASK_ID,tp.COURSE_ID";

	//ユーザーを指定して、課題一覧を取得するSQL
	private static final String TASK_COURSE_LIST_SQL =
			"SELECT * FROM TASK_TBL t "
			+ "LEFT JOIN TASK_GROUP_TBL tg ON(t.TASK_GROUP_ID = tg.TASK_GROUP_ID) "
			+ "LEFT JOIN TASK_PUBLIC_TBL tp ON(t.TASK_ID = tp.TASK_ID) "
			+ "LEFT JOIN COURSE_MASTER cm ON(cm.COURSE_ID=tp.COURSE_ID) "
			+ "LEFT JOIN PUBLIC_STATUS_MASTER ps ON(tp.STATUS_ID = ps.STATUS_ID) "
			+ "WHERE tp.STATUS_ID IN(1,2) ";
	private static final String TASK_COURSE_LIST_WHERE = " tp.COURSE_ID=? ";
	private static final String TASK_GROUP_LIST_WHERE = " tg.TASK_GROUP_ID=? ";
	private static final String TASK_COURSE_LIST_ORDERBY =  " ORDER BY t.TASK_ID";
	private static final String TASK_GROUPNAME_LIST_ORDERBY =  " ORDER BY tg.TASK_GROUP_NAME";
	private static final int TASK_COURSE_LIST_SQL_USER_IDX = 1;

	//ユーザーを指定して、課題一覧を取得するSQL
	private static final String TASK_LIST_SQL =
			"SELECT * FROM TASK_TBL t "
			+ "LEFT JOIN TASK_PUBLIC_TBL tp ON(t.TASK_ID = tp.TASK_ID) "
			+ "LEFT JOIN COURSE_MASTER cm ON(cm.COURSE_ID=tp.COURSE_ID) "
			+ "LEFT JOIN PUBLIC_STATUS_MASTER ps ON(tp.STATUS_ID = ps.STATUS_ID) "
			+ "LEFT JOIN TESTCASE_TABLE tc ON(t.TASK_ID = tc.TASK_ID) "
			+ "LEFT JOIN RESULT_TBL r ON(t.TASK_ID = r.TASK_ID AND r.user_ID=?) "
			+ "LEFT JOIN TASK_GROUP_TBL tg ON(t.TASK_GROUP_ID = tg.TASK_GROUP_ID) "
			+ "WHERE tp.COURSE_ID=? ";
	private static final String TASK_LIST_WHERE = " AND tp.STATUS_ID IN(1,2) ";
	private static final String TASK_LIST_ORDERBY2 = " ORDER BY t.NAME,t.TASK_ID ";
	private static final int TASK_LIST_SQL_USER_IDX = 1;
	private static final int TASK_LIST_SQL_COURCE_IDX = 2;

	//ユーザーと課題IDを指定して、課題を取得するSQL
	private static final String TASK_DETAIL_SQL =
			"SELECT * FROM TASK_TBL t "
			+ "LEFT JOIN TASK_PUBLIC_TBL tp ON(t.TASK_ID = tp.TASK_ID) "
			+ "LEFT JOIN COURSE_MASTER cm ON(cm.COURSE_ID=tp.COURSE_ID) "
			+ "LEFT JOIN PUBLIC_STATUS_MASTER ps ON(tp.STATUS_ID = ps.STATUS_ID) "
			+ "LEFT JOIN TESTCASE_TABLE tc ON(t.TASK_ID = tc.TASK_ID) "
			+ "LEFT JOIN RESULT_TBL r ON(t.TASK_ID = r.TASK_ID AND r.user_ID=?) "
			+ "WHERE tp.COURSE_ID=? AND t.TASK_ID = ? ";
	private static final String TASK_DETAIL_WHERE_SQL = " AND tp.STATUS_ID IN(1,2) ";
	private static final int TASK_DETAIL_SQL_USER_IDX = 1;
	private static final int TASK_DETAIL_SQL_COURCE_IDX = 2;
	private static final int TASK_DETAIL_SQL_TASKID_IDX = 3;
	//挿入SQL
	private static final String TASK_INSERT_SQL =
			"INSERT INTO TASK_TBL "
			+ "(TASK_ID,NAME,TASK_QUESTION,CREATE_USER_ID,DIFFICALTY,ENTRY_DATE,UPDATE_TIM,TASK_GROUP_ID) "
			+ "VALUES(null,?,?,?,?,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,?) ";
	private static final String TASKTESTCASE_INSERT_SQL =
			"INSERT INTO TESTCASE_TABLE "
			+ "(TASK_ID,TESTCASE_ID,ALLMOST_OF_MARKS,OUTPUT_FILE_NAME,INPUT_FILE_NAME) "
			+ "VALUES(?,?,?,?,?) ";
	private static final String TASKPUBLIC_INSERT_SQL =
			"INSERT INTO TASK_PUBLIC_TBL "
			+ "(TASK_ID,COURSE_ID,STATUS_ID,PUBLIC_DATETIME,END_DATETIME) "
			+ "VALUES(?,?,?,?,?) ";

	//更新SQL
	private static final String TASK_UPDATE_SQL =
			"UPDATE TASK_TBL SET "
			+ "NAME=?,TASK_QUESTION=?,DIFFICALTY=?,UPDATE_TIM=CURRENT_TIMESTAMP "
			+ "WHERE TASK_ID=? ";
	private static final String TASKTESTCASE_UPDATE_SQL =
			"UPDATE TESTCASE_TABLE SET "
			+ "ALLMOST_OF_MARKS=?,OUTPUT_FILE_NAME=?,INPUT_FILE_NAME=? "
			+ "WHERE TASK_ID=? AND TESTCASE_ID=?";
	private static final String TASKPUBLIC_UPDATE_SQL =
			"UPDATE TASK_PUBLIC_TBL SET "
			+ "STATUS_ID=?,PUBLIC_DATETIME=?,END_DATETIME=? "
			+ "WHERE TASK_ID=? AND COURSE_ID=?";
	private static final String TASKTESTCASE_SELECT_SQL =
			"SELECT COUNT(TASK_ID) as c "
			+ "FROM TESTCASE_TABLE tc "
			+ "WHERE tc.TASK_ID = ? AND tc.TESTCASE_ID=?";

	private static final String TASK_NAME_SQL =
			"SELECT * FROM TASK_TBL t "
			+ "LEFT JOIN TASK_PUBLIC_TBL tp ON(t.TASK_ID = tp.TASK_ID) "
			+ "LEFT JOIN COURSE_MASTER cm ON(cm.COURSE_ID=tp.COURSE_ID) "
			+ "LEFT JOIN PUBLIC_STATUS_MASTER ps ON(tp.STATUS_ID = ps.STATUS_ID) "
			+ "LEFT JOIN TESTCASE_TABLE tc ON(t.TASK_ID = tc.TASK_ID) "
			+ "LEFT JOIN RESULT_TBL r ON(t.TASK_ID = r.TASK_ID) "
			+ "WHERE t.NAME=?  ";
	private static final int TASK_NAME_SQL_NAMEIDX = 1;

	private static final String TASK_ID_SQL =
			"SELECT * FROM TASK_TBL t "
			+ "LEFT JOIN TASK_PUBLIC_TBL tp ON(t.TASK_ID = tp.TASK_ID) "
			+ "LEFT JOIN COURSE_MASTER cm ON(cm.COURSE_ID=tp.COURSE_ID) "
			+ "LEFT JOIN PUBLIC_STATUS_MASTER ps ON(tp.STATUS_ID = ps.STATUS_ID) "
			+ "WHERE t.TASK_ID=? ORDER BY t.TASK_ID ,tp.COURSE_ID  ";
	private static final String TASK_ID_TESTCASE_SQL =
			"SELECT * FROM TASK_TBL t "
			+ "LEFT JOIN TESTCASE_TABLE tc ON(t.TASK_ID = tc.TASK_ID) "
			+ "WHERE t.TASK_ID=?  ORDER BY t.TASK_ID ,tc.TESTCASE_ID";



	public TaskDao() {
	}
	public TaskDao(Connection con) {
		super(con);
	}

	/**
	 * 公開状況を更新する
	 * @param taskId
	 * @param taskPublicSet
	 * @throws SQLException
	 */
	public void updatePublicState(Integer taskId,List<TaskPublicTblEntity> taskPublicSet) throws SQLException{

		if( con == null ){
			return;
		}

		PreparedStatement ps = null;

        try {
        	this.beginTranzaction();
			ps = con.prepareStatement(TASKPUBLIC_UPDATE_SQL);

			for(TaskPublicTblEntity pub : taskPublicSet){

				ps.setInt(1, pub.getPublicStatusMaster().getStatusId());
				if( pub.getPublicDatetime() != null){
					ps.setDate(2, parseSQLLDateFromUtilData(pub.getPublicDatetime()) );
				}else{
					ps.setNull(2, java.sql.Types.DATE);
				}
				if( pub.getEndDatetime() != null){
					ps.setDate(3, parseSQLLDateFromUtilData(pub.getEndDatetime()) );
				}else{
					ps.setNull(3, java.sql.Types.DATE);
				}
				ps.setInt(4, taskId);
				ps.setInt(5, pub.getCourseMaster().getCourseId());

				ps.addBatch();
			}
			ps.executeBatch();
			//System.out.println("ret3"+ret3.length);

	        //コミット
	        this.commit();
        } catch (SQLException e) {
			//例外発生時はログを出力し、上位へそのままスロー
			this.rollback();
			throw e;
		} finally {
			safeClose(ps,null);
		}
	}
	/**
	 * 学科を指定して、課題の一覧を取得する
	 * 指定が無い場合は全部の課題を取得する
	 *
	 * @param courseId
	 * @return
	 * @throws SQLException
	 */
	public List<TaskTblEntity> getTaskListBy(SearchTaskCondition condition) throws SQLException{

		if( con == null ){
			return null;
		}
		List<TaskTblEntity> list = new ArrayList<TaskTblEntity>();

		PreparedStatement ps = null;
		ResultSet rs = null;

        try {
    		// ステートメント生成
        	StringBuffer sb = new StringBuffer(TASK_LIST_COND_SQL);
        	//WHERE を追加
        	sb.append(getWhereString(condition));
        	//ORDER BY を追加
        	sb.append(TASK_LIST_ORDERBY);

			ps = con.prepareStatement(sb.toString());

			//値をセット
			setWhereParameter(condition,ps);
	        // SQLを実行
	        rs = ps.executeQuery();

	        //値を取り出す
	        int taskId = -1;
	        int wkTaskId = 0;
			TaskTblEntity entity = null;

	        while(rs.next()){
	        	wkTaskId = rs.getInt("TASK_ID");

	        	//同じテストIDの間はテストケースのみため込む
	        	if( taskId != wkTaskId ){
	        		//テストケースが変わったら、テスト情報をセットする
	        		if( entity != null ){
	        			list.add(entity);
	        		}
	        		entity = createTaskTbl(rs,true);
	        		taskId = wkTaskId;
	        	}
	        	entity.addTaskPublicTbl(createTaskPublicTblEntity(rs));
	        }
	        //最後の１件はループの外で登録する
	        if( entity != null){
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
	 * 検索条件の値をセットする
	 * @param condition
	 * @param ps
	 * @throws SQLException
	 */
	private void setWhereParameter(SearchTaskCondition condition,PreparedStatement ps) throws SQLException{
		int index = 1;

		if( StringUtils.isNotEmpty(condition.getTaskName()) ){
			ps.setString(index++,getLikeString(condition.getTaskName()) );

		}
		if( StringUtils.isNotEmpty(condition.getCreator()) ){
			ps.setString(index++,getLikeString(condition.getCreator()) );
		}
		if(condition.getCourseId() != null){
			ps.setInt(index++, condition.getCourseId());
		}
		if( StringUtils.isNotEmpty(condition.getGroupName()) ){
			ps.setString(index++,getLikeString(condition.getGroupName()) );
		}
		if( condition.getGroupId() != null ){
			ps.setInt(index++, condition.getGroupId());

		}

	}

	/**
	 * @param condition
	 * @return
	 */
	private String getWhereString(SearchTaskCondition condition){
		StringBuffer sb = new StringBuffer();

		if( StringUtils.isNotEmpty(condition.getTaskName()) ){
			sb.append(TASK_LIST_WHERE_TASKNAME);

		}
		if( StringUtils.isNotEmpty(condition.getCreator()) ){
			appendWhereWithAnd(sb,TASK_LIST_WHERE_CREATOR);
		}
		if(condition.getCourseId() != null){
			appendWhereWithAnd(sb,TASK_LIST_WHERE_COURSE);
		}
		if( StringUtils.isNotEmpty(condition.getGroupName()) ){
			appendWhereWithAnd(sb,TASK_LIST_WHERE_GROUPNAME);

		}
		if( condition.getGroupId() != null ){
			appendWhereWithAnd(sb,TASK_LIST_WHERE_GROUPID);

		}

		if( sb.length() > 0 ){
			sb.insert(0, " WHERE ");
		}
		return sb.toString();
	}

	/**
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	private TaskTblEntity createTaskTbl(ResultSet rs,boolean isNeedNickName) throws SQLException{
		TaskTblEntity entity = new TaskTblEntity();

		entity.setTaskId(rs.getInt("TASK_ID"));
		entity.setName(rs.getString("NAME"));
		entity.setTaskQuestion(rs.getString("TASK_QUESTION"));
		entity.setCreateUserId(rs.getInt("CREATE_USER_ID"));
		entity.setEntryDate(rs.getTimestamp("ENTRY_DATE"));
		entity.setUpdateTim(rs.getTimestamp("UPDATE_TIM"));
		entity.setDifficalty(rs.getInt("DIFFICALTY"));
		entity.setTerminationDate(rs.getDate("termination_date"));
		if( isNeedNickName ){
			entity.setCreateUserNickName(rs.getString("NICK_NAME"));
			entity.setMailAddress(rs.getString("MAILADRESS"));
			//※ニックネームの複合にメアドが必要
		}
		//課題グループの情報をセット(nullの場合あり)
		entity.setTaskGroupTbl( createTaskGroupTblEntity(rs) );

		return entity;
	}

	/**
	 * 課題グループをセットする
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	private TaskGroupTblEntity createTaskGroupTblEntity(ResultSet rs) throws SQLException{
		TaskGroupTblEntity tgEntity = new TaskGroupTblEntity();

		Integer grpId = fixInt(rs.getInt("TASK_GROUP_ID"),rs.wasNull());

		if( grpId == null ){
			//グループIDがNULLの場合はデータを採ってきていない
			return null;
		}
		tgEntity.setTaskGroupId(grpId);
		tgEntity.setTaskGroupName(fixString(rs.getString("TASK_GROUP_NAME"),rs.wasNull()));

		return tgEntity;
	}

	/**
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	private TaskPublicTblEntity createTaskPublicTblEntity(ResultSet rs) throws SQLException{

		TaskPublicTblEntity p = new TaskPublicTblEntity();
		PublicStatusMasterEntity psm = new PublicStatusMasterEntity();
		CourseMasterEntity cm = new CourseMasterEntity();

		//公開日と締切
		p.setPublicDatetime(rs.getDate("PUBLIC_DATETIME"));
		p.setEndDatetime(rs.getDate("END_DATETIME"));
		//学科情報
		cm.setCourseId(rs.getInt("COURSE_ID"));
		cm.setCourseName(rs.getString("COURSE_NAME"));
		p.setCourseMaster(cm);
		//公開情報
		psm.setStatusId(rs.getInt("STATUS_ID"));
		psm.setStatusName(rs.getString("STATUS_NAME"));
		p.setPublicStatusMaster(psm);

		return p;
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

	        while(rs.next()){
	        	wkTaskId = rs.getInt("TASK_ID");

	        	//同じテストIDの間はテストケースのみため込む
	        	if( taskId != wkTaskId ){
	        		//テストケースが変わったら、テスト情報をセットする
	        		if( entity != null ){
	        			list.add(entity);
	        		}
	        		entity = createTaskTbl(rs,false);
	        		entity.addTaskPublicTbl(createTaskPublicTblEntity(rs));
	        		taskId = wkTaskId;
	        	}
	        }

	        //最後の１件はループの外で登録する
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
	 * 課題グループを指定して課題の一覧を取得する
	 *
	 * @param taskGrpId
	 * @return
	 * @throws SQLException
	 */
	public List<TaskTblEntity> getTaskListBytaskGroupId(Integer taskGrpId) throws SQLException{

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

        	if( taskGrpId != null ){
        		sb.append(" AND ");
        		sb.append(TASK_GROUP_LIST_WHERE);
        	}
        	sb.append(TASK_GROUPNAME_LIST_ORDERBY);

			ps = con.prepareStatement(sb.toString());

        	if( taskGrpId != null ){
        		ps.setInt(TASK_COURSE_LIST_SQL_USER_IDX, taskGrpId);
        	}

	        // SQLを実行
	        rs = ps.executeQuery();


	        //値を取り出す
	        int taskId = -1;
	        int wkTaskId = 0;

	        while(rs.next()){
	        	wkTaskId = rs.getInt("TASK_ID");

	        	//同じテストIDの間はテストケースのみため込む
	        	if( taskId != wkTaskId ){
	        		//テストケースが変わったら、テスト情報をセットする
	        		if( entity != null ){
	        			list.add(entity);
	        		}
	        		entity = createTaskTbl(rs,false);
	        		entity.addTaskPublicTbl(createTaskPublicTblEntity(rs));
	        		taskId = wkTaskId;
	        	}
	        }

	        //最後の１件はループの外で登録する
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
	 * 課題を課題IDを指定して取得する
	 * Listに入れなおすのが難しいので、1回のSQLで取得せず
	 * 2回SQLを発行する。
	 * 2回目でTASK_TBLと公開情報を、2回目でテストケースの情報を取得する
	 *
	 * @param name
	 * @return
	 * @throws SQLException
	 */
	public TaskTblEntity getTaskDetal(Integer taskId) throws SQLException{

		if( con == null ){
			return null;
		}

		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		ResultSet rs = null;
		TaskTblEntity entity = null;
        try {
        	////////////////////////////////////
    		// ステートメント生成
        	ps1 = con.prepareStatement(TASK_ID_SQL);

        	ps1.setInt(1, taskId);

	        // SQLを実行
	        rs = ps1.executeQuery();


	        //値を取り出す
	        while(rs.next()){
	        	if( entity == null ){
	        		//最初だけテストケース以外の情報を入れる
	        		entity = createTaskTbl(rs,false);
	        	}
	        	//公開情報
	        	entity.addTaskPublicTbl(createTaskPublicTblEntity(rs));
	        }

	        ///////////////////////////////
    		// ステートメント生成
	        ps2 = con.prepareStatement(TASK_ID_TESTCASE_SQL);

	        ps2.setInt(1, taskId);

	        // SQLを実行
	        rs = ps2.executeQuery();


	        //値を取り出す
	        while(rs.next()){
	        	//テストケースを追加
	        	addTestCaseEntity(entity,rs);
	        }
	        return entity;
		} catch (SQLException e) {
			//例外発生時はログを出力し、上位へそのままスロー
			throw e;

		} finally {
			safeClose(ps1,rs);
			safeClose(ps2,rs);
		}
	}

	/**
	 * 課題を課題名を指定して取得する
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
	        	entity = createTaskTbl(rs,false);
        		entity.addTaskPublicTbl(createTaskPublicTblEntity(rs));
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

	public List<TaskTblEntity> getTaskList(Integer studentId,Integer courseId,Integer roleId) throws SQLException{

		if( con == null ){
			return null;
		}
		List<TaskTblEntity> list = new ArrayList<TaskTblEntity>();

		PreparedStatement ps = null;
		ResultSet rs = null;


        try {
    		// ステートメント生成
        	StringBuilder sb = new StringBuilder(TASK_LIST_SQL);

			if( RoleId.STUDENT.equals(roleId)){
				sb.append(TASK_LIST_WHERE);
			}
			sb.append(TASK_LIST_ORDERBY2);

			ps = con.prepareStatement(sb.toString());
	        ps.setInt(TASK_LIST_SQL_USER_IDX, studentId);
	        ps.setInt(TASK_LIST_SQL_COURCE_IDX, courseId);

	        // SQLを実行
	        rs = ps.executeQuery();

	        //値を取り出す
	        int taskId = -1;
	        int wkTaskId = 0;
	        TaskTblEntity entity = null;

	        while(rs.next()){
	        	wkTaskId = rs.getInt("TASK_ID");

	        	//同じテストIDの間はテストケースのみため込む
	        	if( taskId != wkTaskId ){
	        		//テストケースが変わったら、テスト情報をセットする
	        		if( entity != null ){
	        			list.add(entity);
	        		}
	        		entity = createTaskTbl(rs,false);
	        		entity.addTaskPublicTbl(createTaskPublicTblEntity(rs));
	        		entity.addResultTbl(getResultTblEntity(rs));
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
	public TaskTblEntity getTaskDetal(int studentId,int courseId,int taskid,int roleId) throws SQLException{

		if( con == null ){
			return null;
		}

		PreparedStatement ps = null;
		ResultSet rs = null;
		TaskTblEntity taskDetail = null;

        try {
    		// ステートメント生成
        	StringBuilder sb = new StringBuilder(TASK_DETAIL_SQL);
			if( RoleId.STUDENT.equals(roleId)){
				sb.append(TASK_DETAIL_WHERE_SQL);
			}

			ps = con.prepareStatement(sb.toString());

	        ps.setInt(TASK_DETAIL_SQL_USER_IDX, studentId);
	        ps.setInt(TASK_DETAIL_SQL_COURCE_IDX, courseId);
	        ps.setInt(TASK_DETAIL_SQL_TASKID_IDX, taskid);

	        // SQLを実行
	        rs = ps.executeQuery();

	        //値を取り出す
	        while(rs.next()){
	        	if( taskDetail == null ){
	        		//最初だけテストケース以外の情報を入れる
	        		taskDetail = createTaskTbl(rs,false);
	        		taskDetail.addTaskPublicTbl(createTaskPublicTblEntity(rs));
	        		taskDetail.addResultTbl(getResultTblEntity(rs));
	        	}
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
	 * テスト結果の追加
	 * @param entity
	 * @param rs
	 * @throws SQLException
	 */
	private ResultTblEntity getResultTblEntity(ResultSet rs) throws SQLException{

		ResultTblEntity resultEntity = null;

		Integer totalScore = fixInt(rs.getInt("TOTAL_SCORE"),rs.wasNull());
		if( totalScore != null  ){
			resultEntity = new ResultTblEntity();
			UserTblEntity user = new UserTblEntity();
			resultEntity.setResultId(rs.getInt("RESULT_ID"));
			resultEntity.setTotalScore(rs.getFloat("TOTAL_SCORE"));
			resultEntity.setHanded(rs.getInt("HANDED"));
	    	resultEntity.setHandedTimestamp(rs.getTimestamp("HANDED_TIMESTAMP"));
			user.setUserId(rs.getInt("USER_ID"));
			resultEntity.setUserTbl(user);

		}

		return resultEntity;
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
	 * 課題の更新
	 * @param entity
	 * @throws SQLException
	 */
	public void update(Integer userId,TaskTblEntity entity) throws SQLException{

		if( con == null ){
			return;
		}

		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		PreparedStatement ps3 = null;
		PreparedStatement ps4 = null;

        try {
        	this.beginTranzaction();

        	////////////////////////////
        	//TASK_TBL
        	ps1 = con.prepareStatement(TASK_UPDATE_SQL);

        	ps1.setString(1, entity.getName());
        	ps1.setString(2, entity.getTaskQuestion());
        	ps1.setInt(3, entity.getDifficalty());
        	ps1.setInt(4, entity.getTaskId());

        	ps1.executeUpdate();

        	////////////////////////////
        	//TASKTESTCASE_INSERT_SQL
        	updateTestcase(entity);

        	////////////////////////////
        	//TASK_PUBLIC_TBL
			Set<TaskPublicTblEntity> taskPublicSet = entity.getTaskPublicTblSet();

			ps3 = con.prepareStatement(TASKPUBLIC_UPDATE_SQL);

			for(TaskPublicTblEntity pub : taskPublicSet){

				ps3.setInt(1, pub.getPublicStatusMaster().getStatusId());
				if( pub.getPublicDatetime() != null){
					ps3.setDate(2, parseSQLLDateFromUtilData(pub.getPublicDatetime()) );
				}else{
					ps3.setNull(2, java.sql.Types.DATE);
				}
				if( pub.getEndDatetime() != null){
					ps3.setDate(3, parseSQLLDateFromUtilData(pub.getEndDatetime()) );
				}else{
					ps3.setNull(3, java.sql.Types.DATE);
				}
				ps3.setInt(4, entity.getTaskId());
				ps3.setInt(5, pub.getCourseMaster().getCourseId());

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

	/**
	 * テストケースの更新
	 * @param entity
	 * @throws SQLException
	 */
	private void updateTestcase(TaskTblEntity entity) throws SQLException{

		PreparedStatement ps2 = null;
		PreparedStatement ps4 = null;

		Set<TestcaseTableEntity> testCaseSet = entity.getTestcaseTableSet();

		ps2 = con.prepareStatement(TASKTESTCASE_UPDATE_SQL);
		ps4 = con.prepareStatement(TASKTESTCASE_INSERT_SQL);

		int caseId = 1;
		for(TestcaseTableEntity testCase : testCaseSet){

			if( isExistTestcase(entity.getTaskId(),caseId) ){
				//テストケースがある場合はUPDATE文を実行
				ps2.setInt(1, testCase.getAllmostOfMarks());
				ps2.setString(2, testCase.getOutputFileName());
				if( StringUtils.isNotEmpty(testCase.getInputFileName())){
					ps2.setString(3, testCase.getInputFileName());
				}else{
					ps2.setNull(3, java.sql.Types.DATE);
				}
				ps2.setInt(4, entity.getTaskId());
				ps2.setInt(5, caseId);

				ps2.addBatch();
			}else{
				//テストケースが無い場合はINSERT文を実行
				ps4.setInt(1, entity.getTaskId());
				ps4.setInt(2, caseId);
				ps4.setInt(3, testCase.getAllmostOfMarks());
				ps4.setString(4, testCase.getOutputFileName());
				if( StringUtils.isNotEmpty(testCase.getInputFileName())){
					ps4.setString(5, testCase.getInputFileName());
				}else{
					ps4.setNull(5, java.sql.Types.DATE);
				}

				ps4.addBatch();
			}
			caseId++;
		}

		ps2.executeBatch();
		ps4.executeBatch();
	}

	/**
	 * テストケースが存在するかどうかのチェック
	 * @param taskId
	 * @param testcaseId
	 * @return
	 * @throws SQLException
	 */
	private boolean isExistTestcase(int taskId,int testcaseId) throws SQLException{

		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean isExist = false;

		ps = con.prepareStatement(TASKTESTCASE_SELECT_SQL );

		ps.setInt(1, taskId);
		ps.setInt(2, testcaseId);

		rs = ps.executeQuery();

        while(rs.next()){
        	if( rs.getInt("c") > 0 ){
        		isExist = true;
        	}
        }

        return isExist;
	}

	/**
	 * 課題の挿入
	 * @param entity
	 * @throws SQLException
	 */
	public void insert(Integer userId,TaskTblEntity entity) throws SQLException{

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
        	ps1.setInt(3, userId);
        	ps1.setInt(4, entity.getDifficalty());
        	ps1.setInt(5, entity.getTaskGroupTbl().getTaskGroupId());

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
