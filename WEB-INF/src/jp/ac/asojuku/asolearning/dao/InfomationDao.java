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

import org.apache.commons.lang3.StringUtils;

import jp.ac.asojuku.asolearning.condition.SearchInfomationCondition;
import jp.ac.asojuku.asolearning.entity.CourseMasterEntity;
import jp.ac.asojuku.asolearning.entity.InfoPublicTblEntity;
import jp.ac.asojuku.asolearning.entity.InfomationTblEntity;
import jp.ac.asojuku.asolearning.entity.TaskTblEntity;
import jp.ac.asojuku.asolearning.entity.UserTblEntity;

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
			  + "    t.DIFFICALTY,"
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

	private static final String INFO_SEARCH =
			"SELECT * FROM INFOMATION_TBL i "
			+ "LEFT JOIN INFO_PUBLIC_TBL ip ON(i.INFOMATION_ID = ip.INFOMATION_ID) "
			+ "LEFT JOIN USER_TBL u ON(i.AUTHOR_USER_ID = u.USER_ID) "
			+ "LEFT JOIN COURSE_MASTER c ON(c.COURSE_ID=ip.COURSE_ID) ";
	private static final String INFO_SEARCH_WHERE_MAIL =
			" u.MAILADRESS LIKE ? ";
	private static final String INFO_SEARCH_WHERE_COURSE =
			" ip.COURSE_ID = ? AND ip.STATUS_ID = 1 ";
	private static final String INFO_SEARCH_WHERE_MSG =
			" i.MESSAGE LIKE ? ";
	private static final String INFO_SEARCH_WHERE_TERM_FROM =
			" ip.PUBLIC_DATETIME <= ? ";
	private static final String INFO_SEARCH_WHERE_TERM_TO =
			" ip.END_DATETIME >= ? ";
	private static final String INFO_SEARCH_ORDER_BY =
			" ORDER BY i.UPDATE_DATE DESC ";


	public List<InfomationTblEntity> searchInfo(SearchInfomationCondition cond) throws SQLException{

		if( con == null ){
			return null;
		}
		List<InfomationTblEntity> list = new ArrayList<InfomationTblEntity>();

		PreparedStatement ps = null;
		ResultSet rs = null;

        try {
    		// ステートメント生成
        	StringBuffer sb = new StringBuffer(INFO_SEARCH);
        	createWhereString(sb,cond);
        	sb.append(INFO_SEARCH_ORDER_BY);

			ps = con.prepareStatement(sb.toString());

			//値をセット
			createWhereParam(ps,cond);

	        // SQLを実行
	        rs = ps.executeQuery();

	        //値を取り出す
	        int infoId = -1;
	        int wkInfoId = 0;
	        InfomationTblEntity entity = null;

	        while(rs.next()){
	        	wkInfoId = rs.getInt("INFOMATION_ID");

	        	//同じINFOIDの間はテストケースのみため込む
	        	if( infoId != wkInfoId ){
	        		//テストケースが変わったら、テスト情報をセットする
	        		if( entity != null ){
	        			list.add(entity);
	        		}
	        		entity = createInfomationTblEntity(rs);
	        		infoId = wkInfoId;
	        	}

	        	addInfoPublicTblEntity(rs,entity);
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

	private void addInfoPublicTblEntity(ResultSet rs,InfomationTblEntity entity) throws SQLException{
		InfoPublicTblEntity infoPublicEntity = new InfoPublicTblEntity();

		infoPublicEntity.setStatusId(rs.getInt("STATUS_ID"));
		infoPublicEntity.setPublicDatetime(rs.getTimestamp("PUBLIC_DATETIME"));
		infoPublicEntity.setEndDatetime(rs.getTimestamp("END_DATETIME"));

		CourseMasterEntity cm = new CourseMasterEntity();
		cm.setCourseId(rs.getInt("COURSE_ID"));
		cm.setCourseName(rs.getString("COURSE_NAME"));
		infoPublicEntity.setCourseMaster(cm);

		entity.addInfoPublicTbl(infoPublicEntity);
	}

	private InfomationTblEntity createInfomationTblEntity(ResultSet rs) throws SQLException{
		InfomationTblEntity entity = new InfomationTblEntity();

		entity.setAuthorUserId(rs.getInt("AUTHOR_USER_ID"));
		entity.setInfomationId(rs.getInt("INFOMATION_ID"));
		entity.setMessage(rs.getString("MESSAGE"));
		entity.setTitle(rs.getString("TITLE"));
		entity.setEntryDate(rs.getTimestamp("ENTRY_DATE"));
		entity.setUpdateDate(rs.getTimestamp("UPDATE_DATE"));

		UserTblEntity user = new UserTblEntity();

		user.setUserId(rs.getInt("USER_ID"));
		user.setMailadress(rs.getString("MAILADRESS"));
		user.setPassword(rs.getString("PASSWORD"));
		user.setName(rs.getString("NAME"));
		user.setNickName(rs.getString("NICK_NAME"));
		user.setAccountExpryDate(rs.getDate("ACCOUNT_EXPRY_DATE"));
		user.setPasswordExpirydate(rs.getDate("PASSWORD_EXPIRYDATE"));
		user.setIsFirstFlg(rs.getInt("IS_FIRST_FLG"));
		user.setCertifyErrCnt(rs.getInt("CERTIFY_ERR_CNT"));
		user.setIsLockFlg(rs.getInt("IS_LOCK_FLG"));
		user.setEntryDate(rs.getTimestamp("ENTRY_DATE"));
		user.setRemark(rs.getString("REMARK"));
		user.setUpdateDate(rs.getTimestamp("UPDATE_DATE"));
		Integer admissionYear = fixInt(rs.getInt("ADMISSION_YEAR"),rs.wasNull());
		user.setAdmissionYear(admissionYear);
		user.setRepeatYearCount(rs.getInt("REPEAT_YEAR_COUNT"));
		Integer graduateYear = fixInt(rs.getInt("GRADUATE_YEAR"),rs.wasNull());
		user.setGraduateYear(graduateYear);
		Integer giveupYear = fixInt(rs.getInt("GIVE_UP_YEAR"),rs.wasNull());
		user.setGiveUpYear(giveupYear);

		entity.setUserEntity(user);

		return entity;
	}
	/**
	 * WHERE句を作る
	 * @param sb
	 * @param cond
	 */
	private void createWhereString(StringBuffer sb,SearchInfomationCondition cond){

		//・作成者のメールアドレス（部分一致）
		if( StringUtils.isNotEmpty(cond.getMailAddress() ) ){
			appendWhereWithAnd(sb,INFO_SEARCH_WHERE_MAIL);
		}
		//・表示対象学科
		if( cond.getCourseId() != null ){
			appendWhereWithAnd(sb,INFO_SEARCH_WHERE_COURSE);
		}
		//・内容（部分一致）
		if( StringUtils.isNotEmpty(cond.getMessage() ) ){
			appendWhereWithAnd(sb,INFO_SEARCH_WHERE_MSG);
		}
		//・表示期間
		if( cond.getDispFrom() != null ){
			appendWhereWithAnd(sb,INFO_SEARCH_WHERE_TERM_FROM);
		}
		if( cond.getDsipTo() != null ){
			appendWhereWithAnd(sb,INFO_SEARCH_WHERE_TERM_TO);
		}

		if( sb.length() > 0 ){
			sb.insert(0, " WHERE ");
		}
	}

	/**
	 * WHERE句を作る
	 * @param sb
	 * @param cond
	 * @throws SQLException
	 */
	private void createWhereParam(PreparedStatement ps,SearchInfomationCondition cond) throws SQLException{

		int index = 1;

		//・作成者のメールアドレス（部分一致）
		if( StringUtils.isNotEmpty(cond.getMailAddress() ) ){
			ps.setString(index++, getLikeString(cond.getMailAddress()));
		}
		//・表示対象学科
		if( cond.getCourseId() != null ){
			ps.setInt(index++, cond.getCourseId());
		}
		//・内容（部分一致）
		if( StringUtils.isNotEmpty(cond.getMessage() ) ){
			ps.setString(index++, getLikeString(cond.getMessage()));
		}
		//・表示期間
		if( cond.getDispFrom() != null ){
			ps.setTimestamp(index++, parseTimeStampFromUtilData(cond.getDispFrom()));
		}
		if( cond.getDsipTo() != null ){
			ps.setTimestamp(index++, parseTimeStampFromUtilData(cond.getDsipTo()));
		}

	}
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
		entity.setDifficalty(rs.getInt("DIFFICALTY"));

		return entity;
	}

}
