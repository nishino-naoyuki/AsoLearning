/**
 *
 */
package jp.ac.asojuku.asolearning.bo.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jp.ac.asojuku.asolearning.bo.CourseBo;
import jp.ac.asojuku.asolearning.dao.CourseDao;
import jp.ac.asojuku.asolearning.dto.CourseDto;
import jp.ac.asojuku.asolearning.entity.CourseMasterEntity;
import jp.ac.asojuku.asolearning.exception.AsoLearningSystemErrException;
import jp.ac.asojuku.asolearning.exception.DBConnectException;

/**
 * コース一覧取得
 * @author nishino
 *
 */
public class CourseBoImpl implements CourseBo {
	Logger logger = LoggerFactory.getLogger(CourseBoImpl.class);

	/* (非 Javadoc)
	 * @see jp.ac.asojuku.asolearning.bo.CourseBo#getCourseAllList()
	 */
	@Override
	public List<CourseDto> getCourseAllList() throws AsoLearningSystemErrException {
		List<CourseDto> list = new ArrayList<CourseDto>();


		CourseDao dao = new CourseDao();

		try {

			//DB接続
			dao.connect();

			//学科一覧を取得する
			List<CourseMasterEntity> entityList = dao.getCourseList();

			for(CourseMasterEntity entity : entityList){
				list.add( new CourseDto(entity));
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

}
