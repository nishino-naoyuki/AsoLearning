/**
 *
 */
package jp.ac.asojuku.asolearning.util;

import java.util.Calendar;

import jp.ac.asojuku.asolearning.entity.UserTblEntity;

/**
 * @author nishino
 *
 */
public class UserUtils {

	/**
	 * 学年の取得
	 * @param userEntity
	 * @return
	 */
	public static Integer getGrade(UserTblEntity userEntity){

		if( userEntity.getAdmissionYear() == null ){
			return null;
		}
		//現在時刻取得
		Calendar calendar = Calendar.getInstance();


		int adYear = userEntity.getAdmissionYear();
		int repCount = userEntity.getRepeatYearCount();

		int nendo = calendar.get(Calendar.YEAR);
		if( calendar.get(Calendar.MONTH) < 4 ){
			//4月より前（3月まで）は前年度
			nendo--;
		}

		return (nendo-adYear-repCount+1);
	}
}
