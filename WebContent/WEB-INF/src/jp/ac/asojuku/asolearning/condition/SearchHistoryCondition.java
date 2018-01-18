/**
 *
 */
package jp.ac.asojuku.asolearning.condition;

/**
 * @author nishino
 *
 */
public class SearchHistoryCondition {

	private Integer userId;
	private Integer actionId;
	private String fromDate;
	private String toDate;
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
	public Integer getActionId() {
		return actionId;
	}
	/**
	 * @param actionId セットする actionId
	 */
	public void setActionId(Integer actionId) {
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


}
