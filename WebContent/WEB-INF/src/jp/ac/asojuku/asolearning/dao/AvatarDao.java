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

import jp.ac.asojuku.asolearning.condition.SearchTaskResultCondition;
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
			"SELECT * FROM AVATAR_MASTER "
			+ "WHERE KIND=? AND "
			+ "ANS_COND_EASY <= ? AND "
			+ "ANS_COND_NORMAL <= ? AND "
			+ "ANS_COND_HARD <= ? AND "
			+ "TOTAL_CND_EASY <= ? AND "
			+ "TOTAL_CND_NORMAL <= ? AND "
			+ "TOTAL_CND_HARD <= ?";

	private static final String AVATAR_SELECT =
			"SELECT * FROM AVATAR_MASTER WHERE AVATAR_ID=?";

	private static final String UPDATE_AVATAR =
			"UPDATE USER_TBL SET "
			+ "AVATAR_ID_CSV = ?, "
			+ "UPDATE_DATE=CURRENT_TIMESTAMP "
			+ "WHERE USER_ID = ?";


	/**
	 * IDを指定してアバター情報を取得する
	 * @param avatarId
	 * @return
	 * @throws SQLException
	 */
	public AvatarMasterEntity getBy(Integer avatarId) throws SQLException{

		if( con == null ){
			return null;
		}

		PreparedStatement ps = null;
		ResultSet rs = null;
		AvatarMasterEntity entity = new AvatarMasterEntity();

        try {
    		// ステートメント生成
			ps = con.prepareStatement(AVATAR_SELECT);

			ps.setInt(1, avatarId);

	        // SQLを実行
	        rs = ps.executeQuery();

	        //値を取り出す
	        while(rs.next()){

	        	entity.setAvatarId(rs.getInt("AVATAR_ID"));
	        	entity.setKind(rs.getInt("KIND"));
	        	entity.setAnsCondEasy(rs.getInt("ANS_COND_EASY"));
	        	entity.setAnsCondNormal(rs.getInt("ANS_COND_NORMAL"));
	        	entity.setAnsCondHard(rs.getInt("ANS_COND_HARD"));
	        	entity.setTotalCndEasy(rs.getInt("TOTAL_CND_EASY"));
	        	entity.setTotalCndNormal(rs.getInt("TOTAL_CND_NORMAL"));
	        	entity.setTotalCndHard(rs.getInt("TOTAL_CND_HARD"));
	        	entity.setFileName(rs.getString("FILE_NAME"));

	        }

		} catch (SQLException e) {
			//例外発生時はログを出力し、上位へそのままスロー
			throw e;

		} finally {
			safeClose(ps,rs);
		}

		return entity;
	}

	/**
	 * アバター情報の更新
	 * @param userEntity
	 * @param hashedPass
	 * @throws SQLException
	 */
	public void update(String abatarCsv,Integer userId) throws SQLException{

		if( con == null ){
			return;
		}

		PreparedStatement ps = null;

        try {
        	ps = con.prepareStatement(UPDATE_AVATAR);

        	//パラメータセット
        	ps.setString(1, abatarCsv);	//AVATAR_ID_CSV
        	ps.setInt(2, userId);	//USER_ID

			ps.executeUpdate();

		} catch (SQLException e) {
			//例外発生時はログを出力し、上位へそのままスロー
			logger.warn("SQLException:",e);
			throw e;
		} finally {
			safeClose(ps,null);
		}
	}

	/**
	 * アバターパーツ一覧を取得する
	 * @param kind
	 * @param avCond
	 * @return
	 * @throws SQLException
	 */
	public List<AvatarMasterEntity> getAvatarList(int kind,SearchTaskResultCondition avCond) throws SQLException{

		if( con == null ){
			return null;
		}

		PreparedStatement ps = null;
		ResultSet rs = null;
		List<AvatarMasterEntity> avatarList = new ArrayList<AvatarMasterEntity>();

        try {
    		// ステートメント生成
			ps = con.prepareStatement(AVATAR_LIST);

			ps.setInt(1, kind);
			ps.setInt(2, avCond.getAnsConditionEasy());
			ps.setInt(3, avCond.getAnsConditionNormal());
			ps.setInt(4, avCond.getAnsConditionHard());
			ps.setInt(5, (int)avCond.getTotalConditionEasy());
			ps.setInt(6, (int)avCond.getTotalConditionNormal());
			ps.setInt(7, (int)avCond.getTotalConditionHard());

	        // SQLを実行
	        rs = ps.executeQuery();

	        //値を取り出す
	        while(rs.next()){
	        	AvatarMasterEntity entity = new AvatarMasterEntity();

	        	entity.setAvatarId(rs.getInt("AVATAR_ID"));
	        	entity.setKind(rs.getInt("KIND"));
	        	entity.setAnsCondEasy(rs.getInt("ANS_COND_EASY"));
	        	entity.setAnsCondNormal(rs.getInt("ANS_COND_NORMAL"));
	        	entity.setAnsCondHard(rs.getInt("ANS_COND_HARD"));
	        	entity.setTotalCndEasy(rs.getInt("TOTAL_CND_EASY"));
	        	entity.setTotalCndNormal(rs.getInt("TOTAL_CND_NORMAL"));
	        	entity.setTotalCndHard(rs.getInt("TOTAL_CND_HARD"));
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
