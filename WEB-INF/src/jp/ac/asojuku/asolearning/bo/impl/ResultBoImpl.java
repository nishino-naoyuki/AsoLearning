/**
 *
 */
package jp.ac.asojuku.asolearning.bo.impl;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.zip.DataFormatException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jp.ac.asojuku.asolearning.bo.ResultBo;
import jp.ac.asojuku.asolearning.condition.SearchUserCondition;
import jp.ac.asojuku.asolearning.config.AppSettingProperty;
import jp.ac.asojuku.asolearning.dao.ResultDao;
import jp.ac.asojuku.asolearning.dto.RankingDto;
import jp.ac.asojuku.asolearning.dto.TaskResultDetailDto;
import jp.ac.asojuku.asolearning.dto.TaskResultMetricsDto;
import jp.ac.asojuku.asolearning.dto.TaskResultTestCaseDto;
import jp.ac.asojuku.asolearning.entity.ResultMetricsTblEntity;
import jp.ac.asojuku.asolearning.entity.ResultTblEntity;
import jp.ac.asojuku.asolearning.entity.ResultTestcaseTblEntity;
import jp.ac.asojuku.asolearning.entity.TaskTblEntity;
import jp.ac.asojuku.asolearning.entity.UserTblEntity;
import jp.ac.asojuku.asolearning.exception.AsoLearningSystemErrException;
import jp.ac.asojuku.asolearning.exception.DBConnectException;
import jp.ac.asojuku.asolearning.util.CompressUtils;
import jp.ac.asojuku.asolearning.util.DateUtil;
import jp.ac.asojuku.asolearning.util.Digest;
import jp.ac.asojuku.asolearning.util.FileUtils;
import jp.ac.asojuku.asolearning.util.TimestampUtil;
import jp.ac.asojuku.asolearning.util.UserUtils;

/**
 * @author nishino
 *
 */
public class ResultBoImpl implements ResultBo {
	private final String CSV_PREFIX = "ranking_";

	Logger logger = LoggerFactory.getLogger(ResultBoImpl.class);

	/* (非 Javadoc)
	 * @see jp.ac.asojuku.asolearning.bo.ResultBo#getResultDetail(int, int)
	 */
	@Override
	public TaskResultDetailDto getResultDetail(int taskId, int userId) throws AsoLearningSystemErrException {


		TaskResultDetailDto resultDetail = new TaskResultDetailDto();

		ResultDao dao = new ResultDao();

		try {

			//DB接続
			dao.connect();

			//課題リスト情報を取得
			ResultTblEntity entity = dao.getResult(taskId, userId);

			resultDetail = getTaskResultDetail(entity);

		} catch (DBConnectException e) {
			//ログ出力
			logger.warn("DB接続エラー：",e);
			throw new AsoLearningSystemErrException(e);

		} catch (SQLException e) {
			//ログ出力
			logger.warn("SQLエラー：",e);
			throw new AsoLearningSystemErrException(e);
		} catch (DataFormatException e) {
			logger.warn("DataFormatExceptionエラー：",e);
			throw new AsoLearningSystemErrException(e);
		} catch (IOException e) {
			logger.warn("IOException：",e);
			throw new AsoLearningSystemErrException(e);
		} finally{

			dao.close();
		}

		return resultDetail;
	}

	/**
	 * entity -> DTO変換
	 * @param entity
	 * @return
	 * @throws IOException
	 * @throws DataFormatException
	 */
	private TaskResultDetailDto getTaskResultDetail(ResultTblEntity entity ) throws DataFormatException, IOException{
		TaskResultDetailDto dto = new TaskResultDetailDto();

		if( entity == null ){
			return null;
		}

		TaskTblEntity taskEntity = entity.getTaskTbl();
		if( taskEntity == null ){
			return null;
		}

		//基本情報取得
		dto.setTaskId( taskEntity.getTaskId() );
		dto.setTaskName( taskEntity.getName() );
		dto.setTotalScore(entity.getTotalScore());
		dto.setHanded((entity.getHanded() == 1 ? true:false));
		dto.setHandedDate(DateUtil.formattedDate(entity.getHandedTimestamp(), "yyyy/MM/dd HH:mm:ss"));
		dto.setAnswerString(CompressUtils.decode(entity.getAnswer()));

		//メトリクスデータ取得
		Set<ResultMetricsTblEntity> retMetricsEntitySet = entity.getResultMetricsTblSet();
		for( ResultMetricsTblEntity rmt : retMetricsEntitySet ){
			TaskResultMetricsDto retMetrics = new TaskResultMetricsDto();

			retMetrics.setMaxMvg(rmt.getMaxMvg());
			retMetrics.setAvrMvg(rmt.getAvrMvg());
			retMetrics.setMaxLoc(rmt.getMaxLoc());
			retMetrics.setAvrLoc(rmt.getAvrLoc());
			retMetrics.setMaxMvgScore(rmt.getAvrMvgScore());
			retMetrics.setMaxLocScore(rmt.getMaxLocScore());
			retMetrics.setAvrMvgScore(rmt.getAvrMvgScore());
			retMetrics.setAvrLocScore(rmt.getAvrLocScore());

			dto.setMetrics(retMetrics);
		}

		//テストケース結果取得
		Set<ResultTestcaseTblEntity> retTestcaseSet = entity.getResultTestcaseTblSet();
		for( ResultTestcaseTblEntity rtt : retTestcaseSet ){
			TaskResultTestCaseDto retTestCase = new TaskResultTestCaseDto();

			retTestCase.setTestcaseId(rtt.getTestcaseId());
			retTestCase.setScore(rtt.getScore());
			retTestCase.setMessage(rtt.getMessage());

			dto.addTaskResultTestCaseDto(retTestCase);
		}

		return dto;

	}

	/* (非 Javadoc)
	 * @see jp.ac.asojuku.asolearning.bo.ResultBo#getRanking(java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public List<RankingDto> getRanking(Integer courseId, Integer taskId) throws AsoLearningSystemErrException {

		List<RankingDto> list = new ArrayList<RankingDto>();
		ResultDao dao = new ResultDao();

		try {

			AppSettingProperty config = AppSettingProperty.getInstance();

			//DB接続
			dao.connect();

			//ランキングを取得
			List<ResultTblEntity> retEntityList =
					dao.getRanking(courseId,taskId,config.getRankingEasy(),config.getRankingNormal(),config.getRankingDiffical());

			int rank = 0;
			float wkScore = Float.MAX_VALUE;
			for( ResultTblEntity retEntity : retEntityList){
				//ランクはここでセット
				if( wkScore > retEntity.getTotalScore()){
					wkScore = retEntity.getTotalScore();
					rank++;
				}
				RankingDto ranking = createRankingDto(retEntity,rank);
				list.add(ranking);
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
	 * ランキング情報をセット
	 *
	 * @param retEntity
	 * @return
	 * @throws AsoLearningSystemErrException
	 */
	private RankingDto createRankingDto(ResultTblEntity retEntity,int rank) throws AsoLearningSystemErrException{

		RankingDto rankingDto = new RankingDto();
		UserTblEntity userEntity = retEntity.getUserTbl();

		if( userEntity == null ){
			throw new AsoLearningSystemErrException("ユーザー情報が存在しません");
		}

		rankingDto.setUserId(userEntity.getUserId());
		rankingDto.setRank(rank);
		rankingDto.setName(userEntity.getName());
		rankingDto.setCourseName(userEntity.getCourseMaster().getCourseName());
		rankingDto.setNickName(Digest.decNickName( userEntity.getNickName(),userEntity.getMailadress()));
		rankingDto.setScore(retEntity.getTotalScore());
		rankingDto.setCourseId(userEntity.getCourseMaster().getCourseId());
		rankingDto.setGrade(UserUtils.getGrade(userEntity));

		return rankingDto;
	}

	@Override
	public Integer getRankingForUser(Integer userId, Integer taskId) throws AsoLearningSystemErrException {

		Integer rank = null;
		ResultDao dao = new ResultDao();

		try {

			//DB接続
			dao.connect();

			rank = dao.getRankingForUser(userId, taskId);


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

		return rank;
	}

	@Override
	public List<TaskResultDetailDto> getResultDetailById(int taskId) throws AsoLearningSystemErrException {

		List<TaskResultDetailDto> list = new ArrayList<TaskResultDetailDto>();

		ResultDao dao = new ResultDao();

		try {

			//DB接続
			dao.connect();

			//課題リスト情報を取得
			List<ResultTblEntity> entityList = dao.getResultByTaskId(taskId);

			for( ResultTblEntity entity:entityList){
				list.add( getTaskResultDetail(entity) );
			}

		} catch (DBConnectException e) {
			//ログ出力
			logger.warn("DB接続エラー：",e);
			throw new AsoLearningSystemErrException(e);

		} catch (SQLException e) {
			//ログ出力
			logger.warn("SQLエラー：",e);
			throw new AsoLearningSystemErrException(e);
		} catch (DataFormatException e) {
			logger.warn("データエラー：",e);
			throw new AsoLearningSystemErrException(e);
		} catch (IOException e) {
			logger.warn("IOエラー：",e);
			throw new AsoLearningSystemErrException(e);
		} finally{

			dao.close();
		}

		return list;
	}

	@Override
	public String createRankingCSV(List<RankingDto> rankingList) throws AsoLearningSystemErrException {

		String csvDir = AppSettingProperty.getInstance().getCsvDir();
		String fileEnc = AppSettingProperty.getInstance().getCsvFileEncode();
		String fname = "";
		StringBuilder sb = new StringBuilder();
		String head = "RANK,学科,学年,学籍番号,ニックネーム,点数";

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

    		for( RankingDto rank : rankingList ){
    			StringBuilder rankSb = new StringBuilder();
    			rankSb.append(rank.getRank()).append(",");
    			rankSb.append(rank.getCourseName()).append(",");
    			rankSb.append(rank.getGrade()).append(",");
    			rankSb.append(rank.getName()).append(",");
    			rankSb.append(rank.getNickName()).append(",");
    			rankSb.append(rank.getScore()).append("\n");
    			writer.write(rankSb.toString());
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
	public void delete(List<Integer> taskList, Integer userId) throws AsoLearningSystemErrException {


		ResultDao dao = new ResultDao();

		try {

			//DB接続
			dao.connect();

			//トランザクション
			dao.beginTranzaction();

			//削除
			for(Integer taskId:taskList){
				dao.delete(taskId, userId);
			}

			dao.commit();

		} catch (Exception e) {
			//ロールバック
			dao.rollback();
			//ログ出力
			logger.warn("DB接続エラー：",e);
			throw new AsoLearningSystemErrException(e);

		} finally{

			dao.close();
		}
	}

	@Override
	public String createTaskUserList(SearchUserCondition userCond) throws AsoLearningSystemErrException {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}
}
