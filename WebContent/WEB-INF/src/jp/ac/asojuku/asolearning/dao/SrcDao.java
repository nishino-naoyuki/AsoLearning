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

import jp.ac.asojuku.asolearning.entity.SrcTblEntity;

/**
 * @author nishino
 *
 */
public class SrcDao extends Dao {

	private static final String SRC_LIST_SQL =
			"SELECT * FROM SRC_TBL src WHERE src.RESULT_ID = ?";
	private static final String SRC_DATA_SQL =
			"SELECT * FROM SRC_TBL src "
			+ "LEFT JOIN RESULT_TBL r ON( src.RESULT_ID = r.RESULT_ID) "
			+ "WHERE src.RESULT_ID = ? AND FILE_NAME = ?";
	private static final String SRC_DATA_WHERE1 = " AND r.USER_ID=?";

	public SrcDao() {
	}
	public SrcDao(Connection con) {
		super(con);
	}

	/**
	 * リストの取得
	 * @param resultId
	 * @return
	 * @throws SQLException
	 */
	public List<SrcTblEntity> getList(Integer resultId) throws SQLException{
		List<SrcTblEntity> list = new ArrayList<SrcTblEntity>();

		if( con == null ){
			return null;
		}

		PreparedStatement ps = null;
		ResultSet rs = null;

        try {
    		// ステートメント生成
			ps = con.prepareStatement(SRC_LIST_SQL);

			ps.setInt(1, resultId);

	        // SQLを実行
	        rs = ps.executeQuery();

	        //値を取り出す
	        while(rs.next()){
	        	SrcTblEntity srcEntity = new SrcTblEntity();

	        	srcEntity.setResultId(resultId);
	        	srcEntity.setFileName(rs.getString("FILE_NAME"));
	        	srcEntity.setSrcId(rs.getInt("SRC_ID"));
	        	srcEntity.setSrc(rs.getString("SRC"));

	        	list.add(srcEntity);
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
	 * ソースコードデータの取得
	 *
	 * @param resultId
	 * @param fileName
	 * @param userId
	 * @return
	 * @throws SQLException
	 */
	public SrcTblEntity get(Integer resultId,String fileName,Integer userId) throws SQLException{
    	SrcTblEntity srcEntity = new SrcTblEntity();

		if( con == null ){
			return null;
		}

		PreparedStatement ps = null;
		ResultSet rs = null;

        try {
        	StringBuilder sb = new StringBuilder(SRC_DATA_SQL);
    		// ステートメント生成
			if( userId != null ){
				sb.append(SRC_DATA_WHERE1);
			}

			ps = con.prepareStatement(sb.toString());

			ps.setInt(1, resultId);
			ps.setString(2, fileName);
			if( userId != null ){
				ps.setInt(3, userId);
			}

	        // SQLを実行
	        rs = ps.executeQuery();

	        //値を取り出す
	        while(rs.next()){

	        	srcEntity.setResultId(resultId);
	        	srcEntity.setFileName(rs.getString("FILE_NAME"));
	        	srcEntity.setSrcId(rs.getInt("SRC_ID"));
	        	srcEntity.setSrc(rs.getString("SRC"));

	        }


		} catch (SQLException e) {
			//例外発生時はログを出力し、上位へそのままスロー
			throw e;

		} finally {
			safeClose(ps,rs);
		}


		return srcEntity;
	}
}
