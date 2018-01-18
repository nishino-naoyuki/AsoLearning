/**
 *
 */
package jp.ac.asojuku.asolearning.util;

import java.util.Calendar;

import jp.ac.asojuku.asolearning.entity.RoleMasterEntity;
import jp.ac.asojuku.asolearning.entity.UserTblEntity;
import jp.ac.asojuku.asolearning.param.RoleId;

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

		RoleMasterEntity role =userEntity.getRoleMaster() ;

		//学生でない場合は学年はnull
		if( role != null && !RoleId.STUDENT.equals(role.getRoleId())){
			return null;
		}
		if( userEntity.getAdmissionYear() == null ){
			return null;
		}
		//現在時刻取得
		Calendar calendar = Calendar.getInstance();

		int adYear = userEntity.getAdmissionYear();
		int repCount = userEntity.getRepeatYearCount();

		int nendo = calendar.get(Calendar.YEAR);

		//Calendar.MONTHは1月は０となる
		if( calendar.get(Calendar.MONTH) < 3 ){
			//4月より前（3月まで）は前年度
			nendo--;
		}

		return (nendo-adYear-repCount+1);
	}
}
