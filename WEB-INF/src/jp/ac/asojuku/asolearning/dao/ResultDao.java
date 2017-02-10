/**
 *
 */
package jp.ac.asojuku.asolearning.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Set;

import jp.ac.asojuku.asolearning.entity.ResultMetricsTblEntity;
import jp.ac.asojuku.asolearning.entity.ResultTblEntity;
import jp.ac.asojuku.asolearning.entity.ResultTestcaseTblEntity;

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

	//検索SQL
	private static final String RESULT_SEARCH_SQL =
			"SELECT * FROM RESULT_TBL r "
			+ "LEFT JOIN RESULT_TESTCASE_TBL rt ON(rt.RESULT_ID = r.RESULT_ID) "
			+ "LEFT JOIN RESULT_METRICS_TBL rm ON(rm.RESULT_ID = r.RESULT_ID) "
			+ "WHERE r.USER_ID=? AND r.RESULT_ID=?";
	private static final int RESULT_SEARCH_IDX = 1;

	//挿入SQL
	private static final String RESULT_INSERT_SQL =
			"INSERT INTO RESULT_TBL "
			+ "(RESULT_ID,USER_ID,TASK_ID,TOTAL_SCORE) "
			+ "VALUES(null,?,?,?) ";

	private static final String TESTCASE_INSERT_SQL =
			"INSERT INTO RESULT_TESTCASE_TBL "
			+ "(RESULT_ID,TESTCASE_ID,SCORE,MESSAGE) "
			+ "VALUES(?,?,?,?) ";

	private static final String METRICS_INSERT_SQL =
			"INSERT INTO RESULT_METRICS_TBL "
			+ "(RESULT_ID,MAX_MVG,AVR_MVG,MAX_LOC,AVR_LOC,MAX_MVG_SCORE,MAX_LOC_SCORE,AVR_MVG_SCORE,AVR_LOC_SCORE) "
			+ "VALUES(?,?,?,?,?,?,?,?,?) ";

	//更新SQL
	private static final String RESULT_UPDATE_SQL =
			"UPDATE RESULT_TBL SET "
			+ "USER_ID = ?,"
			+ "TASK_ID = ?,"
			+ "TOTAL_SCORE = ? "
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

	/**
	 * 結果情報を更新、または追加する
	 *
	 * @param userId
	 * @param resultEntity
	 * @throws SQLException
	 */
	public void insertOrupdateTaskResult(int userId,ResultTblEntity resultEntity) throws SQLException{

		if( resultEntity.getResultId() == null ){
			//新規登録
			insertTaskResult(userId,resultEntity);
		}else{
			//更新処理
			updateTaskResult(userId,resultEntity);
		}
	}

	/**
	 * 課題結果の追加
	 * @param userId
	 * @param resultEntity
	 * @throws SQLException
	 */
	public void insertTaskResult(int userId,ResultTblEntity resultEntity) throws SQLException{

		if( con == null ){
			return;
		}

		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		PreparedStatement ps3 = null;

        try {
        	this.beginTranzaction();

        	////////////////////////////
        	//RESULT_TBL
        	ps1 = con.prepareStatement(RESULT_INSERT_SQL);

        	ps1.setInt(1, userId);
        	ps1.setInt(2, resultEntity.getTaskTbl().getTaskId());
        	ps1.setFloat(3, resultEntity.getTotalScore());

        	ps1.executeUpdate();

			int result_id = getLastInsertid("RESULT_TBL");

        	////////////////////////////
        	//RESULT_TESTCASE_TBL
			Set<ResultTestcaseTblEntity> testcaseSet = resultEntity.getResultTestcaseTblSet();

			for(ResultTestcaseTblEntity rtt : testcaseSet){

				ps2 = con.prepareStatement(TESTCASE_INSERT_SQL);

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

			for(ResultMetricsTblEntity rmt : metricsList){

				ps3 = con.prepareStatement(METRICS_INSERT_SQL);

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
	 * 課題結果の更新
	 * @param userId
	 * @param resultEntity
	 * @throws SQLException
	 */
	public void updateTaskResult(int userId,ResultTblEntity resultEntity) throws SQLException{

		if( con == null ){
			return;
		}

		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		PreparedStatement ps3 = null;

        try {
        	this.beginTranzaction();

        	////////////////////////////
        	//RESULT_TBL
        	ps1 = con.prepareStatement(RESULT_UPDATE_SQL);

        	ps1.setInt(1, userId);
        	ps1.setInt(2, resultEntity.getTaskTbl().getTaskId());
        	ps1.setFloat(3, resultEntity.getTotalScore());
        	ps1.setInt(4, resultEntity.getResultId());

        	ps1.executeUpdate();

			int result_id = getLastInsertid("RESULT_TBL");

        	////////////////////////////
        	//RESULT_TESTCASE_TBL
			Set<ResultTestcaseTblEntity> testcaseSet = resultEntity.getResultTestcaseTblSet();

			for(ResultTestcaseTblEntity rtt : testcaseSet){

				ps2 = con.prepareStatement(TESTCASE_UPDATE_SQL);

				ps2.setInt(1, rtt.getScore());
				ps2.setString(2, rtt.getMessage());
				ps2.setInt(3, result_id);
				ps2.setInt(4, rtt.getTestcaseId());

				ps2.addBatch();
			}

			ps2.executeBatch();

        	////////////////////////////
        	//RESULT_TESTCASE_TBL
			Set<ResultMetricsTblEntity> metricsList = resultEntity.getResultMetricsTblSet();

			for(ResultMetricsTblEntity rmt : metricsList){

				ps3 = con.prepareStatement(METRICS_UPDATE_SQL);

				ps3.setInt(1, rmt.getMaxMvg());
				ps3.setFloat(2, rmt.getAvrMvg());
				ps3.setInt(3, rmt.getMaxLoc());
				ps3.setFloat(4, rmt.getAvrLoc());
				ps3.setFloat(5, rmt.getMaxMvgScore());
				ps3.setFloat(6, rmt.getMaxLocScore());
				ps3.setFloat(7, rmt.getAvrMvgScore());
				ps3.setFloat(8, rmt.getAvrLocScore());
				ps3.setInt(9, result_id);

				ps3.addBatch();
			}
			ps3.executeBatch();

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

	public ResultTblEntity getResultTblEntity(int userId,int resultid){
		ResultTblEntity resultEinty = new ResultTblEntity();


		return resultEinty;
	}
}
