/**
 *
 */
package jp.ac.asojuku.asolearning.bo;

import jp.ac.asojuku.asolearning.dto.LogonInfoDTO;
import jp.ac.asojuku.asolearning.exception.AccountLockedException;
import jp.ac.asojuku.asolearning.exception.AsoLearningSystemErrException;
import jp.ac.asojuku.asolearning.exception.LoginFailureException;

/**
 * ログオンビジネスロジック
 * @author nishino
 *
 */
public interface LoginBo {

	LogonInfoDTO login(String mailadress,String password) throws LoginFailureException, AsoLearningSystemErrException, AccountLockedException;
}
