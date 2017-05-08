/**
 *
 */
package jp.ac.asojuku.asolearning.bo;

import java.util.List;

import jp.ac.asojuku.asolearning.condition.SearchInfomationCondition;
import jp.ac.asojuku.asolearning.dto.InfomationListDto;
import jp.ac.asojuku.asolearning.dto.InfomationSearchResultDto;
import jp.ac.asojuku.asolearning.dto.LogonInfoDTO;
import jp.ac.asojuku.asolearning.exception.AsoLearningSystemErrException;

/**
 * お知らせ情報BO
 * @author nishino
 *
 */
public interface InfomationBo {

	/**
	 * 検索処理
	 * @param cond
	 * @return
	 * @throws AsoLearningSystemErrException
	 */
	public List<InfomationSearchResultDto> search(SearchInfomationCondition cond) throws AsoLearningSystemErrException;

	InfomationListDto get(LogonInfoDTO logon) throws AsoLearningSystemErrException;
}
