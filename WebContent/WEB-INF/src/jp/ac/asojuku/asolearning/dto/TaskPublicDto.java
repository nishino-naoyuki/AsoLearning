/**
 *
 */
package jp.ac.asojuku.asolearning.dto;

import jp.ac.asojuku.asolearning.param.TaskPublicStateId;

/**
 * 課題公開情報
 * @author nishino
 *
 */
public class TaskPublicDto {

	/** COURSE_ID. */
	private Integer courseId;

	/** COURSE_NAME. */
	private String courseName;

	private TaskPublicStateId status;

	/** PUBLIC_DATETIME. */
	private String publicDatetime;

	/** 締切日. */
	private String endDatetime;

	/**
	 * @return courseId
	 */
	public Integer getCourseId() {
		return courseId;
	}

	/**
	 * @param courseId セットする courseId
	 */
	public void setCourseId(Integer courseId) {
		this.courseId = courseId;
	}

	/**
	 * @return courseName
	 */
	public String getCourseName() {
		return courseName;
	}

	/**
	 * @param courseName セットする courseName
	 */
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	/**
	 * @return status
	 */
	public TaskPublicStateId getStatus() {
		return status;
	}

	/**
	 * @param status セットする status
	 */
	public void setStatus(TaskPublicStateId status) {
		this.status = status;
	}

	/**
	 * @return publicDatetime
	 */
	public String getPublicDatetime() {
		return publicDatetime;
	}

	/**
	 * @param publicDatetime セットする publicDatetime
	 */
	public void setPublicDatetime(String publicDatetime) {
		this.publicDatetime = publicDatetime;
	}

	/**
	 * @return endDatetime
	 */
	public String getEndDatetime() {
		return endDatetime;
	}

	/**
	 * @param endDatetime セットする endDatetime
	 */
	public void setEndDatetime(String endDatetime) {
		this.endDatetime = endDatetime;
	}


}
