/**
 *
 */
package jp.ac.asojuku.asolearning.dto;

/**
 * CSV処理の途中経過
 * @author nishino
 *
 */
public class CSVProgressDto {

	private int total;	//処理すべき件数
	private int now;	//現在処理した件数


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


}
