/**
 *
 */
package jp.ac.asojuku.asolearning.dto;

/**
 * ログオン情報
 * @author nishino
 *
 */
public class LogonInfoDTO {

	private String name;		//名前
	private String nickName;	//ニックネーム
	private int userId;			//ユーザーID
	private int courseId;
	private int roleId;
	private String courseName;
	private String roleName;
	private Integer grade;


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
	 * @return userId
	 */
	public int getUserId() {
		return userId;
	}
	/**
	 * @param userId セットする userId
	 */
	public void setUserId(int userId) {
		this.userId = userId;
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
	 * @return roleId
	 */
	public int getRoleId() {
		return roleId;
	}
	/**
	 * @param roleId セットする roleId
	 */
	public void setRoleId(int roleId) {
		this.roleId = roleId;
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
	 * @return roleName
	 */
	public String getRoleName() {
		return roleName;
	}
	/**
	 * @param roleName セットする roleName
	 */
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	/**
	 * @return grade
	 */
	public Integer getGrade() {
		return grade;
	}
	/**
	 * @param grade セットする grade
	 */
	public void setGrade(Integer grade) {
		this.grade = grade;
	}


}
