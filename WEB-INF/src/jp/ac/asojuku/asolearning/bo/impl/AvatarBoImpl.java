/**
 *
 */
package jp.ac.asojuku.asolearning.bo.impl;

import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jp.ac.asojuku.asolearning.bo.AvatarBo;
import jp.ac.asojuku.asolearning.dao.AvatarDao;
import jp.ac.asojuku.asolearning.dao.ResultDao;
import jp.ac.asojuku.asolearning.dto.AvatarDto;
import jp.ac.asojuku.asolearning.dto.AvatarPartsDto;
import jp.ac.asojuku.asolearning.dto.LogonInfoDTO;
import jp.ac.asojuku.asolearning.entity.AvatarMasterEntity;
import jp.ac.asojuku.asolearning.entity.ResultTblEntity;
import jp.ac.asojuku.asolearning.exception.AsoLearningSystemErrException;
import jp.ac.asojuku.asolearning.exception.DBConnectException;
import jp.ac.asojuku.asolearning.param.AvatarKind;

/**
 * アバター実装処理
 * @author nishino
 *
 */
public class AvatarBoImpl implements AvatarBo {
	Logger logger = LoggerFactory.getLogger(AvatarBoImpl.class);

	/* (非 Javadoc)
	 * @see jp.ac.asojuku.asolearning.bo.AvatarBo#getParts(jp.ac.asojuku.asolearning.dto.LogonInfoDTO)
	 */
	@Override
	public AvatarPartsDto getParts(LogonInfoDTO userInfo) throws AsoLearningSystemErrException {
		AvatarPartsDto avatorParts = new AvatarPartsDto();

		AvatarDao dao = new AvatarDao();

		try {

			//DB接続
			dao.connect();

			ResultDao retDao = new ResultDao(dao.getConnection());

			List<ResultTblEntity> retList = retDao.getListByUser(userInfo.getUserId());

			//解いた問題数と、合計得点を算出
			int ansNum = 0;
			int total = 0;

			for( ResultTblEntity ret : retList ){
				ansNum++;
				total += ret.getTotalScore();
			}

			//アバター種類のリスト
			AvatarKind[] kindList = AvatarKind.getList();

			for(AvatarKind kind : kindList){
				AvatarDto dto = getAvatarDto(dao, ansNum, total,kind);
				avatorParts.setAvatarDto(kind, dto);
			}

		} catch (DBConnectException e) {
			//ログ出力
			logger.warn("DB接続エラー：",e);
			throw new AsoLearningSystemErrException(e);

		} catch (SQLException e) {
			//ログ出力
			logger.warn("SQLエラー：",e);
			throw new AsoLearningSystemErrException(e);
		} finally{

			dao.close();
		}

		return avatorParts;
	}


	/**
	 * 指定した種類のアバター情報を取得する
	 * @param dao
	 * @param ansNum
	 * @param total
	 * @param kind
	 * @return
	 * @throws SQLException
	 */
	private AvatarDto getAvatarDto(AvatarDao dao,int ansNum ,int total,AvatarKind kind) throws SQLException{

		//DBよりその種類のアバター情報取得する
		List<AvatarMasterEntity> list = dao.getAvatarList(kind.getId(), ansNum, total);

		//取得した情報をDTOに変換する
		AvatarDto avatarDto = new AvatarDto();

		for( AvatarMasterEntity avatar : list){
			AvatarDto.Element element = avatarDto.new Element();

			//アバターIDとファイル名をセット
			element.setId(avatar.getAvatarId());
			element.setName(avatar.getFileName());

			//リストに追加
			avatarDto.addAvatarList(element);
		}
		avatarDto.setKind(kind);

		return avatarDto;
	}
}
