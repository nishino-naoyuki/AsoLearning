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

import jp.ac.asojuku.asolearning.entity.AvatarMasterEntity;

/**
 * アバターDAO
 * @author nishino
 *
 */
public class AvatarDao extends Dao {

	public AvatarDao() {
	}
	public AvatarDao(Connection con) {
		super(con);
	}

	// 種類を指定してアバターの一覧を取得する
	private static final String AVATAR_LIST =
			"SELECT * FROM AVATAR_MASTER WHERE KIND=? AND ANS_COND <= ? AND TOTAL_CND <= ?";


	/**
	 * アバターパーツ一覧を取得する
	 * @param kind
	 * @param ansNum
	 * @param total
	 * @return
	 * @throws SQLException
	 */
	public List<AvatarMasterEntity> getAvatarList(int kind,int ansNum,int total) throws SQLException{

		if( con == null ){
			return null;
		}

		PreparedStatement ps = null;
		ResultSet rs = null;
		List<AvatarMasterEntity> avatarList = new ArrayList<AvatarMasterEntity>();

        try {
    		// ステートメント生成
			ps = con.prepareStatement(AVATAR_LIST);

	        // SQLを実行
	        rs = ps.executeQuery();

	        //値を取り出す
	        while(rs.next()){
	        	AvatarMasterEntity entity = new AvatarMasterEntity();

	        	entity.setAvatarId(rs.getInt("AVATAR_ID"));
	        	entity.setKind(rs.getInt("KIND"));
	        	entity.setAnsCond(rs.getInt("ANS_COND"));
	        	entity.setTotalCnd(rs.getInt("TOTAL_CND"));
	        	entity.setFileName(rs.getString("FILE_NAME"));

	        	avatarList.add(entity);
	        }

		} catch (SQLException e) {
			//例外発生時はログを出力し、上位へそのままスロー
			throw e;

		} finally {
			safeClose(ps,rs);
		}

		return avatarList;
	}
}
