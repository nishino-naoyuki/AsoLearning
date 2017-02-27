/**
 *
 */
package jp.ac.asojuku.asolearning.bo.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jp.ac.asojuku.asolearning.bo.ResultBo;
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
import jp.ac.asojuku.asolearning.util.DateUtil;
import jp.ac.asojuku.asolearning.util.Digest;
import jp.ac.asojuku.asolearning.util.UserUtils;

/**
 * @author nishino
 *
 */
public class ResultBoImpl implements ResultBo {

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
		} finally{

			dao.close();
		}

		return resultDetail;
	}

	/**
	 * entity -> DTO変換
	 * @param entity
	 * @return
	 */
	private TaskResultDetailDto getTaskResultDetail(ResultTblEntity entity ){
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

			//DB接続
			dao.connect();

			//ランキングを取得
			List<ResultTblEntity> retEntityList = dao.getRanking(courseId,taskId);

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
		} finally{

			dao.close();
		}

		return list;
	}
}
