/**
 *
 */
package jp.ac.asojuku.asolearning.dto;

import java.util.List;

/**
 * お知らせ情報
 * @author nishino
 *
 */
public class InfomationDto {

	private int infomationId;
	private String infomationTitle;
	private String contents;
	private List<InfoPublicDto> infoPublicList;
	/**
	 * @return infomationId
	 */
	public int getInfomationId() {
		return infomationId;
	}
	/**
	 * @param infomationId セットする infomationId
	 */
	public void setInfomationId(int infomationId) {
		this.infomationId = infomationId;
	}
	/**
	 * @return infomationTitle
	 */
	public String getInfomationTitle() {
		return infomationTitle;
	}
	/**
	 * @param infomationTitle セットする infomationTitle
	 */
	public void setInfomationTitle(String infomationTitle) {
		this.infomationTitle = infomationTitle;
	}
	/**
	 * @return contents
	 */
	public String getContents() {
		return contents;
	}
	/**
	 * @param contents セットする contents
	 */
	public void setContents(String contents) {
		this.contents = contents;
	}
	/**
	 * @return infoPublicList
	 */
	public List<InfoPublicDto> getInfoPublicList() {
		return infoPublicList;
	}
	/**
	 * @param infoPublicList セットする infoPublicList
	 */
	public void setInfoPublicList(List<InfoPublicDto> infoPublicList) {
		this.infoPublicList = infoPublicList;
	}


}
