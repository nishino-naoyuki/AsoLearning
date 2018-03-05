/**
 *
 */
package jp.ac.asojuku.asolearning.bo;

import java.util.List;

import jp.ac.asojuku.asolearning.condition.SearchHistoryCondition;
import jp.ac.asojuku.asolearning.dto.HistoryDto;
import jp.ac.asojuku.asolearning.exception.AsoLearningSystemErrException;

/**
 * 履歴
 * @author nishino
 *
 */
public interface HistoryBo {

	/**
	 * 履歴情報を挿入する
	 *
	 * @param userId
	 * @param actionId
	 * @param message
	 * @throws AsoLearningSystemErrException
	 */
	void insert(Integer userId,Integer actionId,String message)throws AsoLearningSystemErrException;

	/**
	 * 履歴情報を取得する
	 *
	 * @param condition
	 * @param offset  取得するデータのオフセット
	 * @param num　　　取得するデータの件数
	 * @return
	 * @throws AsoLearningSystemErrException
	 */
	List<HistoryDto> getList(SearchHistoryCondition condition,int offset,int num)throws AsoLearningSystemErrException;
}
