/**
 *
 */
package jp.ac.asojuku.asolearning.dto;

import java.util.ArrayList;
import java.util.List;

/**]
 * 結果詳細データ
 * @author nishino
 *
 */
public class TaskResultDetailDto {

	private int taskId;
	private String taskName;
	private float totalScore;
	private boolean handed;
	private String handedDate;

	private List<TaskResultTestCaseDto> testcase = new ArrayList<TaskResultTestCaseDto>();
	private TaskResultMetricsDto metrics;
	/**
	 * @return taskId
	 */
	public int getTaskId() {
		return taskId;
	}
	/**
	 * @param taskId セットする taskId
	 */
	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}
	/**
	 * @return taskName
	 */
	public String getTaskName() {
		return taskName;
	}
	/**
	 * @param taskName セットする taskName
	 */
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	/**
	 * @return totalScore
	 */
	public float getTotalScore() {
		return totalScore;
	}
	/**
	 * @param totalScore セットする totalScore
	 */
	public void setTotalScore(float totalScore) {
		this.totalScore = totalScore;
	}
	public void addTaskResultTestCaseDto( TaskResultTestCaseDto dto ){
		testcase.add(dto);
	}
	/**
	 * @return testcase
	 */
	public List<TaskResultTestCaseDto> getTestcase() {
		return testcase;
	}
	/**
	 * @param testcase セットする testcase
	 */
	public void setTestcase(List<TaskResultTestCaseDto> testcase) {
		this.testcase = testcase;
	}
	/**
	 * @return metrics
	 */
	public TaskResultMetricsDto getMetrics() {
		return metrics;
	}
	/**
	 * @param metrics セットする metrics
	 */
	public void setMetrics(TaskResultMetricsDto metrics) {
		this.metrics = metrics;
	}
	public boolean isHanded() {
		return handed;
	}
	public void setHanded(boolean handed) {
		this.handed = handed;
	}
	public String getHandedDate() {
		return handedDate;
	}
	public void setHandedDate(String handedDate) {
		this.handedDate = handedDate;
	}


}
