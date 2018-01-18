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

	private Integer taskId;
	private Float total;
	private String taskName;
	private boolean handed;
	private String handedDate;

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

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public Integer getTaskId() {
		return taskId;
	}

	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}

	public String getHandedDate() {
		return handedDate;
	}

	public void setHandedDate(String handedData) {
		this.handedDate = handedData;
	}

}
