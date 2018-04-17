/**
 *
 */
package jp.ac.asojuku.asolearning.dto;

import java.io.Serializable;
import java.util.HashMap;

import jp.ac.asojuku.asolearning.param.InfoPublicStateId;

/**
 * 公開情報
 * @author nishino
 *
 */
public class InfoPublicDto implements Serializable{

	private InfoPublicStateId status;

	/** COURSE_ID. */
	private Integer courseId;

	/** COURSE_NAME. */
	private String courseName;

	/** PUBLIC_DATETIME. */
	private String publicDatetime;

	/** 締切日. */
	private String endDatetime;

	/** GRADE（学年、対象かどうか) */
	private HashMap<Integer,Boolean> grade = new HashMap<>();

	/**
	 * @return status
	 */
	public InfoPublicStateId getStatus() {
		return status;
	}

	/**
	 * @param status セットする status
	 */
	public void setStatus(InfoPublicStateId status) {
		this.status = status;
	}

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

	public void setGradeMap(Integer key,Boolean value){
		grade.put(key, value);
	}

	public Boolean getGradeMap(Integer key){
		return grade.get(key);
	}

	/**
	 * @return grade
	 */
	public HashMap<Integer, Boolean> getGrade() {
		return grade;
	}

	/**
	 * @param grade セットする grade
	 */
	public void setGrade(HashMap<Integer, Boolean> grade) {
		this.grade = grade;
	}


}
