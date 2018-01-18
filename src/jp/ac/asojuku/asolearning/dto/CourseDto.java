/**
 *
 */
package jp.ac.asojuku.asolearning.dto;

import jp.ac.asojuku.asolearning.entity.CourseMasterEntity;

/**
 * 学科DTO
 * @author nishino
 *
 */
public class CourseDto {

	private int id;
	private String name;

	public CourseDto(CourseMasterEntity entity){

		this.id = entity.getCourseId();
		this.name = entity.getCourseName();
	}

	/**
	 * @return id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id セットする id
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name セットする name
	 */
	public void setName(String name) {
		this.name = name;
	}


}
