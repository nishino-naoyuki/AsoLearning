package jp.ac.asojuku.asolearning.bo.impl;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opencsv.CSVReader;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;

import jp.ac.asojuku.asolearning.bo.UserBo;
import jp.ac.asojuku.asolearning.csv.model.UserCSV;
import jp.ac.asojuku.asolearning.dao.UserDao;
import jp.ac.asojuku.asolearning.dto.CSVProgressDto;
import jp.ac.asojuku.asolearning.dto.UserDto;
import jp.ac.asojuku.asolearning.entity.CourseMasterEntity;
import jp.ac.asojuku.asolearning.entity.RoleMasterEntity;
import jp.ac.asojuku.asolearning.entity.UserTblEntity;
import jp.ac.asojuku.asolearning.err.ActionErrors;
import jp.ac.asojuku.asolearning.exception.AsoLearningSystemErrException;
import jp.ac.asojuku.asolearning.exception.DBConnectException;
import jp.ac.asojuku.asolearning.param.SessionConst;
import jp.ac.asojuku.asolearning.util.Digest;

public class UserBoImpl implements UserBo {
	Logger logger = LoggerFactory.getLogger(UserBoImpl.class);
	private static final String[] HEADER = new String[] { "roleId", "name", "mailAddress", "nickName", "courseId", "password","admissionYear" };
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
	public void insertByCSV(List<UserCSV> userList,String uuid,HttpSession session,String type) throws AsoLearningSystemErrException {

		CSVProgressDto progress = new CSVProgressDto(userList.size(),0);
		///////////////////////////////////////
		//初期情報をセッションにセット
		session.setAttribute(uuid+SessionConst.SESSION_CSV_PROGRESS, progress);

		UserDao dao = new UserDao();

		try {

			//DB接続
			dao.connect();
			//トランザクション
			dao.beginTranzaction();
			///////////////////////////////////////
			//処理を行う
			for( UserCSV user : userList){
				//もうちょっと良い方法がありそうだが、ココだけのためにクラス作るのも
				//面倒なので、こうする。。。。(> <)
				if("rdoInsertUpdate".equalsIgnoreCase(type)){
					//追加・更新
					insertOrUpdate(dao,user);
				}else if("rdoDelete".equalsIgnoreCase(type)){
					//削除

				}else if("rdoGraduate".equalsIgnoreCase(type)){
					//卒業

				}else{
					//退学

				}
				///////////////////////////////////////
				//セッションの情報を更新
				updateSessionInfo(uuid,session,progress);
			}

			dao.commit();

		} catch (DBConnectException e) {
			//ログ出力
			logger.warn("DB接続エラー：",e);
			dao.rollback();
			throw new AsoLearningSystemErrException(e);

		} catch (SQLException e) {
			//ログ出力
			logger.warn("SQLエラー：",e);
			dao.rollback();
			throw new AsoLearningSystemErrException(e);
		} finally{

			dao.close();
		}

	}

	/**
	 * 処理件数をアップし、セッションの情報をカウントアップ
	 * @param session
	 * @param progress
	 */
	private void updateSessionInfo(String uuid,HttpSession session,CSVProgressDto progress){
		progress.addNow();
		session.setAttribute(uuid+SessionConst.SESSION_CSV_PROGRESS, progress);
	}

	/**
	 * 追加・更新処理
	 * @param dao
	 * @param user
	 * @throws SQLException
	 * @throws AsoLearningSystemErrException
	 */
	public void insertOrUpdate(UserDao dao,UserCSV user) throws SQLException, AsoLearningSystemErrException{

		UserTblEntity userEntity = dao.getUserInfoByMailAddress(user.getMailAddress());

		boolean insertFlg = (userEntity==null);

		//取得したEnityとCSVのデータをマージする
		userEntity = mergeUserTblEntityFrom(user,userEntity);
		//パスワードのハッシュ値計算
		String hashPwd = Digest.createPassword(userEntity.getMailadress(),userEntity.getPassword());

		if( insertFlg ){
			//新規追加
			dao.insert(userEntity,hashPwd);
		}else{
			//更新
			dao.update(userEntity, hashPwd);
		}
	}

	/**
	 * CSVのデータからEntityを作成する
	 * @param user
	 * @param baseEntity
	 * @return
	 */
	public UserTblEntity mergeUserTblEntityFrom(UserCSV user,UserTblEntity baseEntity){

		UserTblEntity entity = baseEntity;
		CourseMasterEntity course = null;
		RoleMasterEntity role = null;
		if( entity == null ){
			entity = new UserTblEntity();
			role = new RoleMasterEntity();
			course = new CourseMasterEntity();

		}else{
			role = entity.getRoleMaster();
			course = entity.getCourseMaster();
		}

		entity.setMailadress(user.getMailAddress());
		entity.setAdmissionYear(user.getAdmissionYear());
		entity.setName( user.getName() );
		entity.setNickName(user.getNickName());
		entity.setPassword(user.getPassword());

		role.setRoleId(user.getRoleId());
		course.setCourseId(user.getCourseId());

		entity.setRoleMaster(role);
		entity.setCourseMaster(course);

		return entity;
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

	@Override
	public List<UserCSV> checkForCSV(String csvPath, ActionErrors errors,String type) throws AsoLearningSystemErrException {

		List<UserCSV> list = null;
		try {
			///////////////////////////////
			//CSVを読み込みマッピング
            CSVReader reader = new CSVReader(new InputStreamReader(new FileInputStream(csvPath), "SJIS"), ',', '"', 1);
            ColumnPositionMappingStrategy<UserCSV> strat = new ColumnPositionMappingStrategy<UserCSV>();
            strat.setType(UserCSV.class);
            strat.setColumnMapping(HEADER);
            CsvToBean<UserCSV> csv = new CsvToBean<UserCSV>();
            list = csv.parse(strat, reader);

            // エラーチェック
            for(UserCSV userCsv : list){
            	//TODOエラーチェック
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

		return list;
	}
}
