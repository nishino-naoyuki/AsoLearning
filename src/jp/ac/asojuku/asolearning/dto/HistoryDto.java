package jp.ac.asojuku.asolearning.dto;

import java.io.Serializable;

public class HistoryDto implements Serializable{

	private String userName;
	private String nickName;
	private String actionDate;
	private String actionName;
	private String message;
	private String courseName;
	private String mailAddress;

	/**
	 * @return userName
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * @param userName セットする userName
	 */
	public void setUserName(String userName) {
		this.userName = userName;
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
	 * @return actionDate
	 */
	public String getActionDate() {
		return actionDate;
	}
	/**
	 * @param actionDate セットする actionDate
	 */
	public void setActionDate(String actionDate) {
		this.actionDate = actionDate;
	}
	/**
	 * @return actionName
	 */
	public String getActionName() {
		return actionName;
	}
	/**
	 * @param actionName セットする actionName
	 */
	public void setActionName(String actionName) {
		this.actionName = actionName;
	}
	/**
	 * @return message
	 */
	public String getMessage() {
		return message;
	}
	/**
	 * @param message セットする message
	 */
	public void setMessage(String message) {
		this.message = message;
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
	 * @return mailAddress
	 */
	public String getMailAddress() {
		return mailAddress;
	}
	/**
	 * @param mailAddress セットする mailAddress
	 */
	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}
}
