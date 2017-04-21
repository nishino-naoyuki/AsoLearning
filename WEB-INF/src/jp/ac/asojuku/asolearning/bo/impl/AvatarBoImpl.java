/**
 *
 */
package jp.ac.asojuku.asolearning.bo.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jp.ac.asojuku.asolearning.bo.AvatarBo;
import jp.ac.asojuku.asolearning.condition.SearchTaskResultCondition;
import jp.ac.asojuku.asolearning.dao.AvatarDao;
import jp.ac.asojuku.asolearning.dao.ResultDao;
import jp.ac.asojuku.asolearning.dto.AvatarDto;
import jp.ac.asojuku.asolearning.dto.AvatarPartsDto;
import jp.ac.asojuku.asolearning.dto.AvatarSettingDto;
import jp.ac.asojuku.asolearning.dto.LogonInfoDTO;
import jp.ac.asojuku.asolearning.entity.AvatarMasterEntity;
import jp.ac.asojuku.asolearning.entity.ResultTblEntity;
import jp.ac.asojuku.asolearning.exception.AsoLearningSystemErrException;
import jp.ac.asojuku.asolearning.exception.DBConnectException;
import jp.ac.asojuku.asolearning.param.AvatarKind;
import jp.ac.asojuku.asolearning.param.Difficalty;

/**
 * アバター実装処理
 * @author nishino
 *
 */
public class AvatarBoImpl implements AvatarBo {
	Logger logger = LoggerFactory.getLogger(AvatarBoImpl.class);

	public void updateAvatar(LogonInfoDTO userInfo,AvatarSettingDto avatarDto) throws AsoLearningSystemErrException{

		AvatarDao dao = new AvatarDao();

		try {

			//DB接続
			dao.connect();

			/////////////////////////////////////////
			//設定可能かのチェック
			boolean bChkOk = checkAvatarId(dao,userInfo.getUserId(),avatarDto);
			if( bChkOk == false ){
				throw new AsoLearningSystemErrException("アバターIDが不正に操作された可能性があります。");
			}

			//アバター種類のリスト
			AvatarKind[] kindList = AvatarKind.getList();

			StringBuilder sb = new StringBuilder();

			for( AvatarKind kind : kindList){
				int id = avatarDto.getAvatar(kind);
				if(sb.length() > 0){
					sb.append(",");
				}
				sb.append(id);
			}

			//アバターの設定を行う
			dao.update(sb.toString(), userInfo.getUserId());


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

	}

	/**
	 * アバターIDが不正なIDでないかをチェックする
	 * @param dao
	 * @param userId
	 * @param avatarDto
	 * @return
	 * @throws SQLException
	 */
	private boolean checkAvatarId(AvatarDao dao,Integer userId,AvatarSettingDto avatarDto) throws SQLException{

		boolean bOKflg = true;

		//解いた問題数と、合計得点を算出
		SearchTaskResultCondition avCond =
				getSearchAvatarCondition(dao.getConnection(),userId);


		//アバター種類のリスト
		AvatarKind[] kindList = AvatarKind.getList();

		for(AvatarKind kind : kindList){
			//設定しようとするアバターIDを取得
			Integer avatarId = avatarDto.getAvatar(kind);
			//DBよりその種類のアバター情報取得する
			List<AvatarMasterEntity> list = dao.getAvatarList(kind.getId(), avCond);
			//設定しようとするIDがリストにあるかをチェックする
			boolean bFind = false;
			for( AvatarMasterEntity avatar : list){
				if( avatar.getAvatarId() == avatarId ){
					bFind = true;
					break;
				}
			}
			//アバターIDがない場合は不正な捜査をされたので、エラー
			if( bFind == false ){
				logger.warn("アバターIDが不正に操作された可能性があります。avatarId="+avatarId);
				bOKflg = false;
				break;
			}
		}

		return bOKflg;
	}
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

			//解いた問題数と、合計得点を算出
			SearchTaskResultCondition avCond =
					getSearchAvatarCondition(dao.getConnection(),userInfo.getUserId());

			//アバター種類のリスト
			AvatarKind[] kindList = AvatarKind.getList();

			for(AvatarKind kind : kindList){
				AvatarDto dto = getAvatarDto(dao, avCond,kind);
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
	 * 問題を解いた結果を取得する
	 * @param con
	 * @param userId
	 * @return
	 * @throws SQLException
	 */
	private SearchTaskResultCondition getSearchAvatarCondition(Connection con,Integer userId) throws SQLException{

		ResultDao retDao = new ResultDao(con);

		List<ResultTblEntity> retList = retDao.getListByUser(userId);

		//解いた問題数と、合計得点を算出
		SearchTaskResultCondition avCond = new SearchTaskResultCondition();

		for( ResultTblEntity ret : retList ){

			Integer difficalty = ret.getTaskTbl().getDifficalty();
			float score = ret.getTotalScore();
			boolean handed = ( ret.getHanded() == 1 ? true: false);

			if( handed ){
				//提出済みの数を数える
				setAnswerNum(avCond,difficalty);
			}
			//点数を集計
			setTotalScore(avCond,difficalty,score);
		}

		return avCond;
	}

	/**
	 * 解答数を難易度別に集計する
	 * @param avCond
	 * @param difficalty
	 */
	private void setAnswerNum(SearchTaskResultCondition avCond,Integer difficalty ){

		if( Difficalty.EASY.equals(difficalty)){
			//簡単の解答数を追加
			avCond.addAnsConditionEasy();

		}else if(Difficalty.NORMAL.equals(difficalty)){
			//普通の解答数を追加
			avCond.addAnsConditionNormal();

		}else{
			//難しいの解答数を追加
			avCond.addAnsConditionHard();
		}
	}


	/**
	 * 合計点数を難易度別に集計する
	 * @param avCond
	 * @param difficalty
	 * @param score
	 */
	private void setTotalScore(SearchTaskResultCondition avCond,Integer difficalty,float score ){

		if( Difficalty.EASY.equals(difficalty)){
			//簡単の点数を追加
			avCond.addTotalConditionEasy(score);

		}else if(Difficalty.NORMAL.equals(difficalty)){
			//普通の点数を追加
			avCond.addTotalConditionNormal(score);

		}else{
			//難しいの点数を追加
			avCond.addTotalConditionHard(score);
		}
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
	private AvatarDto getAvatarDto(AvatarDao dao,SearchTaskResultCondition avCond,AvatarKind kind) throws SQLException{

		//DBよりその種類のアバター情報取得する
		List<AvatarMasterEntity> list = dao.getAvatarList(kind.getId(), avCond);

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
