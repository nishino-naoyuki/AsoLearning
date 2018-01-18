/**
 *
 */
package jp.ac.asojuku.asolearning.dto;

/**
 * @author nishino
 *
 */
public class SrcDTO {
	private Integer resultId;
	private String fileName;
	private String src;

	/**
	 * @return resultId
	 */
	public Integer getResultId() {
		return resultId;
	}
	/**
	 * @param resultId セットする resultId
	 */
	public void setResultId(Integer resultId) {
		this.resultId = resultId;
	}
	/**
	 * @return fileName
	 */
	public String getFileName() {
		return fileName;
	}
	/**
	 * @param fileName セットする fileName
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	/**
	 * @return src
	 */
	public String getSrc() {
		return src;
	}
	/**
	 * @param src セットする src
	 */
	public void setSrc(String src) {
		this.src = src;
	}


}
