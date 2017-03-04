/**
 *
 */
package jp.ac.asojuku.asolearning.bo;

import java.util.List;

import jp.ac.asojuku.asolearning.dto.RankingDto;
import jp.ac.asojuku.asolearning.dto.TaskResultDetailDto;
import jp.ac.asojuku.asolearning.exception.AsoLearningSystemErrException;

/**
 * 課題の結果BO
 *
 * @author nishino
 *
 */
public interface ResultBo {

	/**
	 * 削除する
	 *
	 * @param taskList
	 * @param userId
	 * @throws AsoLearningSystemErrException
	 */
	void delete(List<Integer> taskList,Integer userId) throws AsoLearningSystemErrException;
	/**
	 * ランキングCSVの作成
	 * @param rankingList
	 * @return　作成したCSVファイルの名前
	 * @throws AsoLearningSystemErrException
	 */
	String createRankingCSV(List<RankingDto> rankingList) throws AsoLearningSystemErrException;

	/**
	 * 結果の詳細情報の取得
	 *
	 * @param taskId
	 * @param userId
	 * @return
	 * @throws AsoLearningSystemErrException
	 */
	List<TaskResultDetailDto> getResultDetailById(int taskId) throws AsoLearningSystemErrException;

	/**
	 * 結果の詳細情報の取得
	 *
	 * @param taskId
	 * @param userId
	 * @return
	 * @throws AsoLearningSystemErrException
	 */
	TaskResultDetailDto getResultDetail(int taskId,int userId) throws AsoLearningSystemErrException;

	/**
	 * ランキング情報を取得する
	 *
	 * @param courseId 指定しない場合はNULL
	 * @param taskId 指定しない場合はNULL
	 * @return
	 * @throws AsoLearningSystemErrException
	 */
	List<RankingDto> getRanking(Integer courseId,Integer taskId) throws AsoLearningSystemErrException;

	/**
	 * ランキングの取得
	 * @param userId
	 * @param taskId
	 * @return
	 * @throws AsoLearningSystemErrException
	 */
	Integer getRankingForUser(Integer userId,Integer taskId) throws AsoLearningSystemErrException;
}
