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
