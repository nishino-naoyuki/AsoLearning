/**
 *
 */
package jp.ac.asojuku.asolearning.dto;

import java.io.Serializable;

/**
 * ランキング情報
 *
 * @author nishino
 *
 */
public class RankingDto implements Serializable{

	private int userId;
	private int rank;
	private String name;
	private String courseName;
	private int courseId;
	private String nickName;
	private Float score;
	private int grade;



	/**
	 * @return rank
	 */
	public int getRank() {
		return rank;
	}
	/**
	 * @param rank セットする rank
	 */
	public void setRank(int rank) {
		this.rank = rank;
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
	 * @return nickName
	 */
	public String getNickName() {
		return nickName;
	}
	/**
	 * @param nickName セットする nickName
	 */
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	/**
	 * @return score
	 */
	public Float getScore() {
		return score;
	}
	/**
	 * @param score セットする score
	 */
	public void setScore(Float score) {
		this.score = score;
	}
	/**
	 * @return courseId
	 */
	public int getCourseId() {
		return courseId;
	}
	/**
	 * @param courseId セットする courseId
	 */
	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}
	/**
	 * @return grade
	 */
	public int getGrade() {
		return grade;
	}
	/**
	 * @param grade セットする grade
	 */
	public void setGrade(int grade) {
		this.grade = grade;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}


}
