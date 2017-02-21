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
		session.setAttribute(SessionConst.SESSION_CSV_PROGRESS, progress);

		///////////////////////////////////////
		//処理を行う
		for( UserCSV user : userList){

		}

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
