/**
 *
 */
package jp.ac.asojuku.asolearning.dto;

/**
 * お知らせ検索結果
 * @author nishino
 *
 */
public class InfomationSearchResultDto {

	private Integer infomationId;
	private String maileAddress;
	private String nickName;
	private String publickCourse;
	private String title;
	private String termFrom;
	private String termTo;
	/**
	 * @return maileAddress
	 */
	public String getMaileAddress() {
		return maileAddress;
	}
	/**
	 * @param maileAddress セットする maileAddress
	 */
	public void setMaileAddress(String maileAddress) {
		this.maileAddress = maileAddress;
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
	 * @return publickCourse
	 */
	public String getPublickCourse() {
		return publickCourse;
	}
	/**
	 * @param publickCourse セットする publickCourse
	 */
	public void setPublickCourse(String publickCourse) {
		this.publickCourse = publickCourse;
	}
	/**
	 * @return title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title セットする title
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return termFrom
	 */
	public String getTermFrom() {
		return termFrom;
	}
	/**
	 * @param termFrom セットする termFrom
	 */
	public void setTermFrom(String termFrom) {
		this.termFrom = termFrom;
	}
	/**
	 * @return termTo
	 */
	public String getTermTo() {
		return termTo;
	}
	/**
	 * @param termTo セットする termTo
	 */
	public void setTermTo(String termTo) {
		this.termTo = termTo;
	}
	/**
	 * @return infomationId
	 */
	public Integer getInfomationId() {
		return infomationId;
	}
	/**
	 * @param infomationId セットする infomationId
	 */
	public void setInfomationId(Integer infomationId) {
		this.infomationId = infomationId;
	}


}
