/**
 *
 */
package jp.ac.asojuku.asolearning.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * 課題情報
 * @author nishino
 *
 */
public class TaskDto {

	private int taskId;
	private String taskName;		//課題名
	private String TerminationDate;	//締め切り
	private boolean RequiredFlg;	//公開設定
	private String question;		//問題文
	private TaskResultDto result;
	private Integer rank;
	private List<TaskPublicDto> taskPublicList = new ArrayList<>();
	private List<TaskTestCaseDto> taskTestCaseDtoList = new ArrayList<>();

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
	 * @return terminationDate
	 */
	public String getTerminationDate() {
		return TerminationDate;
	}
	/**
	 * @param terminationDate セットする terminationDate
	 */
	public void setTerminationDate(String terminationDate) {
		TerminationDate = terminationDate;
	}
	/**
	 * @return requiredFlg
	 */
	public boolean isRequiredFlg() {
		return RequiredFlg;
	}
	/**
	 * @param requiredFlg セットする requiredFlg
	 */
	public void setRequiredFlg(boolean requiredFlg) {
		RequiredFlg = requiredFlg;
	}
	/**
	 * @return result
	 */
	public TaskResultDto getResult() {
		return result;
	}
	/**
	 * @param result セットする result
	 */
	public void setResult(TaskResultDto result) {
		this.result = result;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public Integer getRank() {
		return rank;
	}
	public void setRank(Integer rank) {
		this.rank = rank;
	}
	public List<TaskPublicDto> getTaskPublicList() {
		return taskPublicList;
	}
	public void setTaskPublicList(List<TaskPublicDto> taskPublicList) {
		this.taskPublicList = taskPublicList;
	}
	public void addTaskPublicList(TaskPublicDto taskPublic) {
		this.taskPublicList.add(taskPublic);
	}
	public List<TaskTestCaseDto> getTaskTestCaseDtoList() {
		return taskTestCaseDtoList;
	}
	public void setTaskTestCaseDtoList(List<TaskTestCaseDto> taskTestCaseDtoList) {
		this.taskTestCaseDtoList = taskTestCaseDtoList;
	}
	public void addTaskTestCaseDtoList(TaskTestCaseDto taskTestCaseDto) {
		this.taskTestCaseDtoList.add(taskTestCaseDto);
	}


}
