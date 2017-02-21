/**
 *
 */
package jp.ac.asojuku.asolearning.bo.impl;

import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jp.ac.asojuku.asolearning.bo.LoginBo;
import jp.ac.asojuku.asolearning.dao.UserDao;
import jp.ac.asojuku.asolearning.dto.LogonInfoDTO;
import jp.ac.asojuku.asolearning.entity.UserTblEntity;
import jp.ac.asojuku.asolearning.exception.AccountLockedException;
import jp.ac.asojuku.asolearning.exception.AsoLearningSystemErrException;
import jp.ac.asojuku.asolearning.exception.DBConnectException;
import jp.ac.asojuku.asolearning.exception.LoginFailureException;
import jp.ac.asojuku.asolearning.util.Digest;

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
					dao.getUserInfoByUserName(mailadress);

			//ユーザー状態チェック
			vlidateUserState(entity,mailadress,password);

			//会員テーブル→ログイン情報
			login = MemberEntityToLogonInfoDTO(entity);

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
	 */
	private LogonInfoDTO MemberEntityToLogonInfoDTO(UserTblEntity entity){

		if( entity == null ){
			return null;
		}

		LogonInfoDTO loginDto = new LogonInfoDTO();

		loginDto.setName(entity.getName());
		loginDto.setNickName(entity.getNickName());
		loginDto.setCourseId(entity.getCourseMaster().getCourseId());
		loginDto.setUserId(entity.getUserId());
		loginDto.setRoleId(entity.getRoleMaster().getRoleId());

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
	 */
	private void vlidateUserState(UserTblEntity entity,String mailadress,String password)throws LoginFailureException, AsoLearningSystemErrException, AccountLockedException{

		if( entity == null){
			throw new LoginFailureException();
		}

		//パスワードのハッシュ値計算
		String hashPwd = Digest.createPassword(mailadress,password);

		if( !hashPwd.equals(entity.getPassword())){
			//パスワード認証失敗かうんとアップ:TODO
			throw new LoginFailureException();

		}

		//ロックフラグ
		if( entity.getIsLockFlg() == 1 ){
			throw new AccountLockedException();
		}

		//TODO:期限切れ
	}
}
