/**
 *
 */
package jp.ac.asojuku.asolearning.bo;

import java.util.List;

import jp.ac.asojuku.asolearning.condition.SearchUserCondition;
import jp.ac.asojuku.asolearning.dto.LogonInfoDTO;
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
	 * コメントを登録（更新）する
	 * ※コメントを登録できるのは先生又は管理者だけ
	 *
	 * @param logonInfo
	 * @param resultId
	 * @param comment
	 * @throws AsoLearningSystemErrException
	 */
	void setComment(LogonInfoDTO logonInfo,Integer resultId,String comment) throws AsoLearningSystemErrException;
	/**
	 * 指定された結果情報のソースコードを表示する
	 * 閲覧者が管理者の場合は、誰のソースコードでも閲覧OKだが、ユーザーの場合は自分の市か見れない
	 * @param logonInfo
	 * @param resultId
	 * @param fileName
	 * @return
	 * @throws AsoLearningSystemErrException
	 */
	String getSrcCode(LogonInfoDTO logonInfo,Integer resultId,String fileName) throws AsoLearningSystemErrException;

	String createTaskUserList(SearchUserCondition userCond) throws AsoLearningSystemErrException;
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
	List<RankingDto> getRanking(Integer courseId, Integer taskId, Integer taskGrpId, Integer grade)
			throws AsoLearningSystemErrException;

	/**
	 * ランキングの取得
	 * @param userId
	 * @param taskId
	 * @return
	 * @throws AsoLearningSystemErrException
	 */
	Integer getRankingForUser(Integer userId,Integer taskId) throws AsoLearningSystemErrException;
}
