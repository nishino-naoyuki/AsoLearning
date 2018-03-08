/**
 *
 */
package jp.ac.asojuku.asolearning.condition;

import jp.ac.asojuku.asolearning.param.ActionId;
import jp.ac.asojuku.asolearning.param.RoleId;

/**
 * @author nishino
 *
 */
public class SearchHistoryCondition {

	private Integer userId;
	private Integer courseId;
	private RoleId roleId;
	private ActionId actionId;
	private String fromDate;
	private String toDate;
	private String mail;
	/**
	 * @return userId
	 */
	public Integer getUserId() {
		return userId;
	}
	/**
	 * @param userId セットする userId
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	/**
	 * @return actionId
	 */
	public ActionId getActionId() {
		return actionId;
	}
	/**
	 * @param actionId セットする actionId
	 */
	public void setActionId(ActionId actionId) {
		this.actionId = actionId;
	}
	/**
	 * @return fromDate
	 */
	public String getFromDate() {
		return fromDate;
	}
	/**
	 * @param fromDate セットする fromDate
	 */
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	/**
	 * @return toDate
	 */
	public String getToDate() {
		return toDate;
	}
	/**
	 * @param toDate セットする toDate
	 */
	public void setToDate(String toDate) {
		this.toDate = toDate;
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
	 * @return roleId
	 */
	public RoleId getRoleId() {
		return roleId;
	}
	/**
	 * @param roleId セットする roleId
	 */
	public void setRoleId(RoleId roleId) {
		this.roleId = roleId;
	}
	/**
	 * @return mail
	 */
	public String getMail() {
		return mail;
	}
	/**
	 * @param mail セットする mail
	 */
	public void setMail(String mail) {
		this.mail = mail;
	}


}
