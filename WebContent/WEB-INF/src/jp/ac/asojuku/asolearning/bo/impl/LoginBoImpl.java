/**
 *
 */
package jp.ac.asojuku.asolearning.bo.impl;

import java.sql.SQLException;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jp.ac.asojuku.asolearning.bo.LoginBo;
import jp.ac.asojuku.asolearning.config.AppSettingProperty;
import jp.ac.asojuku.asolearning.dao.AvatarDao;
import jp.ac.asojuku.asolearning.dao.HistoryDao;
import jp.ac.asojuku.asolearning.dao.UserDao;
import jp.ac.asojuku.asolearning.dto.AvatarSettingDto;
import jp.ac.asojuku.asolearning.dto.LogonInfoDTO;
import jp.ac.asojuku.asolearning.entity.AvatarMasterEntity;
import jp.ac.asojuku.asolearning.entity.UserTblEntity;
import jp.ac.asojuku.asolearning.exception.AccountLockedException;
import jp.ac.asojuku.asolearning.exception.AsoLearningSystemErrException;
import jp.ac.asojuku.asolearning.exception.DBConnectException;
import jp.ac.asojuku.asolearning.exception.LoginFailureException;
import jp.ac.asojuku.asolearning.param.ActionId;
import jp.ac.asojuku.asolearning.param.AvatarKind;
import jp.ac.asojuku.asolearning.param.RoleId;
import jp.ac.asojuku.asolearning.util.Digest;
import jp.ac.asojuku.asolearning.util.UserUtils;

/**
 * ログイン処理
 * @author nishino
 *
 */
public class LoginBoImpl implements LoginBo {

	Logger logger = LoggerFactory.getLogger(LoginBoImpl.class);

	@Override
	public LogonInfoDTO login(String mailadress, String password) throws LoginFailureException, AsoLearningSystemErrException, AccountLockedException {

		LogonInfoDTO login = new LogonInfoDTO();

		UserDao dao = new UserDao();

		try {

			//DB接続
			dao.connect();

			//ユーザー情報を取得
			UserTblEntity entity =
					dao.getUserInfoByMailAddress(mailadress);

			//ユーザー状態チェック
			vlidateUserState(entity,dao,mailadress,password);

			AvatarDao avatarDao = new AvatarDao(dao.getConnection());
			//会員テーブル→ログイン情報
			login = MemberEntityToLogonInfoDTO(avatarDao,entity);

			//動作ログをセット
			HistoryDao history = new HistoryDao(dao.getConnection());
			history.insert(entity.getUserId(), ActionId.LOGIN.getId(), "");

		} catch (DBConnectException e) {
			//ログ出力
			logger.warn("DB接続エラー：",e);
			throw new LoginFailureException();

		} catch (SQLException e) {
			//ログ出力
			logger.warn("SQLエラー：",e);
			throw new LoginFailureException();
		} catch (AccountLockedException e) {
			logger.warn("アカウントがロックされている：",e);
			throw new AccountLockedException();
		} finally{

			dao.close();
		}

		return login;
	}

	/**
	 * 会員情報よりログイン情報を取得する
	 * @param entity
	 * @return
	 * @throws AsoLearningSystemErrException
	 * @throws SQLException
	 * @throws NumberFormatException
	 */
	private LogonInfoDTO MemberEntityToLogonInfoDTO(AvatarDao avatarDao,UserTblEntity entity) throws AsoLearningSystemErrException, NumberFormatException, SQLException{

		if( entity == null ){
			return null;
		}

		LogonInfoDTO loginDto = new LogonInfoDTO();

		loginDto.setName(entity.getName());
		loginDto.setNickName( Digest.decNickName( entity.getNickName(),entity.getMailadress()));
		loginDto.setCourseId(entity.getCourseMaster().getCourseId());
		loginDto.setUserId(entity.getUserId());
		loginDto.setRoleId(entity.getRoleMaster().getRoleId());
		loginDto.setCourseName(entity.getCourseMaster().getCourseName());
		loginDto.setRoleName(RoleId.search(entity.getRoleMaster().getRoleId()).getMsg());
		loginDto.setGrade(UserUtils.getGrade(entity));
		loginDto.setMailAddress(entity.getMailadress());
		//アバターのCSV→AvatarSettingDto
		AvatarSettingDto dto = new AvatarSettingDto();
		String avatarList = entity.getAbatarIdList();

		if( StringUtils.isNotEmpty(avatarList) ){
			//アバターのリストがあれば、カンマで区切ってIDとファイル名を取得する
			String[] avatars = avatarList.split(",");
			for( int i = 0; i < avatars.length; i++ ){
				AvatarMasterEntity avaEntity = avatarDao.getBy(Integer.parseInt(avatars[i]));
				dto.setAvatarDto(AvatarKind.search(i), Integer.parseInt(avatars[i]),avaEntity.getFileName());
			}
		}
		loginDto.setAvatar(dto);

		return loginDto;
	}

	/**
	 * ユーザーの状態を確認
	 * ・有効期限
	 * ・ロック状態
	 *
	 * @param entity
	 * @throws AsoLearningSystemErrException
	 * @throws AccountLockedException
	 * @throws SQLException
	 */
	private void vlidateUserState(UserTblEntity entity,UserDao dao,String mailadress,String password)throws LoginFailureException, AsoLearningSystemErrException, AccountLockedException, SQLException{

		if( entity == null){
			throw new LoginFailureException();
		}

		//パスワードのハッシュ値計算
		String hashPwd = Digest.createPassword(mailadress,password);

		//ロックフラグ
		if( entity.getIsLockFlg() == 1 ){
			throw new AccountLockedException();
		}

		//期限切れ
		Date now = new Date();

		if(
			StringUtils.isNotEmpty(AppSettingProperty.getInstance().getPwdExpiry()) &&
			now.after(entity.getAccountExpryDate())
			){
			throw new LoginFailureException();
		}

		if( !hashPwd.equals(entity.getPassword())){
			//パスワード認証失敗かうんとアップ
			Integer limit = getPasswordLockLimit();
			if( limit != null ){
				entity.setCertifyErrCnt(entity.getCertifyErrCnt()+1);
				dao.updateCertErrCnt(entity.getUserId(), entity.getCertifyErrCnt());

				logger.warn("認証失敗回数アップ："+entity.getCertifyErrCnt());

				if( entity.getCertifyErrCnt() > limit ){
					//ロック
					logger.warn("アカウントロック");
					dao.updateLockFlg(entity.getUserId(), 1);
				}
			}
			throw new LoginFailureException();

		}


	}

	/**
	 * パスワード閾値を取得
	 * @return
	 * @throws AsoLearningSystemErrException
	 */
	private Integer getPasswordLockLimit() throws AsoLearningSystemErrException{
		Integer lockLimit = null;
		String limit = AppSettingProperty.getInstance().getPwdLockLmit();

		try{
			lockLimit = Integer.parseInt(limit);
		}catch(NumberFormatException e){
			lockLimit = null;
		}

		return lockLimit;
	}
}
