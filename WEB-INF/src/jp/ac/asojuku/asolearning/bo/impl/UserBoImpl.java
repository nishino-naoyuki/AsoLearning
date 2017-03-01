package jp.ac.asojuku.asolearning.bo.impl;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opencsv.CSVReader;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;

import jp.ac.asojuku.asolearning.bo.UserBo;
import jp.ac.asojuku.asolearning.condition.SearchUserCondition;
import jp.ac.asojuku.asolearning.config.AppSettingProperty;
import jp.ac.asojuku.asolearning.csv.model.UserCSV;
import jp.ac.asojuku.asolearning.dao.HistoryDao;
import jp.ac.asojuku.asolearning.dao.TaskDao;
import jp.ac.asojuku.asolearning.dao.UserDao;
import jp.ac.asojuku.asolearning.dto.CSVProgressDto;
import jp.ac.asojuku.asolearning.dto.TaskResultDto;
import jp.ac.asojuku.asolearning.dto.UserDetailDto;
import jp.ac.asojuku.asolearning.dto.UserDto;
import jp.ac.asojuku.asolearning.dto.UserSearchResultDto;
import jp.ac.asojuku.asolearning.entity.CourseMasterEntity;
import jp.ac.asojuku.asolearning.entity.ResultTblEntity;
import jp.ac.asojuku.asolearning.entity.RoleMasterEntity;
import jp.ac.asojuku.asolearning.entity.TaskTblEntity;
import jp.ac.asojuku.asolearning.entity.UserTblEntity;
import jp.ac.asojuku.asolearning.err.ActionErrors;
import jp.ac.asojuku.asolearning.err.ErrorCode;
import jp.ac.asojuku.asolearning.exception.AsoLearningSystemErrException;
import jp.ac.asojuku.asolearning.exception.DBConnectException;
import jp.ac.asojuku.asolearning.param.ActionId;
import jp.ac.asojuku.asolearning.param.SessionConst;
import jp.ac.asojuku.asolearning.util.DateUtil;
import jp.ac.asojuku.asolearning.util.Digest;
import jp.ac.asojuku.asolearning.util.UserUtils;
import jp.ac.asojuku.asolearning.validator.UserValidator;

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

			//動作ログをセット
			HistoryDao history = new HistoryDao(dao.getConnection());
			history.insert(entity.getUserId(), ActionId.USER_CREATE.getId(), "");

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
	 * @throws AsoLearningSystemErrException
	 */
	public UserTblEntity mergeUserTblEntityFrom(UserCSV user,UserTblEntity baseEntity) throws AsoLearningSystemErrException{

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
		entity.setAdmissionYear(( StringUtils.isEmpty( user.getAdmissionYear() ) ? null: Integer.parseInt(user.getAdmissionYear())));
		entity.setName( user.getName() );
		entity.setNickName( Digest.encNickName( user.getNickName(),user.getMailAddress() ));
		entity.setPassword(user.getPassword());

		role.setRoleId(user.getRoleId());
		course.setCourseId(user.getCourseId());

		entity.setRoleMaster(role);
		entity.setCourseMaster(course);

		return entity;
	}
	/**
	 * ユーザー情報（新規登録・更新用）
	 * ※チェック済みのデータであることが前提
	 * @param dto
	 * @return
	 * @throws AsoLearningSystemErrException
	 */
	private UserTblEntity getUserTblEntityFrom(UserDto dto) throws AsoLearningSystemErrException{
		UserTblEntity entity = new UserTblEntity();

		entity.setUserId(dto.getUserId());
		entity.setMailadress(dto.getMailAdress());
		entity.setName(dto.getName());
		entity.setNickName( Digest.encNickName(dto.getNickName(), dto.getMailAdress()) );
		entity.setAccountExpryDate(dto.getAccountExpryDate());
		entity.setPasswordExpirydate(dto.getAccountExpryDate());
		entity.setAdmissionYear(Integer.parseInt(dto.getAdmissionYear()));

		CourseMasterEntity courseMaster = new CourseMasterEntity();

		courseMaster.setCourseId(dto.getCourseId());
		entity.setCourseMaster(courseMaster);

		RoleMasterEntity roleMasterEntity = new RoleMasterEntity();
		roleMasterEntity.setRoleId(dto.getRoleId());
		entity.setRoleMaster(roleMasterEntity);
		return entity;
	}

	/* (非 Javadoc)
	 * @see jp.ac.asojuku.asolearning.bo.UserBo#checkForCSV(java.lang.String, jp.ac.asojuku.asolearning.err.ActionErrors, java.lang.String)
	 */
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
        		UserValidator.useName(userCsv.getName(), errors);
        		UserValidator.useNickName(userCsv.getNickName(), errors);
        		UserValidator.roleId(String.valueOf(userCsv.getRoleId()), errors);
        		//UserValidator.courseId(String.valueOf(userCsv.getRoleId()), list, errors);
        		UserValidator.admissionYear(userCsv.getAdmissionYear(), errors);
        		UserValidator.mailAddress(userCsv.getMailAddress(), errors);
        		UserValidator.password(userCsv.getPassword(), errors);
            }

        } catch (Exception e) {
        	logger.warn("CSVパースエラー：",e);
        	errors.add(ErrorCode.ERR_CSV_FORMAT_ERROR);
        }

		return list;
	}

	@Override
	public List<UserSearchResultDto> search(SearchUserCondition cond) throws AsoLearningSystemErrException {

		List<UserSearchResultDto> list = new ArrayList<UserSearchResultDto> ();
		UserDao dao = new UserDao();

		try {

			//DB接続
			dao.connect();

			//課題リスト情報を取得
			List<UserTblEntity> useEntityList = dao.search(cond);

			//Entity -> Dto
			for( UserTblEntity entiy : useEntityList ){
				UserSearchResultDto dto = getUserSearchResultDtoFromEntity(entiy,cond);
				if( dto != null){
					list.add( dto );
				}
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

		return list;
	}

	/**
	 * Entity -> DTO
	 * @param entiy
	 * @return
	 * @throws AsoLearningSystemErrException
	 */
	private UserSearchResultDto getUserSearchResultDtoFromEntity(UserTblEntity entiy,SearchUserCondition cond) throws AsoLearningSystemErrException{
		UserSearchResultDto dto = null;

		//学年の検索条件はここでは軸
		if( isNotSettingCondition(cond) ||	isMatchGrade(cond,UserUtils.getGrade(entiy)) ){
			dto = new UserSearchResultDto();

			//ユーザー情報をセット
			dto.setUserDto(getUserDtoFromEntity(entiy));

			//結果をセット
			if( CollectionUtils.isNotEmpty(entiy.getResultTblSet()) ){
				for(ResultTblEntity result : entiy.getResultTblSet()){
					TaskResultDto retDto = new TaskResultDto();

					retDto.setHanded( (result.getHanded()==0?false:true) );
					retDto.setTotal(result.getTotalScore());
					retDto.setTaskName(result.getTaskTbl().getName());
					retDto.setHandedDate( DateUtil.formattedDate(result.getHandedTimestamp(), "yyyy/MM/dd HH:mm:ss"));

					dto.addResultList(retDto);
				}

			}
		}

		return dto;
	}

	public boolean isNotSettingCondition(SearchUserCondition cond){
		return cond.getGrade() == null;
	}
	public boolean isMatchGrade(SearchUserCondition cond,Integer grade){

		if( grade == null && cond.getGrade() == null){
			return true;
		}
		if( grade ==  cond.getGrade()){
			return true;
		}

		return false;
	}
	/**
	 * Entity -> DTO
	 * @param entiy
	 * @return
	 * @throws AsoLearningSystemErrException
	 */
	private UserDto getUserDtoFromEntity(UserTblEntity entiy) throws AsoLearningSystemErrException{
		UserDto userDto = new UserDto();

		userDto.setUserId( entiy.getUserId() );
		userDto.setMailAdress( entiy.getMailadress() );
		userDto.setName( entiy.getName() );
		userDto.setNickName( Digest.decNickName(entiy.getNickName(),entiy.getMailadress()) );
		userDto.setAccountExpryDate( entiy.getAccountExpryDate() );
		userDto.setPasswordExpriryDate( entiy.getPasswordExpirydate() );
		userDto.setCourseId( entiy.getCourseMaster().getCourseId() );
		userDto.setRoleId( entiy.getRoleMaster().getRoleId() );
		userDto.setFirstFlg( (entiy.getIsFirstFlg()==1?true:false) );
		userDto.setCertifyErrCnt(entiy.getCertifyErrCnt() );
		userDto.setLockFlg( (entiy.getIsLockFlg()==1?true:false) );
		userDto.setAdmissionYear( (entiy.getAdmissionYear()==null ? "":entiy.getAdmissionYear().toString()) );
		userDto.setGraduateYear( (entiy.getGraduateYear()==null ? "":entiy.getGraduateYear().toString()) );
		userDto.setRepeatYearCount( (entiy.getRepeatYearCount()==null ? "":entiy.getRepeatYearCount().toString()) );
		userDto.setCourseName(entiy.getCourseMaster().getCourseName());
		userDto.setRoleName(entiy.getRoleMaster().getRoleName());
		userDto.setGrade(UserUtils.getGrade(entiy));

		return userDto;
	}

	@Override
	public UserDetailDto detail(Integer userId) throws AsoLearningSystemErrException {

		UserDetailDto dto = new UserDetailDto();
		UserDao dao = new UserDao();

		try {

			//DB接続
			dao.connect();

			TaskDao taskdao = new TaskDao(dao.getConnection());
			//ユーザー情報を取得
			UserTblEntity userEntity = dao.detail(userId);
			//課題リスト情報を取得
			List<TaskTblEntity> entityList =
					taskdao.getTaskList(userId, userEntity.getCourseMaster().getCourseId(), 0, 10);

			//Entity -> Dto
			dto = getUserDetailDtoFromEntity(userEntity,entityList);

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

		return dto;
	}

	/**
	 * Entity -> DTO
	 * @param entiy
	 * @return
	 * @throws AsoLearningSystemErrException
	 */
	private UserDetailDto getUserDetailDtoFromEntity(UserTblEntity entiy,List<TaskTblEntity> entityList) throws AsoLearningSystemErrException{
		UserDetailDto userDto = new UserDetailDto();

		userDto.setUserId( entiy.getUserId() );
		userDto.setMailAdress( entiy.getMailadress() );
		userDto.setName( entiy.getName() );
		userDto.setNickName( Digest.decNickName(entiy.getNickName(),entiy.getMailadress()) );
		userDto.setAccountExpryDate( entiy.getAccountExpryDate() );
		userDto.setPasswordExpriryDate( entiy.getPasswordExpirydate() );
		userDto.setCourseId( entiy.getCourseMaster().getCourseId() );
		userDto.setRoleId( entiy.getRoleMaster().getRoleId() );
		userDto.setFirstFlg( (entiy.getIsFirstFlg()==1?true:false) );
		userDto.setCertifyErrCnt(entiy.getCertifyErrCnt() );
		userDto.setLockFlg( (entiy.getIsLockFlg()==1?true:false) );
		userDto.setAdmissionYear( (entiy.getAdmissionYear()==null ? "":entiy.getAdmissionYear().toString()) );
		userDto.setGraduateYear( (entiy.getGraduateYear()==null ? "":entiy.getGraduateYear().toString()) );
		userDto.setRepeatYearCount( (entiy.getRepeatYearCount()==null ? "":entiy.getRepeatYearCount().toString()) );
		userDto.setCourseName(entiy.getCourseMaster().getCourseName());
		userDto.setRoleName(entiy.getRoleMaster().getRoleName());
		userDto.setGrade(UserUtils.getGrade(entiy));

		for( TaskTblEntity task : entityList ){
			TaskResultDto retDto = new TaskResultDto();

			if( CollectionUtils.isEmpty( task.getResultTblSet() ) ){
				retDto.setHanded(false);
			}else{
				ResultTblEntity result = task.getResultTblSet().iterator().next();

				retDto.setHanded( (result.getHanded()==0?false:true) );
				retDto.setTotal(result.getTotalScore());
				retDto.setHandedDate( DateUtil.formattedDate(result.getHandedTimestamp(), "yyyy/MM/dd HH:mm:ss"));
			}
			retDto.setTaskId(task.getTaskId());
			retDto.setTaskName(task.getName());

			userDto.addResultList(retDto);
		}

		return userDto;
	}

	@Override
	public void updatePassword(Integer userId, String password,String maileaddress) throws AsoLearningSystemErrException {

		UserDao dao = new UserDao();

		try {

			//DB接続
			dao.connect();

			//パスワードのハッシュ値計算
			String hashPwd = Digest.createPassword(maileaddress,password);

			//パスワード変更
			dao.updatePassword(userId, hashPwd);

			//有効期限を設定ファイルから取得
			Integer pwdExp = getPwdExpiry();
			if( pwdExp != null ){
				//ユーザー情報を取得しなおす
				UserTblEntity userEntity = dao.detail(userId);
				Date expDate = userEntity.getAccountExpryDate();
				//設定ファイルの値を足したものを渡す
				dao.updatePassLimit(userId, DateUtil.plusDay(expDate, pwdExp));
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

	}

	/**
	 * パスワードの有効期限を取得する
	 * @return
	 * @throws AsoLearningSystemErrException
	 */
	private Integer getPwdExpiry() throws AsoLearningSystemErrException{
		Integer pwdExpInt = null;
		String pwdExp = AppSettingProperty.getInstance().getPwdExpiry();

		if(StringUtils.isNotEmpty(pwdExp)){
			try{
				pwdExpInt = Integer.parseInt(pwdExp);
			}catch(NumberFormatException e){
				pwdExpInt = null;
			}
		}

		return pwdExpInt;
	}

	@Override
	public void updateNickName(Integer userId, String nickName,String maileaddress) throws AsoLearningSystemErrException {

		UserDao dao = new UserDao();

		try {

			//DB接続
			dao.connect();

			//パスワードのハッシュ値計算
			String encNickName = Digest.encNickName(nickName, maileaddress);

			//ニックネーム変更
			dao.updateNickName(userId, encNickName);

			//動作ログをセット
			HistoryDao history = new HistoryDao(dao.getConnection());
			history.insert(userId, ActionId.NICK_NAME.getId(), "");

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
}
