/**
 *
 */
package jp.ac.asojuku.asolearning.condition;

import java.util.Date;

/**
 * お知らせ検索条件
 * @author nishino
 *
 */
public class SearchInfomationCondition {
	//・作成者のメールアドレス（部分一致）
	private String mailAddress;
	//・表示対象学科
	private Integer courseId;
	//・内容（部分一致）
	private String message;
	//・表示期間
	private Date dispFrom;
	private Date dsipTo;

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
	 * @return dispFrom
	 */
	public Date getDispFrom() {
		return dispFrom;
	}
	/**
	 * @param dispFrom セットする dispFrom
	 */
	public void setDispFrom(Date dispFrom) {
		this.dispFrom = dispFrom;
	}
	/**
	 * @return dsipTo
	 */
	public Date getDsipTo() {
		return dsipTo;
	}
	/**
	 * @param dsipTo セットする dsipTo
	 */
	public void setDsipTo(Date dsipTo) {
		this.dsipTo = dsipTo;
	}
}
