/**
 *
 */
package jp.ac.asojuku.asolearning.permit;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jp.ac.asojuku.asolearning.config.AppSettingProperty;
import jp.ac.asojuku.asolearning.exception.AsoLearningSystemErrException;

/**
 * 権限のチェックを行う
 * @author nishino
 *
 */
public class PermissionChecker {

	/**
	 * 画面IDと役割IDから権限をチェックする
	 * 権限情報は設定ファイルから取得する
	 * @param dispId
	 * @param roleId
	 * @return
	 */
	public static boolean check(String dispId,Integer roleId){
		Logger logger = LoggerFactory.getLogger(PermissionChecker.class);

		boolean result = false;
		String permit;
		try {
			String roleStr = roleId.toString();
			permit = AppSettingProperty.getInstance().getDisplayPermit(dispId);

			if( StringUtils.isNotEmpty(permit) ){
				String[] permits = permit.split(",");

				for( String permitElement : permits){
					if( permitElement.equals(roleStr)){
						result = true;
						break;
					}
				}
			}else{
				//設定なしは全権限アクセスOK
				result = true;
			}

		} catch (AsoLearningSystemErrException e) {
			logger.warn("権限の設定が読めません");
			result = false;
		}

		return result;
	}
}
