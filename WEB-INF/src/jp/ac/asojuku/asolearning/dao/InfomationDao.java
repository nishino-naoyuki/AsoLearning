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

import jp.ac.asojuku.asolearning.entity.TaskTblEntity;

/**
 * お知らせ情報のDAO
 * @author nishino
 *
 */
public class InfomationDao extends Dao {

	public InfomationDao() {
	}
	public InfomationDao(Connection con) {
		super(con);
	}

	//7日以内に作成された課題を表示
	private static final String TASK_CREATE_RECNET =
			  "select * from "
			+ "(select *,datediff(CURRENT_DATE(),t.ENTRY_DATE) diff  from TASK_TBL t) dd "
			+ "LEFT JOIN TASK_PUBLIC_TBL tp ON(dd.TASK_ID = tp.TASK_ID) "
			+ "LEFT JOIN PUBLIC_STATUS_MASTER ps ON(tp.STATUS_ID = ps.STATUS_ID) "
			+ "WHERE (dd.diff BETWEEN -7 AND 7) AND tp.STATUS_ID IN(1,2) ";

	//7日以内に更新された課題を表示
	private static final String TASK_UPDATE_RECNET =
			  "select * from "
			+ "(select *,datediff(CURRENT_DATE(),t.UPDATE_TIM) diff  from TASK_TBL t) dd "
			+ "LEFT JOIN TASK_PUBLIC_TBL tp ON(dd.TASK_ID = tp.TASK_ID) "
			+ "LEFT JOIN PUBLIC_STATUS_MASTER ps ON(tp.STATUS_ID = ps.STATUS_ID) "
			+ "WHERE dd.UPDATE_TIM <> dd.ENTRY_DATE AND (dd.diff BETWEEN -7 AND 7) AND tp.STATUS_ID IN(1,2) ";

	//3日以内の締め切りでまだ提出していない課題
	private static final String TASK_ENDDATE_RECNET =
			    "SELECT * FROM "
			  + "("
			  + "  SELECT "
			  + "    t.TASK_ID,"
			  + "    t.NAME,"
			  + "    t.TASK_QUESTION,"
			  + "    t.CREATE_USER_ID,"
			  + "    t.ENTRY_DATE,"
			  + "    t.UPDATE_TIM,"
			  + "    tp.COURSE_ID, "
			  + "    tp.STATUS_ID,"
			  + "    DATEDIFF(tp.END_DATETIME,CURRENT_DATE()) diff "
			  + "  FROM TASK_TBL t "
			  + "        LEFT JOIN TASK_PUBLIC_TBL tp ON(t.TASK_ID = tp.TASK_ID)"
			  + "  WHERE tp.END_DATETIME is not null"
			  + ") dd "
			+ "WHERE (dd.diff BETWEEN 0 AND 4) AND dd.STATUS_ID IN(1,2) AND "
			+ "COURSE_ID = ? AND "
			+ "not exists(SELECT USER_ID FROM RESULT_TBL r WHERE r.USER_ID=? AND r.HANDED=1 AND r.TASK_ID=dd.TASK_ID)";

	private static final String WHERE_COURSEID = "tp.COURSE_ID = ?";

	/**
	 * 7日以内更新された課題を取得する
	 *
	 * @param courseId
	 * @return
	 * @throws SQLException
	 */
	public List<TaskTblEntity> getNearEndDateList(Integer userId,Integer courseId) throws SQLException{

		if( con == null ){
			return null;
		}
		List<TaskTblEntity> list = new ArrayList<TaskTblEntity>();

		PreparedStatement ps = null;
		ResultSet rs = null;

        try {
    		// ステートメント生成
        	StringBuffer sb = new StringBuffer(TASK_ENDDATE_RECNET);
			ps = con.prepareStatement(sb.toString());

			//値をセット
        	ps.setInt(1, courseId);
        	ps.setInt(2, userId);

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
	        		entity = createTaskTbl(rs);
	        		taskId = wkTaskId;
	        	}
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
	 * 7日以内更新された課題を取得する
	 *
	 * @param courseId
	 * @return
	 * @throws SQLException
	 */
	public List<TaskTblEntity> getUpdateRecentList(Integer courseId) throws SQLException{

		if( con == null ){
			return null;
		}
		List<TaskTblEntity> list = new ArrayList<TaskTblEntity>();

		PreparedStatement ps = null;
		ResultSet rs = null;

        try {
    		// ステートメント生成
        	StringBuffer sb = new StringBuffer(TASK_UPDATE_RECNET);
        	//WHERE を追加
        	if( courseId != null ){
        		sb.append(" AND ");
        		sb.append(WHERE_COURSEID);
        	}

			ps = con.prepareStatement(sb.toString());

			//値をセット
        	if( courseId != null ){
        		ps.setInt(1, courseId);
        	}

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
	        		entity = createTaskTbl(rs);
	        		taskId = wkTaskId;
	        	}
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
	 * 7日以内に作成された課題を取得する
	 *
	 * @param courseId
	 * @return
	 * @throws SQLException
	 */
	public List<TaskTblEntity> getCreateRecentList(Integer courseId) throws SQLException{

		if( con == null ){
			return null;
		}
		List<TaskTblEntity> list = new ArrayList<TaskTblEntity>();

		PreparedStatement ps = null;
		ResultSet rs = null;

        try {
    		// ステートメント生成
        	StringBuffer sb = new StringBuffer(TASK_CREATE_RECNET);
        	//WHERE を追加
        	if( courseId != null ){
        		sb.append(" AND ");
        		sb.append(WHERE_COURSEID);
        	}

			ps = con.prepareStatement(sb.toString());

			//値をセット
        	if( courseId != null ){
        		ps.setInt(1, courseId);
        	}

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
	        		entity = createTaskTbl(rs);
	        		taskId = wkTaskId;
	        	}
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
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	private TaskTblEntity createTaskTbl(ResultSet rs) throws SQLException{
		TaskTblEntity entity = new TaskTblEntity();

		entity.setTaskId(rs.getInt("TASK_ID"));
		entity.setName(rs.getString("NAME"));
		entity.setTaskQuestion(rs.getString("TASK_QUESTION"));
		entity.setCreateUserId(rs.getInt("CREATE_USER_ID"));
		entity.setEntryDate(rs.getTimestamp("ENTRY_DATE"));
		entity.setUpdateTim(rs.getTimestamp("UPDATE_TIM"));

		return entity;
	}

}
