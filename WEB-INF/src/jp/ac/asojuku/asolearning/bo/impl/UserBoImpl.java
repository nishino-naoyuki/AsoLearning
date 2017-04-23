package jp.ac.asojuku.asolearning.bo.impl;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
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
import jp.ac.asojuku.asolearning.dao.AvatarDao;
import jp.ac.asojuku.asolearning.dao.HistoryDao;
import jp.ac.asojuku.asolearning.dao.TaskDao;
import jp.ac.asojuku.asolearning.dao.UserDao;
import jp.ac.asojuku.asolearning.dto.AvatarSettingDto;
import jp.ac.asojuku.asolearning.dto.CSVProgressDto;
import jp.ac.asojuku.asolearning.dto.LogonInfoDTO;
import jp.ac.asojuku.asolearning.dto.TaskResultDto;
import jp.ac.asojuku.asolearning.dto.UserDetailDto;
import jp.ac.asojuku.asolearning.dto.UserDto;
import jp.ac.asojuku.asolearning.dto.UserSearchResultDto;
import jp.ac.asojuku.asolearning.entity.AvatarMasterEntity;
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
import jp.ac.asojuku.asolearning.param.AvatarKind;
import jp.ac.asojuku.asolearning.param.RoleId;
import jp.ac.asojuku.asolearning.param.SessionConst;
import jp.ac.asojuku.asolearning.util.DateUtil;
import jp.ac.asojuku.asolearning.util.Digest;
import jp.ac.asojuku.asolearning.util.FileUtils;
import jp.ac.asojuku.asolearning.util.TimestampUtil;
import jp.ac.asojuku.asolearning.util.UserUtils;
import jp.ac.asojuku.asolearning.validator.UserValidator;

public class UserBoImpl implements UserBo {
	private final String CSV_PREFIX_USERTASK = "usertask_";
	private final String CSV_PREFIX = "userdata_";
	Logger logger = LoggerFactory.getLogger(UserBoImpl.class);
	private static final String[] HEADER = new String[] { "roleId", "name", "mailAddress", "nickName", "courseId", "password","admissionYear" };
	@Override
	public void insert(UserDto userDto,LogonInfoDTO loginInfo) throws AsoLearningSystemErrException {


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
			history.insert(loginInfo.getUserId(), ActionId.USER_CREATE.getId(), "");

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
		entity.setRemark(dto.getRemark());

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
        		if( RoleId.STUDENT.equals(userCsv.getRoleId())){
        			UserValidator.admissionYear(userCsv.getAdmissionYear(), errors);
        		}
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
			AvatarDao avatarDao = new AvatarDao(dao.getConnection());
			dto = getUserDetailDtoFromEntity(userEntity,entityList,avatarDao);

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
	 * @throws SQLException
	 * @throws NumberFormatException
	 */
	private UserDetailDto getUserDetailDtoFromEntity(UserTblEntity entiy,List<TaskTblEntity> entityList,AvatarDao avatarDao) throws AsoLearningSystemErrException, NumberFormatException, SQLException{
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

		///////////////////////////////////////
		//アバターのCSV→AvatarSettingDto
		AvatarSettingDto dto = new AvatarSettingDto();
		String avatarList = entiy.getAbatarIdList();

		if( StringUtils.isNotEmpty(avatarList) ){
			//アバターのリストがあれば、カンマで区切ってIDとファイル名を取得する
			String[] avatars = avatarList.split(",");
			for( int i = 0; i < avatars.length; i++ ){
				AvatarMasterEntity avaEntity = avatarDao.getBy(Integer.parseInt(avatars[i]));
				dto.setAvatarDto(AvatarKind.search(i), Integer.parseInt(avatars[i]),avaEntity.getFileName());
			}
		}
		userDto.setAvatar(dto);

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

			//動作ログをセット
			HistoryDao history = new HistoryDao(dao.getConnection());
			history.insert(userId, ActionId.PWD_CHANGE.getId(), "");

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

	@Override
	public String createUserCSV(List<UserSearchResultDto> userList) throws AsoLearningSystemErrException {

		String csvDir = AppSettingProperty.getInstance().getCsvDir();
		String fileEnc = AppSettingProperty.getInstance().getCsvFileEncode();
		String fname = "";
		StringBuilder sb = new StringBuilder();
		String head = "学科,学年,学籍番号,ニックネーム,メールアドレス";

		String timeStr =
				TimestampUtil.formattedTimestamp(TimestampUtil.current(), "yyyyMMddHHmmssSSS");
		fname = CSV_PREFIX+timeStr+".csv";

        FileOutputStream fos = null;
        OutputStreamWriter osw = null;
        BufferedWriter writer = null;

        try{
        	fos = new FileOutputStream(csvDir+"/"+fname);
        	osw = new OutputStreamWriter(fos,fileEnc);
        	writer =new BufferedWriter(osw);

        	//ファイル出力
    		sb.append(head);
    		sb.append("\n");
    		osw.write(sb.toString());

    		for( UserSearchResultDto user : userList ){
    			StringBuilder userSb = new StringBuilder();
    			UserDto userDto = user.getUserDto();
    			userSb.append(userDto.getCourseName()).append(",");
    			userSb.append(userDto.getGrade()).append(",");
    			userSb.append(userDto.getName()).append(",");
    			userSb.append(userDto.getNickName()).append(",");
    			userSb.append(userDto.getMailAdress()).append("\n");
    			writer.write(userSb.toString());
    		}

    		writer.flush();
		} catch (IOException e) {
        	logger.warn("IOException：",e);
        	FileUtils.delete(fname);
			throw new AsoLearningSystemErrException(e);
		}finally{
			//クローズ
        	if(fos != null){
        		try {
					fos.close();
				} catch (IOException e) {
					;//ignore
				}
        	}

        	if(osw != null){
        		try {
        			osw.close();
				} catch (IOException e) {
					;//ignore
				}
        	}

        	if(writer != null){
        		try {
        			writer.close();
				} catch (IOException e) {
					;//ignore
				}
        	}
        }



		return fname;
	}

	@Override
	public String createTaskUserCSV(SearchUserCondition cond) throws AsoLearningSystemErrException {

		UserDao dao = new UserDao();
		String fname = "";

		try {

			//DB接続
			dao.connect();

			//課題リスト情報を取得
			List<UserTblEntity> useEntityList = dao.searchUserTask(cond);

			//CSV情報を作成する
			String csvDir = AppSettingProperty.getInstance().getCsvDir();
			String fileEnc = AppSettingProperty.getInstance().getCsvFileEncode();
			String timeStr =
					TimestampUtil.formattedTimestamp(TimestampUtil.current(), "yyyyMMddHHmmssSSS");
			fname = CSV_PREFIX_USERTASK+timeStr+".csv";
			StringBuilder header = new StringBuilder("学科,学年,学籍番号,ニックネーム,メアド");

	        FileOutputStream fos = null;
	        OutputStreamWriter osw = null;
	        BufferedWriter writer = null;

	        try{
	        	fos = new FileOutputStream(csvDir+"/"+fname);
	        	osw = new OutputStreamWriter(fos,fileEnc);
	        	writer =new BufferedWriter(osw);

	    		boolean outputHeader = false;

	    		for( UserTblEntity user : useEntityList ){
	    			StringBuilder sb = new StringBuilder();

	    			//課題ごとの結果を取得する
	    			List<ResultTblEntity> retList = getResultList(user);
	    			if( outputHeader == false){
		    			//ヘッダー情報をセット
		    			setTaskHeader(header,retList);
		    			writer.write(header.toString());
	    				outputHeader = true;
	    			}
	    			//ユーザー情報をStringBuilderにセット
	    			setUserInfoForCSV(sb,user);
	    			//結果情報をStringBuilderにセット
	    			StringBuilder sbResult = setResultData(retList);
	    			if( sbResult.length() > 0 ){
	    				sb.append(sbResult);
	    			}
	    			sb.append("\n");

	    			writer.write(sb.toString());
	    		}

	    		writer.flush();
			} catch (IOException e) {
	        	logger.warn("IOException：",e);
	        	FileUtils.delete(fname);
				throw new AsoLearningSystemErrException(e);
			}finally{
				//クローズ
	        	if(fos != null){
	        		try {
						fos.close();
					} catch (IOException e) {
						;//ignore
					}
	        	}

	        	if(osw != null){
	        		try {
	        			osw.close();
					} catch (IOException e) {
						;//ignore
					}
	        	}

	        	if(writer != null){
	        		try {
	        			writer.close();
					} catch (IOException e) {
						;//ignore
					}
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

		return fname;
	}

	private void setTaskHeader(StringBuilder header,List<ResultTblEntity> retList){

		for( ResultTblEntity ret : retList){
			if( header.length() > 0 ){
				header.append(",");
			}
			header.append(ret.getTaskTbl().getName());
		}
		header.append("\n");

	}

	/**
	 * 結果データを設定
	 * @param retList
	 * @return
	 */
	private StringBuilder setResultData(List<ResultTblEntity> retList){
		StringBuilder sbResult = new StringBuilder();

		for( ResultTblEntity ret : retList){
			if( sbResult.length() > 0 ){
				sbResult.append(",");
			}

			if( ret.getHanded() ==  1){
				//提出済み
				sbResult.append(",").append(ret.getTotalScore());
			}else{
				sbResult.append(",");
			}
		}

		return sbResult;
	}

	/**
	 * CSVのユーザー情報をセット
	 * @param sb
	 * @param user
	 * @throws AsoLearningSystemErrException
	 */
	private void setUserInfoForCSV(StringBuilder sb,UserTblEntity user) throws AsoLearningSystemErrException{

		sb.append(user.getCourseMaster().getCourseName()).append(",");
		sb.append(UserUtils.getGrade(user)).append(",");
		sb.append(user.getName()).append(",");
		sb.append( Digest.decNickName(user.getNickName(), user.getMailadress()) ).append(",");
		sb.append(user.getMailadress());
	}

	/**
	 * 結果情報を取得
	 * @param user
	 * @return
	 */
	private List<ResultTblEntity> getResultList(UserTblEntity user){


		//課題ごとの結果を取得する
		List<ResultTblEntity> retList = user.getResultTblSet();
		if( CollectionUtils.isNotEmpty(retList) ){
			retList.sort((a,b)-> a.getResultId() - b.getResultId() );
		}else{
			retList = new ArrayList();	//空のリスト作成
		}

		return retList;
	}
}
