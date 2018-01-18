/**
 *
 */
package jp.ac.asojuku.asolearning.condition;

/**
 * ユーザー検索条件
 * @author nishino
 *
 */
public class SearchUserCondition {

	private String name;
	private String mailaddress;
	private Integer roleId;
	private Integer courseId;
	private Integer taskId;
	private Integer handed;
	private Integer grade;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMailaddress() {
		return mailaddress;
	}
	public void setMailaddress(String mailaddress) {
		this.mailaddress = mailaddress;
	}
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	public Integer getCourseId() {
		return courseId;
	}
	public void setCourseId(Integer courseId) {
		this.courseId = courseId;
	}
	public Integer getTaskId() {
		return taskId;
	}
	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}
	public Integer getHanded() {
		return handed;
	}
	public void setHanded(Integer handed) {
		this.handed = handed;
	}
	public Integer getGrade() {
		return grade;
	}
	public void setGrade(Integer grade) {
		this.grade = grade;
	}

}
