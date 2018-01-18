/**
 *
 */
package jp.ac.asojuku.asolearning.bo;

import java.util.List;

import jp.ac.asojuku.asolearning.dto.CourseDto;
import jp.ac.asojuku.asolearning.exception.AsoLearningSystemErrException;

/**
 * 学科BO
 * @author nishino
 *
 */
public interface CourseBo {

	/**
	 * 学科の一覧を取得する
	 * @return
	 * @throws AsoLearningSystemErrException
	 */
	List<CourseDto> getCourseAllList() throws AsoLearningSystemErrException;
}
