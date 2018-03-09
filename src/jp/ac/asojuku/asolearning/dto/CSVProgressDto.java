/**
 *
 */
package jp.ac.asojuku.asolearning.dto;

import java.io.Serializable;

/**
 * CSV処理の途中経過
 * @author nishino
 *
 */
public class CSVProgressDto implements Serializable{

	private int total;	//処理すべき件数
	private int now;	//現在処理した件数
	private String errorMsg;

	public CSVProgressDto(){
	}

	public CSVProgressDto(int total, int now) {
		super();
		this.total = total;
		this.now = now;
	}

	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int getNow() {
		return now;
	}
	public void setNow(int now) {
		this.now = now;
	}
	public void addNow(){
		now++;
	}

	/**
	 * @return errorMsg
	 */
	public String getErrorMsg() {
		return errorMsg;
	}

	/**
	 * @param errorMsg セットする errorMsg
	 */
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

}
