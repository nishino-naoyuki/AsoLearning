/**
 *
 */
package jp.ac.asojuku.asolearning.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import jp.ac.asojuku.asolearning.entity.CourseMasterEntity;
import jp.ac.asojuku.asolearning.entity.ResultMetricsTblEntity;
import jp.ac.asojuku.asolearning.entity.ResultTblEntity;
import jp.ac.asojuku.asolearning.entity.ResultTestcaseTblEntity;
import jp.ac.asojuku.asolearning.entity.SrcTblEntity;
import jp.ac.asojuku.asolearning.entity.TaskTblEntity;
import jp.ac.asojuku.asolearning.entity.UserTblEntity;

/**
 * @author nishino
 *
 */
public class ResultDao extends Dao {

	public ResultDao() {
	}
	public ResultDao(Connection con) {
		super(con);
	}


	//指定した課題の結果を削除
	private static final String RESULT_GET_FOR_DELETE_SQL =
			"SELECT RESULT_ID FROM RESULT_TBL r WHERE r.TASK_ID=? ";
	private static final String RESULT_GET_FOR_DELETE_USERID_SQL = " r.USER_ID=? ";
	private static final String RESULT_DELETE_TASKID_SQL =
			"DELETE FROM RESULT_TBL WHERE RESULT_ID=? ";
	private static final String RESULT_TESTCASE_DELETE_TASKID_SQL =
			"DELETE FROM RESULT_TESTCASE_TBL WHERE RESULT_ID=? ";
	private static final String RESULT_METRICS_DELETE_TASKID_SQL =
			"DELETE FROM RESULT_METRICS_TBL WHERE RESULT_ID=? ";

	//検索SQL
	private static final String RESULT_SEARCH_TASKID_SQL =
			"SELECT * "
			+ "FROM RESULT_TBL r "
			+ "LEFT JOIN RESULT_TESTCASE_TBL rt ON(rt.RESULT_ID = r.RESULT_ID) "
			+ "LEFT JOIN RESULT_METRICS_TBL rm ON(rm.RESULT_ID = r.RESULT_ID) "
			+ "LEFT JOIN TASK_TBL t ON(r.TASK_ID = t.TASK_ID) "
			+ "LEFT JOIN USER_TBL u ON(r.USER_ID = u.USER_ID) "
			+ "WHERE r.TASK_ID=? AND u.ROLE_ID=0 AND "
			+ "u.GRADUATE_YEAR is null AND u.GIVE_UP_YEAR is null "
			+ "ORDER BY r.RESULT_ID";
	//検索SQL
	private static final String RESULT_SEARCH_SQL =
			"SELECT * "
			+ "FROM RESULT_TBL r "
			+ "LEFT JOIN RESULT_TESTCASE_TBL rt ON(rt.RESULT_ID = r.RESULT_ID) "
			+ "LEFT JOIN RESULT_METRICS_TBL rm ON(rm.RESULT_ID = r.RESULT_ID) "
			+ "LEFT JOIN TASK_TBL t ON(r.TASK_ID = t.TASK_ID) "
			+ "LEFT JOIN USER_TBL u ON(r.USER_ID = u.USER_ID) "
			+ "WHERE r.USER_ID=? AND r.TASK_ID=? AND "
			+ "u.GRADUATE_YEAR is null AND u.GIVE_UP_YEAR is null "
			+ "ORDER BY r.RESULT_ID";
	private static final int RESULT_SEARCH_USR_ID = 1;
	private static final int RESULT_SEARCH_TASK_ID = 2;

	//ランキングを求めるSQL
	private static final String RESULT_RANKING_SQL_SELECT =
			"SELECT "
			+ "SUM(r.total) as rank_total,"
			+ "u.USER_ID,"
			+ "u.NAME,"
			+ "u.NICK_NAME,"
			+ "u.MAILADRESS,"
			+ "u.ADMISSION_YEAR,"
			+ "u.REPEAT_YEAR_COUNT,"
			+ "u.REMARK,"
			//+ "t.NAME taskname,"
			//+ "t.DIFFICALTY,"
			+ "cm.COURSE_ID, "
			+ "u.GRADE grade,"
			+ "COURSE_NAME "
			+ "FROM "
			+ "("
			+ "   SELECT rt.TASK_ID, rt.RESULT_ID, rt.USER_ID, rt.TOTAL_SCORE*? as total "
			+ "   FROM RESULT_TBL rt LEFT JOIN TASK_TBL t ON( t.TASK_ID = rt.TASK_ID) "
			+ "   WHERE t.DIFFICALTY=0 "
			+ "UNION"
			+ "   SELECT rt.TASK_ID, rt.RESULT_ID, rt.USER_ID, rt.TOTAL_SCORE*? as total "
			+ "   FROM RESULT_TBL rt LEFT JOIN TASK_TBL t ON( t.TASK_ID = rt.TASK_ID) "
			+ "   WHERE t.DIFFICALTY=1 "
			+ "UNION"
			+ "   SELECT rt.TASK_ID, rt.RESULT_ID, rt.USER_ID, rt.TOTAL_SCORE*? as total "
			+ "   FROM RESULT_TBL rt LEFT JOIN TASK_TBL t ON( t.TASK_ID = rt.TASK_ID) "
			+ "   WHERE t.DIFFICALTY=2"
			+ ") r "
			+ "LEFT JOIN USER_TBL u ON( r.USER_ID = u.USER_ID AND u.GRADUATE_YEAR is null AND u.GIVE_UP_YEAR is null) "
			+ "LEFT JOIN TASK_TBL t ON( t.TASK_ID = r.TASK_ID) "
			+ "LEFT JOIN COURSE_MASTER cm ON(u.COURSE_ID = cm.COURSE_ID) "
			+ "WHERE u.ROLE_ID=0 ";
	private static final String RESULT_RANKING_SQL_WHERE_COURSE =
			" u.COURSE_ID=?";
	private static final String RESULT_RANKING_SQL_WHERE_TASK =
			" t.TASK_ID=?";
	private static final String RESULT_RANKING_SQL_WHERE_TASKGRP =
			" t.TASK_GROUP_ID=?";
	private static final String RESULT_RANKING_SQL_WHERE_GRADE =
			" grade=?";
	private static final String RESULT_RANKING_SQL_GROUPBY =
			" GROUP BY u.USER_ID,u.NAME,u.NICK_NAME,u.MAILADRESS,u.MAILADRESS,u.ADMISSION_YEAR,"
			+ "u.REPEAT_YEAR_COUNT,u.REMARK,cm.COURSE_ID,grade,COURSE_NAME ";
	private static final String RESULT_RANKING_SQL_ORDERBY =
			" ORDER BY rank_total DESC";
	//挿入SQL
	private static final String RESULT_INSERT_SQL =
			"INSERT INTO RESULT_TBL "
			+ "(RESULT_ID,USER_ID,TASK_ID,TOTAL_SCORE,HANDED,HANDED_TIMESTAMP,ANSWER) "
			+ "VALUES(null,?,?,?,?,?,?) ";

	private static final String TESTCASE_INSERT_SQL =
			"INSERT INTO RESULT_TESTCASE_TBL "
			+ "(RESULT_ID,TESTCASE_ID,SCORE,MESSAGE) "
			+ "VALUES(?,?,?,?) ";

	private static final String METRICS_INSERT_SQL =
			"INSERT INTO RESULT_METRICS_TBL "
			+ "(RESULT_ID,MAX_MVG,AVR_MVG,MAX_LOC,AVR_LOC,MAX_MVG_SCORE,MAX_LOC_SCORE,AVR_MVG_SCORE,AVR_LOC_SCORE) "
			+ "VALUES(?,?,?,?,?,?,?,?,?) ";

	private static final String SRCTBL_INSERT_SQL =
			"INSERT INTO SRC_TBL "
			+ "(SRC_ID,RESULT_ID,FILE_NAME,SRC) "
			+ "VALUES(null,?,?,?) ";

	//更新SQL
	private static final String RESULT_UPDATE_SQL =
			"UPDATE RESULT_TBL SET "
			+ "USER_ID = ?,"
			+ "TASK_ID = ?,"
			+ "TOTAL_SCORE = ?,"
			+ "HANDED=?,"
			+ "HANDED_TIMESTAMP=?,"
			+ "ANSWER=?  "
			+ "WHERE RESULT_ID=? ";

	private static final String TESTCASE_UPDATE_SQL =
			"UPDATE RESULT_TESTCASE_TBL SET  "
			+ "SCORE = ?,"
			+ "MESSAGE = ? "
			+ "WHERE RESULT_ID=? AND TESTCASE_ID = ? ";

	private static final String METRICS_UPDATE_SQL =
			"UPDATE RESULT_METRICS_TBL SET "
			+ "MAX_MVG = ?,"
			+ "AVR_MVG = ?,"
			+ "MAX_LOC = ?,"
			+ "AVR_LOC = ?,"
			+ "MAX_MVG_SCORE = ?,"
			+ "MAX_LOC_SCORE = ?,"
			+ "AVR_MVG_SCORE = ?,"
			+ "AVR_LOC_SCORE = ? "
			+ "WHERE RESULT_ID = ?";

	private static final String SRCTBL_DELETE_SQL =
			"DELETE FROM SRC_TBL WHERE RESULT_ID=? ";

	//UserIDランキングを求めるSQL
	private static final String RESULT_RANKING_USERID_SQL_SELECT =
			"SELECT "
			+ "TOTAL_SCORE,"
			+ "("
			+ "   SELECT COUNT(*)+1 "
			+ "   FROM RESULT_TBL r2 "
			+ "   WHERE r2.TOTAL_SCORE > r1.TOTAL_SCORE AND r2.TASK_ID=?"
			+ ") rank "
			+ "FROM RESULT_TBL r1 "
			+ "WHERE r1.USER_ID=? AND r1.TASK_ID=?";

	//個人の結果（ヘッダ情報）の一覧を取得する
	private static final String RESULT_LIST_BY_USER =
			"SELECT * FROM RESULT_TBL r "
			+ "LEFT JOIN TASK_TBL t ON(r.TASK_ID = t.TASK_ID) "
			+ "WHERE r.USER_ID = ?";

	//コメントを更新する
	private static final String UPDATE_COMMENT =
			"UPDATE RESULT_TBL SET "
			+ "COMMENT = ?, "
			+ "COMMENT_UPDATE_TIME = CURRENT_TIMESTAMP "
			+ "WHERE RESULT_ID = ?";

	/**
	 * コメントを更新（登録）する
	 *
	 * @param resultId
	 * @param comment
	 * @throws SQLException
	 */
	public void updateComment(Integer resultId,String comment) throws SQLException{

		if( con == null ){
			return;
		}

		PreparedStatement ps = null;

        try {
    		// ステートメント生成
			ps = con.prepareStatement(UPDATE_COMMENT);

			//値をセット
			ps.setString(1, comment);
			ps.setInt(2, resultId);

			ps.executeUpdate();

        } catch (SQLException e) {
			//例外発生時はログを出力し、上位へそのままスロー
			throw e;
		}finally {
			safeClose(ps,null);
		}
	}
	/**
	 * 結果リスト
	 * @param userId
	 * @return
	 * @throws SQLException
	 */
	public List<ResultTblEntity> getListByUser(int userId) throws SQLException{

		List<ResultTblEntity> list = new ArrayList<>();

		PreparedStatement ps = null;
		ResultSet rs = null;

        try {
    		// ステートメント生成
			ps = con.prepareStatement(RESULT_LIST_BY_USER);

	        ps.setInt(1, userId);

	        // SQLを実行
	        rs = ps.executeQuery();

	        //値を取り出す
	        ResultTblEntity resultEntity = null;
	        while(rs.next()){
	    		resultEntity = new ResultTblEntity();
	    		TaskTblEntity taskEntity = new TaskTblEntity();

	    		//ResultTblEntity
	        	resultEntity.setResultId(rs.getInt("RESULT_ID"));
	        	resultEntity.setTotalScore(rs.getFloat("TOTAL_SCORE"));
	        	resultEntity.setHanded(rs.getInt("HANDED"));
	        	resultEntity.setHandedTimestamp(rs.getTimestamp("HANDED_TIMESTAMP"));
	        	resultEntity.setAnswer(rs.getString("ANSWER"));

	        	//taskEntity
	        	taskEntity.setTaskId(rs.getInt("TASK_ID"));
	        	taskEntity.setName(rs.getString("NAME"));
	        	taskEntity.setTaskQuestion(rs.getString("TASK_QUESTION"));
	        	taskEntity.setCreateUserId(rs.getInt("CREATE_USER_ID"));
	        	taskEntity.setEntryDate(rs.getTimestamp("ENTRY_DATE"));
	        	taskEntity.setUpdateTim(rs.getTimestamp("UPDATE_TIM"));
	        	taskEntity.setTerminationDate(rs.getDate("termination_date"));
	        	taskEntity.setDifficalty(rs.getInt("DIFFICALTY"));

	        	resultEntity.setTaskTbl(taskEntity);

	        	list.add(resultEntity);
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
	 * 結果情報の削除
	 * ※内部的にトランザクションはかけない。
	 * 　上位側で必ずトランザクションをかけること
	 *
	 * @param taskId
	 * @param userId　任意（指定しない場合はNULL）
	 * @throws SQLException
	 */
	public void delete(Integer taskId,Integer userId) throws SQLException{

		if( con == null ){
			return;
		}

		StringBuffer sb = new StringBuffer();
		PreparedStatement psSelect = null;
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		PreparedStatement ps3 = null;
		ResultSet rs = null;
		int resultId;

        try {

    		// ステートメント生成
        	sb.append(RESULT_GET_FOR_DELETE_SQL);
        	if( userId != null ){
        		sb.append(" AND ").append(RESULT_GET_FOR_DELETE_USERID_SQL);
        	}
        	psSelect = con.prepareStatement(sb.toString());

        	psSelect.setInt(1, taskId);
        	if( userId != null ){
        		psSelect.setInt(2,userId);
        	}

	        // SQLを実行
	        rs = psSelect.executeQuery();

        	ps1 = con.prepareStatement(RESULT_DELETE_TASKID_SQL);
        	ps2 = con.prepareStatement(RESULT_TESTCASE_DELETE_TASKID_SQL);
        	ps3 = con.prepareStatement(RESULT_METRICS_DELETE_TASKID_SQL);
	        while(rs.next()){
	        	resultId = rs.getInt("RESULT_ID");

	        	////////////////////////////
	        	//RESULT_TESTCASE_TBL
	        	ps2.setInt(1, resultId);
	        	ps2.executeUpdate();

	        	////////////////////////////
	        	//RESULT_METRICS_TBL
	        	ps3.setInt(1, resultId);
	        	ps3.executeUpdate();

	        	////////////////////////////
	        	//RESULT_TBL
	        	ps1.setInt(1, resultId);
	        	ps1.executeUpdate();
	        }



		} catch (SQLException e) {
			//例外発生時はログを出力し、上位へそのままスロー
			throw e;
		} finally {
			safeClose(psSelect,null);
			safeClose(ps1,null);
			safeClose(ps2,null);
			safeClose(ps3,null);
		}
	}
	/**
	 * 結果情報を取得する
	 *
	 * @param taskId
	 * @param userId
	 * @return
	 * @throws SQLException
	 */
	public List<ResultTblEntity> getResultByTaskId(int taskId) throws SQLException{

		List<ResultTblEntity> list = new ArrayList<>();


		PreparedStatement ps = null;
		ResultSet rs = null;

        try {
    		// ステートメント生成
			ps = con.prepareStatement(RESULT_SEARCH_TASKID_SQL);

	        ps.setInt(1, taskId);

	        // SQLを実行
	        rs = ps.executeQuery();

	        //値を取り出す
	        int resultId = -1;
	        int wkResultId = 0;
	        ResultTblEntity resultEntity = null;
	        while(rs.next()){
	        	wkResultId = rs.getInt("RESULT_ID");

	        	if( resultId != wkResultId ){
		        	if( resultEntity != null ){
		        		list.add(resultEntity);
		        	}
		        	resultEntity = createResultTblEntity(rs);
		        	wkResultId = resultId;
	        	}
	        	//テストケースは複数アル
	        	resultEntity.addResultTestcaseTbl(createResultTestcaseTblEntity(rs));

	        }
	        //最後の１件はループの外で登録する
	        if( resultEntity != null){
	        	list.add(resultEntity);
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
	 * ユーザーと課題を指定してランキングを取得する
	 * @param userId
	 * @param taskId
	 * @return
	 * @throws SQLException
	 */
	public Integer getRankingForUser(Integer userId,Integer taskId) throws SQLException{
		Integer rank = null;

		PreparedStatement ps = null;
		ResultSet rs = null;

        try {
    		// ステートメント生成
        	StringBuffer sql = new StringBuffer(RESULT_RANKING_USERID_SQL_SELECT);

			ps = con.prepareStatement(sql.toString());

			ps.setInt(1, taskId);
			ps.setInt(2, userId);
			ps.setInt(3, taskId);

	        // SQLを実行
	        rs = ps.executeQuery();

	        //値を取り出す
	        while(rs.next()){

	        	rank = rs.getInt("rank");
	        }

		} catch (SQLException e) {
			//例外発生時はログを出力し、上位へそのままスロー
			throw e;

		} finally {
			safeClose(ps,rs);
		}

        return rank;
	}

	/**
	 * ランキングの取得
	 *
	 * @param courseId
	 * @param taskId
	 * @return
	 * @throws SQLException
	 */
	public List<ResultTblEntity> getRanking(Integer courseId,Integer taskId,Integer taskGrpId,Integer grade,float easyOffset,float normalOffset,float difficalOffset) throws SQLException{
		List<ResultTblEntity> list = new ArrayList<ResultTblEntity>();


		PreparedStatement ps = null;
		ResultSet rs = null;

        try {
    		// ステートメント生成
        	StringBuffer sql = new StringBuffer(RESULT_RANKING_SQL_SELECT);

        	sql.append(getRankingWhereString(courseId,taskId,taskGrpId,grade));
        	sql.append(RESULT_RANKING_SQL_GROUPBY);
        	sql.append(RESULT_RANKING_SQL_ORDERBY);

			ps = con.prepareStatement(sql.toString());

			ps = setRankingPram(ps,courseId,taskId,taskGrpId,grade,easyOffset,normalOffset,difficalOffset);

	        // SQLを実行
	        rs = ps.executeQuery();

	        //値を取り出す
	        while(rs.next()){

	        	list.add( getResultTblEntityForRanking(rs) );
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
	 * ランキング用のWHERE句を作成
	 * @param courseId
	 * @param taskId
	 * @return
	 */
	private String getRankingWhereString(Integer courseId,Integer taskId,Integer taskGrpId,Integer grade){
		StringBuffer sb = new StringBuffer();

		if( courseId != null ){
			sb.append(RESULT_RANKING_SQL_WHERE_COURSE);
		}
		if( taskId != null ){
			appendWhereWithAnd(sb,RESULT_RANKING_SQL_WHERE_TASK);
		}
		if( taskGrpId != null ){
			appendWhereWithAnd(sb,RESULT_RANKING_SQL_WHERE_TASKGRP);
		}
		if( grade != null ){
			appendWhereWithAnd(sb,RESULT_RANKING_SQL_WHERE_GRADE);
		}

		if( sb.length() > 0 ){
			//既にWHERE条件はあるのでAND
			sb.insert(0, " AND ");
		}

		return sb.toString();
	}

	/**
	 * ランキング用のパラメータをセットする
	 * @param ps
	 * @param courseId
	 * @param taskId
	 * @return
	 * @throws SQLException
	 */
	private PreparedStatement setRankingPram(PreparedStatement ps,Integer courseId,Integer taskId,Integer taskGrpId,Integer grade,float easyOffset,float normalOffset,float difficalOffset) throws SQLException{
		int index = 1;

		ps.setFloat(index++, easyOffset);
		ps.setFloat(index++, normalOffset);
		ps.setFloat(index++, difficalOffset);

		if( courseId != null ){
			ps.setInt(index, courseId);
			index++;
		}
		if( taskId != null ){
			ps.setInt(index, taskId);
			index++;
		}
		if( taskGrpId != null ){
			ps.setInt(index, taskGrpId);
			index++;
		}
		if( grade != null ){
			ps.setInt(index, grade);
		}

		return ps;
	}

	/**
	 * ランキングデータを取得
	 *
	 *
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	private ResultTblEntity getResultTblEntityForRanking(ResultSet rs) throws SQLException{
		ResultTblEntity entity = new ResultTblEntity();

		//////////////////////////////////
		// RESULT_TBL
		entity.setTotalScore(rs.getFloat("rank_total"));

		//////////////////////////////////
		// USER_TBL
		UserTblEntity userEntity = new UserTblEntity();
		userEntity.setUserId(rs.getInt("USER_ID"));
		userEntity.setName(rs.getString("NAME"));
		userEntity.setNickName(rs.getString("NICK_NAME"));
		userEntity.setMailadress(rs.getString("MAILADRESS"));
		userEntity.setAdmissionYear(rs.getInt("ADMISSION_YEAR"));
		userEntity.setRepeatYearCount(rs.getInt("REPEAT_YEAR_COUNT"));
		userEntity.setRemark(rs.getString("REMARK"));
		userEntity.setGrade(rs.getInt("grade"));


		//////////////////////////////////
		// COURSE_TD
		CourseMasterEntity courseEntity = new CourseMasterEntity();
		courseEntity.setCourseId(rs.getInt("COURSE_ID"));
		courseEntity.setCourseName(rs.getString("COURSE_NAME"));
		userEntity.setCourseMaster(courseEntity);

		//////////////////////////////////
		// TASK_TBL
		TaskTblEntity taskEntity = new TaskTblEntity();
//		taskEntity.setName(rs.getString("taskname"));
//		taskEntity.setDifficalty(rs.getInt("DIFFICALTY"));

		entity.setUserTbl(userEntity);
		entity.setTaskTbl(taskEntity);

		return entity;
	}

	/**
	 * 結果情報を取得する
	 *
	 * @param taskId
	 * @param userId
	 * @return
	 * @throws SQLException
	 */
	public ResultTblEntity getResult(int taskId,int userId) throws SQLException{

		ResultTblEntity resultEntity = null;


		PreparedStatement ps = null;
		ResultSet rs = null;

        try {
    		// ステートメント生成
			ps = con.prepareStatement(RESULT_SEARCH_SQL);

	        ps.setInt(RESULT_SEARCH_USR_ID, userId);
	        ps.setInt(RESULT_SEARCH_TASK_ID, taskId);

	        // SQLを実行
	        rs = ps.executeQuery();

	        //値を取り出す
	        while(rs.next()){

	        	//合計、複雑度は1回だけ
	        	if( resultEntity == null ){
	        		resultEntity = createResultTblEntity(rs);
	        	}
	        	//テストケースは複数アル
	        	resultEntity.addResultTestcaseTbl(createResultTestcaseTblEntity(rs));

	        }

		} catch (SQLException e) {
			//例外発生時はログを出力し、上位へそのままスロー
			throw e;

		} finally {
			safeClose(ps,rs);
		}

		return resultEntity;
	}

	/**
	 * 結果情報の設定
	 *
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	private ResultTblEntity createResultTblEntity(ResultSet rs) throws SQLException{
		ResultTblEntity resultEntity = new ResultTblEntity();
		TaskTblEntity taskEntity = new TaskTblEntity();

    	resultEntity.setResultId(rs.getInt("RESULT_ID"));
    	resultEntity.setTotalScore(rs.getFloat("TOTAL_SCORE"));
    	resultEntity.setHanded(rs.getInt("HANDED"));
    	resultEntity.setHandedTimestamp(rs.getTimestamp("HANDED_TIMESTAMP"));
    	resultEntity.setAnswer(rs.getString("ANSWER"));
    	resultEntity.setComment(rs.getString("COMMENT"));

    	//taskEntity
    	taskEntity.setTaskId(rs.getInt("TASK_ID"));
    	taskEntity.setName(rs.getString("NAME"));
    	taskEntity.setTaskQuestion(rs.getString("TASK_QUESTION"));
    	taskEntity.setCreateUserId(rs.getInt("CREATE_USER_ID"));
    	taskEntity.setEntryDate(rs.getTimestamp("ENTRY_DATE"));
    	taskEntity.setUpdateTim(rs.getTimestamp("UPDATE_TIM"));
    	taskEntity.setTerminationDate(rs.getDate("termination_date"));
    	taskEntity.setDifficalty(rs.getInt("DIFFICALTY"));

    	resultEntity.setTaskTbl(taskEntity);

    	//複雑殿解析結果
    	resultEntity.addResultMetricsTbl(createResultMetricsTblEntity(rs));


		return resultEntity;
	}

	/**
	 * テストケース結果のセット
	 *
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	private ResultTestcaseTblEntity createResultTestcaseTblEntity(ResultSet rs) throws SQLException{

		ResultTestcaseTblEntity rt = new ResultTestcaseTblEntity();

		rt.setScore(rs.getInt("SCORE"));
		rt.setMessage(rs.getString("MESSAGE"));
		rt.setTestcaseId(rs.getInt("TESTCASE_ID"));

		return rt;
	}

	/**
	 * 複雑度結果のセット
	 *
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	private ResultMetricsTblEntity createResultMetricsTblEntity(ResultSet rs) throws SQLException{

		ResultMetricsTblEntity rm = new ResultMetricsTblEntity();

		rm.setAvrLoc(rs.getFloat("AVR_LOC"));
		rm.setAvrLocScore(rs.getFloat("AVR_LOC_SCORE"));
		rm.setAvrMvg(rs.getFloat("AVR_MVG"));
		rm.setAvrMvgScore(rs.getFloat("AVR_MVG_SCORE"));

		rm.setMaxLoc(rs.getInt("MAX_LOC"));
		rm.setMaxLocScore(rs.getFloat("MAX_LOC_SCORE"));
		rm.setMaxMvg(rs.getInt("MAX_MVG"));
		rm.setMaxMvgScore(rs.getFloat("MAX_MVG_SCORE"));

		return rm;
	}


	/**
	 * 結果情報を更新、または追加する
	 *
	 * @param userId
	 * @param resultEntity
	 * @throws SQLException
	 */
	public void insertOrupdateTaskResult(int userId,ResultTblEntity resultEntity,List<SrcTblEntity> srcFileList) throws SQLException{

		if( resultEntity.getResultId() == null ){
			//新規登録
			insertTaskResult(userId,resultEntity,srcFileList);
		}else{
			//更新処理
			updateTaskResult(userId,resultEntity,srcFileList);
		}
	}

	/**
	 * 課題結果の追加
	 * @param userId
	 * @param resultEntity
	 * @throws SQLException
	 */
	public void insertTaskResult(int userId,ResultTblEntity resultEntity,List<SrcTblEntity> srcFileList) throws SQLException{

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
        	//RESULT_TBL
        	ps1 = con.prepareStatement(RESULT_INSERT_SQL);

        	ps1.setInt(1, userId);
        	ps1.setInt(2, resultEntity.getTaskTbl().getTaskId());
        	ps1.setFloat(3, resultEntity.getTotalScore());
        	ps1.setInt(4, resultEntity.getHanded());
			if( resultEntity.getHandedTimestamp() != null){
				//ps1.setDate(5, parseSQLLDateFromUtilData(resultEntity.getHandedTimestamp()) );
				ps1.setTimestamp(5,new Timestamp(resultEntity.getHandedTimestamp().getTime())  );
			}else{
				ps1.setNull(5, java.sql.Types.DATE);
			}
			ps1.setString(6, resultEntity.getAnswer());

        	ps1.executeUpdate();

			int result_id = getLastInsertid("RESULT_TBL");

        	////////////////////////////
        	//RESULT_TESTCASE_TBL
			Set<ResultTestcaseTblEntity> testcaseSet = resultEntity.getResultTestcaseTblSet();

			ps2 = con.prepareStatement(TESTCASE_INSERT_SQL);

			for(ResultTestcaseTblEntity rtt : testcaseSet){

				ps2.setInt(1, result_id);
				ps2.setInt(2, rtt.getTestcaseId());
				ps2.setInt(3, rtt.getScore());
				ps2.setString(4, rtt.getMessage());

				ps2.addBatch();
			}

			ps2.executeBatch();

        	////////////////////////////
        	//RESULT_TESTCASE_TBL
			Set<ResultMetricsTblEntity> metricsList = resultEntity.getResultMetricsTblSet();

			ps3 = con.prepareStatement(METRICS_INSERT_SQL);

			for(ResultMetricsTblEntity rmt : metricsList){

				ps3.setInt(1, result_id);
				ps3.setInt(2, rmt.getMaxMvg());
				ps3.setFloat(3, rmt.getAvrMvg());
				ps3.setInt(4, rmt.getMaxLoc());
				ps3.setFloat(5, rmt.getAvrLoc());
				ps3.setFloat(6, rmt.getMaxMvgScore());
				ps3.setFloat(7, rmt.getMaxLocScore());
				ps3.setFloat(8, rmt.getAvrMvgScore());
				ps3.setFloat(9, rmt.getAvrLocScore());

				ps3.addBatch();
			}
			ps3.executeBatch();

        	////////////////////////////
        	//SRC_TBL
			ps4 = con.prepareStatement(SRCTBL_INSERT_SQL);

			for(SrcTblEntity src : srcFileList){

				ps4.setInt(1, result_id);
				ps4.setString(2, src.getFileName());
				ps4.setString(3, src.getSrc());

				ps4.addBatch();
			}
			ps4.executeBatch();

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
			safeClose(ps4,null);
		}
	}

	/**
	 * 課題結果の更新
	 * @param userId
	 * @param resultEntity
	 * @throws SQLException
	 */
	public void updateTaskResult(int userId,ResultTblEntity resultEntity,List<SrcTblEntity> srcFileList) throws SQLException{

		if( con == null ){
			return;
		}

		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		PreparedStatement ps3 = null;
		PreparedStatement ps4 = null;
		PreparedStatement ps5 = null;

        try {
        	this.beginTranzaction();

        	////////////////////////////
        	//RESULT_TBL
        	ps1 = con.prepareStatement(RESULT_UPDATE_SQL);

        	ps1.setInt(1, userId);
        	ps1.setInt(2, resultEntity.getTaskTbl().getTaskId());
        	ps1.setFloat(3, resultEntity.getTotalScore());
        	ps1.setInt(4, resultEntity.getHanded());
			if( resultEntity.getHandedTimestamp() != null){
				ps1.setTimestamp(5,new Timestamp(resultEntity.getHandedTimestamp().getTime())  );
			}else{
				ps1.setNull(5, java.sql.Types.TIMESTAMP);
			}
        	ps1.setString(6, resultEntity.getAnswer());
        	ps1.setInt(7, resultEntity.getResultId());

        	ps1.executeUpdate();


        	////////////////////////////
        	//RESULT_TESTCASE_TBL
			Set<ResultTestcaseTblEntity> testcaseSet = resultEntity.getResultTestcaseTblSet();

			ps2 = con.prepareStatement(TESTCASE_UPDATE_SQL);

			for(ResultTestcaseTblEntity rtt : testcaseSet){

				ps2.setInt(1, rtt.getScore());
				ps2.setString(2, rtt.getMessage());
				ps2.setInt(3, resultEntity.getResultId());
				ps2.setInt(4, rtt.getTestcaseId());

				ps2.addBatch();
			}

			ps2.executeBatch();

        	////////////////////////////
        	//RESULT_TESTCASE_TBL
			Set<ResultMetricsTblEntity> metricsList = resultEntity.getResultMetricsTblSet();

			ps3 = con.prepareStatement(METRICS_UPDATE_SQL);

			for(ResultMetricsTblEntity rmt : metricsList){

				ps3.setInt(1, rmt.getMaxMvg());
				ps3.setFloat(2, rmt.getAvrMvg());
				ps3.setInt(3, rmt.getMaxLoc());
				ps3.setFloat(4, rmt.getAvrLoc());
				ps3.setFloat(5, rmt.getMaxMvgScore());
				ps3.setFloat(6, rmt.getMaxLocScore());
				ps3.setFloat(7, rmt.getAvrMvgScore());
				ps3.setFloat(8, rmt.getAvrLocScore());
				ps3.setInt(9, resultEntity.getResultId());

				ps3.addBatch();
			}
			ps3.executeBatch();

        	////////////////////////////
        	//SRC_TBL
			//一旦削除し、新規追加
			ps4 = con.prepareStatement(SRCTBL_DELETE_SQL);
			ps4.setInt(1, resultEntity.getResultId());
			ps4.executeUpdate();

			//新規追加
			ps5 = con.prepareStatement(SRCTBL_INSERT_SQL);

			for(SrcTblEntity src : srcFileList){

				ps5.setInt(1, resultEntity.getResultId());
				ps5.setString(2, src.getFileName());
				ps5.setString(3, src.getSrc());

				ps5.addBatch();
			}
			ps5.executeBatch();

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
			safeClose(ps4,null);
			safeClose(ps5,null);
		}
	}

	public ResultTblEntity getResultTblEntity(int userId,int resultid){
		ResultTblEntity resultEinty = new ResultTblEntity();


		return resultEinty;
	}
}
