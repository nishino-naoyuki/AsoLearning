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

	private int resultId;
	private int taskId;
	private String taskName;
	private float totalScore;
	private boolean handed;
	private String handedDate;
	private String answerString;
	private List<String> srcFileList;

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
	/**
	 * @return answerString
	 */
	public String getAnswerString() {
		return answerString;
	}
	/**
	 * @param answerString セットする answerString
	 */
	public void setAnswerString(String answerString) {
		this.answerString = answerString;
	}

	/**
	 * @return srcFileList
	 */
	public List<String> getSrcFileList() {
		return srcFileList;
	}
	/**
	 * @param srcFileList セットする srcFileList
	 */
	public void setSrcFileList(List<String> srcFileList) {
		this.srcFileList = srcFileList;
	}
	/**
	 * @return resultId
	 */
	public int getResultId() {
		return resultId;
	}
	/**
	 * @param resultId セットする resultId
	 */
	public void setResultId(int resultId) {
		this.resultId = resultId;
	}

}
