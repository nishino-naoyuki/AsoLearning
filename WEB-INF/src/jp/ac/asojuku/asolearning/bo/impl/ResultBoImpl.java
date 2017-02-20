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

		dto.setTaskId( taskEntity.getTaskId() );
		dto.setTaskName( taskEntity.getName() );
		dto.setTotalScore(entity.getTotalScore());

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

	@Override
	public List<RankingDto> getRanking(Integer courseId, Integer taskId) throws AsoLearningSystemErrException {

		List<RankingDto> list = new ArrayList<RankingDto>();
		ResultDao dao = new ResultDao();

		try {

			//DB接続
			dao.connect();

			//ランキングを取得
			List<ResultTblEntity> retEntityList = dao.getRanking(courseId,taskId);

			for( ResultTblEntity retEntity : retEntityList){
				RankingDto ranking = createRankingDto(retEntity);
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
	private RankingDto createRankingDto(ResultTblEntity retEntity) throws AsoLearningSystemErrException{

		RankingDto rankingDto = new RankingDto();
		UserTblEntity userEntity = retEntity.getUserTbl();

		if( userEntity == null ){
			throw new AsoLearningSystemErrException("ユーザー情報が存在しません");
		}

		rankingDto.setName(userEntity.getName());
		rankingDto.setCourseName(userEntity.getCourseMaster().getCourseName());
		rankingDto.setNickName(userEntity.getNickName());
		rankingDto.setScore(retEntity.getTotalScore());
		rankingDto.setCourseId(userEntity.getCourseMaster().getCourseId());

		return rankingDto;
	}
}
