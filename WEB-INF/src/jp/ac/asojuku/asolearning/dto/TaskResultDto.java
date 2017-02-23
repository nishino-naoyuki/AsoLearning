/**
 *
 */
package jp.ac.asojuku.asolearning.dto;

/**
 * 課題結果DTO
 * @author nishino
 *
 */
public class TaskResultDto {

	private Float total;
	private boolean handed;

	public boolean isHanded() {
		return handed;
	}

	public void setHanded(boolean handed) {
		this.handed = handed;
	}

	/**
	 * @return total
	 */
	public Float getTotal() {
		return total;
	}

	/**
	 * @param total セットする total
	 */
	public void setTotal(Float total) {
		this.total = total;
	}

}
