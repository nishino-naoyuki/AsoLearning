/**
 *
 */
package jp.ac.asojuku.asolearning.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import jp.ac.asojuku.asolearning.entity.TaskGroupTblEntity;

/**
 * 課題グループDAO
 * @author nishino
 *
 */
public class TaskGroupDao extends Dao {

	//検索条件を指定して課題一覧を取得
	private static final String TASK_GRP_LIST_COND_SQL =
			"SELECT * FROM TASK_GROUP_TBL tg ";
	private static final String TASK_LIST_WHERE_TASKNAME = "tg.TASK_GROUP_NAME LIKE ?";

	//学科を指定して課題一覧を取得する（指定した学科に対して公開しているものだけ）
	private static final String TASK_GRP_LIST_BYCID_SQL =
			"SELECT * FROM TASK_GROUP_TBL tg "
			+ "LEFT JOIN TASK_TBL t ON(t.TASK_GROUP_ID = tg.TASK_GROUP_ID) "
			+ "LEFT JOIN TASK_PUBLIC_TBL tp ON(tp.TASK_ID = t.TASK_ID) "
			+ "WHERE tp.STATUS_ID IN(1,2) ";
	private static final String TASK_GRP_LIST_BYCID_WHERE = " tp.COURSE_ID = ?";
	private static final String TASK_GRP_LIST_BYCID_GROUPBY = " GROUP BY t.TASK_GROUP_ID ";
	private static final String TASK_GRP_LIST_BYCID_ORDERBY = " ORDER BY tg.TASK_GROUP_NAME ";


	/**
	 * 課題グループを取得
	 * @param groupName
	 * @return
	 * @throws SQLException
	 */
	public List<TaskGroupTblEntity> getTaskGroupListBy(String groupName) throws SQLException{

		if( con == null ){
			return null;
		}
		List<TaskGroupTblEntity> list = new ArrayList<TaskGroupTblEntity>();

		PreparedStatement ps = null;
		ResultSet rs = null;

        try {
    		// ステートメント生成
        	StringBuffer sb = new StringBuffer(TASK_GRP_LIST_COND_SQL);
        	//WHERE を追加
        	if( StringUtils.isNotEmpty(groupName)){
        		sb.append(" WHERE ");
        		sb.append(TASK_LIST_WHERE_TASKNAME);
        	}

			ps = con.prepareStatement(sb.toString());

			//値をセット
        	if( StringUtils.isNotEmpty(groupName)){
        		ps.setString(1,getLikeString(groupName));
        	}
	        // SQLを実行
	        rs = ps.executeQuery();

	        //値を取り出す
	        while(rs.next()){
	        	list.add(createTaskGroupTbl(rs));
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
	 * 課題グループを取得
	 * @param groupName
	 * @return
	 * @throws SQLException
	 */
	public List<TaskGroupTblEntity> getTaskGroupListByCourseId(Integer courseId) throws SQLException{

		if( con == null ){
			return null;
		}
		List<TaskGroupTblEntity> list = new ArrayList<TaskGroupTblEntity>();

		PreparedStatement ps = null;
		ResultSet rs = null;

        try {
    		// ステートメント生成
        	StringBuffer sb = new StringBuffer(TASK_GRP_LIST_BYCID_SQL);
        	//WHERE を追加
        	if( courseId != null){
        		sb.append(" AND ");
        		sb.append(TASK_GRP_LIST_BYCID_WHERE);
        	}
        	sb.append(TASK_GRP_LIST_BYCID_GROUPBY);
        	sb.append(TASK_GRP_LIST_BYCID_ORDERBY);

			ps = con.prepareStatement(sb.toString());

			//値をセット
        	if( courseId != null){
        		ps.setInt(1, courseId);
        	}
	        // SQLを実行
	        rs = ps.executeQuery();

	        //値を取り出す
	        while(rs.next()){
	        	list.add(createTaskGroupTbl(rs));
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
	 * TaskGroupTblEntityを作成する
	 *
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	private TaskGroupTblEntity createTaskGroupTbl(ResultSet rs) throws SQLException{
		TaskGroupTblEntity entity = new TaskGroupTblEntity();

		entity.setTaskGroupId(rs.getInt("TASK_GROUP_ID"));
		entity.setTaskGroupName(rs.getString("TASK_GROUP_NAME"));
		entity.setEntryDate(rs.getDate("ENTRY_DATE"));
		entity.setUpdateDate(rs.getDate("UPDATE_DATE"));

		return entity;
	}
}
