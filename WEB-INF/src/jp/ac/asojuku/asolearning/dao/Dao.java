/**
 *
 */
package jp.ac.asojuku.asolearning.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jp.ac.asojuku.asolearning.config.AppSettingProperty;
import jp.ac.asojuku.asolearning.exception.AsoLearningSystemErrException;
import jp.ac.asojuku.asolearning.exception.DBConnectException;

/**
 * DBアクセス基本クラス
 * @author nishino
 *
 */
public class Dao {

	Logger logger = LoggerFactory.getLogger(Dao.class);
	protected Connection con = null;


	public Dao(){
	}

	public Dao(Connection con){
		this.con = con;
	}

	public void connect() throws DBConnectException{

		if( con != null ){
			//接続済みの場合は何もしない
			return;
		}

    	InitialContext ctx;
		try {
			ctx = new InitialContext();

        	DataSource ds =
        		(DataSource)ctx.lookup(AppSettingProperty.getInstance().getDBString());

			// MySQLに接続
	        con = ds.getConnection();

		} catch (NamingException e) {
			throw new DBConnectException(e);
		} catch (SQLException e) {
			throw new DBConnectException(e);
		} catch (AsoLearningSystemErrException e) {
			throw new DBConnectException(e);
		}


	}

	public void close(){

		if( con != null ){
			try {
				con.close();
				con = null;
			} catch (SQLException e) {
				logger.info("closeに失敗しました：",e);
			}
		}
	}

	public Connection getConnection(){
		return con;
	}

	public void beginTranzaction() throws SQLException{
		if( con != null ){
			con.setAutoCommit(false);
		}
	}

	public void commit() throws SQLException{
		if( con != null ){
			try {
				con.commit();
			} finally{
				con.setAutoCommit(true);
			}
		}

	}

	public void rollback(){
		if( con != null ){
			try {
				con.rollback();
			}catch(SQLException e){
				logger.info("rollebackに失敗しました：",e);
			} finally{
				try{
					con.setAutoCommit(true);
				}catch(SQLException e){
					logger.info("setAutoCommitに失敗しました：",e);
				}
			}
		}

	}

	/**
	 * util.Date を sql.Dateに変換する
	 * @param jDate
	 * @return
	 */
	protected java.sql.Date parseSQLLDateFromUtilData(java.util.Date jDate){
		return new java.sql.Date(jDate.getTime());
	}

	protected Integer fixInt(int value, boolean isNull) {
	    return isNull ? null : value;
	}

	/**
	 * PreparedStatementとResultSetをクローズする
	 * @param ps
	 * @param rs
	 */
	protected void safeClose(PreparedStatement ps,ResultSet rs){

    	try {
	        // 接続を閉じる
        	if( rs != null ){
				rs.close();
        	}
        	if( ps != null ){
	        	ps.close();
        	}
		} catch (SQLException e) {
			logger.info("closeに失敗しました：",e);
			;	//closeの失敗は無視
		}
	}

	/**
	 * 直前にINSERTしたAUTOINCREMENTのIDを取得する
	 * @param tableName
	 * @return
	 * @throws SQLException
	 */
	protected int getLastInsertid(String tableName) throws SQLException{
		if( con == null ){
			return 0;
		}

		int lastid = 0;

		PreparedStatement ps = null;
		ResultSet rs = null;

		// ステートメント生成
		ps = con.prepareStatement("select last_insert_id() as lastId from "+tableName);

        // SQLを実行
        rs = ps.executeQuery();

        //値を取り出す
        while(rs.next()){
        	lastid = rs.getInt("lastId");
        }

        return lastid;
	}

	/**
	 * WHEREの条件文をつなぐ
	 * @param sb
	 * @param whereCond
	 * @return
	 */
	protected void appendWhereWithAnd(StringBuffer sb,String whereCond){

		if( sb.length() > 0 ){
			sb.append(" AND ");
		}

		sb.append(whereCond);

	}

	protected String getLikeString(String param){

		String[] params = param.split("");
		StringBuffer sb = new StringBuffer();

		for(String str:params){
			switch(str){
			case "\\":
				sb.append("\\");
				break;
			case "%":
				sb.append("\\");
				break;
			case "_":
				sb.append("\\");
				break;
			}
			sb.append(str);
		}

		if( sb.length() > 0){
			sb.insert(0, "%");
			sb.append("%");
		}

		return sb.toString();
	}
}
