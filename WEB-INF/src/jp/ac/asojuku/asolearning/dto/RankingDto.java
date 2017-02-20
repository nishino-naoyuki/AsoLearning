/**
 *
 */
package jp.ac.asojuku.asolearning.dto;

/**
 * ランキング情報
 *
 * @author nishino
 *
 */
public class RankingDto {

	private String name;
	private String courseName;
	private int courseId;
	private String nickName;
	private Float score;
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


}
