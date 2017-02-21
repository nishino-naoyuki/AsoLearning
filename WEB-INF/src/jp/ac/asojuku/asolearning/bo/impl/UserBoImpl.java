package jp.ac.asojuku.asolearning.bo.impl;

import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jp.ac.asojuku.asolearning.bo.UserBo;
import jp.ac.asojuku.asolearning.dao.UserDao;
import jp.ac.asojuku.asolearning.dto.UserDto;
import jp.ac.asojuku.asolearning.entity.CourseMasterEntity;
import jp.ac.asojuku.asolearning.entity.RoleMasterEntity;
import jp.ac.asojuku.asolearning.entity.UserTblEntity;
import jp.ac.asojuku.asolearning.exception.AsoLearningSystemErrException;
import jp.ac.asojuku.asolearning.exception.DBConnectException;
import jp.ac.asojuku.asolearning.util.Digest;

public class UserBoImpl implements UserBo {
	Logger logger = LoggerFactory.getLogger(UserBoImpl.class);

	@Override
	public void insert(UserDto userDto) throws AsoLearningSystemErrException {


		if( userDto == null ){
			return;
		}

		UserDao dao = new UserDao();

		try {

			//DB接続
			dao.connect();

			UserTblEntity entity = getUserTblEntityFrom(userDto);

			//パスワードのハッシュ値計算
			String hashPwd = Digest.createPassword(userDto.getMailAdress(),userDto.getPassword());

			//課題リスト情報を取得
			dao.insert(entity,hashPwd);

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

	@Override
	public void insertByCSV(String csvPath) throws AsoLearningSystemErrException {
		// TODO 自動生成されたメソッド・スタブ

	}

	/**
	 * ユーザー情報（新規登録・更新用）
	 * @param dto
	 * @return
	 */
	private UserTblEntity getUserTblEntityFrom(UserDto dto){
		UserTblEntity entity = new UserTblEntity();

		entity.setUserId(dto.getUserId());
		entity.setMailadress(dto.getMailAdress());
		entity.setName(dto.getName());
		entity.setNickName(dto.getNickName());
		entity.setAccountExpryDate(dto.getAccountExpryDate());
		entity.setPasswordExpirydate(dto.getAccountExpryDate());
		entity.setAdmissionYear(dto.getAdmissionYear());

		CourseMasterEntity courseMaster = new CourseMasterEntity();

		courseMaster.setCourseId(dto.getCourseId());
		entity.setCourseMaster(courseMaster);

		RoleMasterEntity roleMasterEntity = new RoleMasterEntity();
		roleMasterEntity.setRoleId(dto.getRoleId());
		entity.setRoleMaster(roleMasterEntity);
		return entity;
	}
}
