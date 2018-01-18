package jp.ac.asojuku.asolearning.dto;

public class HistoryDto {

	private String userName;
	private String nickName;
	private String actionDate;
	private String actionName;
	private String message;

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
}
