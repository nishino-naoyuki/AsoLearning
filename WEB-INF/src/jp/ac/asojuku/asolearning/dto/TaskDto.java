/**
 *
 */
package jp.ac.asojuku.asolearning.dto;

import java.util.Date;

/**
 * 課題情報
 * @author nishino
 *
 */
public class TaskDto {

	private int taskId;
	private String taskName;		//課題名
	private Date TerminationDate;	//締め切り
	private boolean RequiredFlg;	//公開設定
	private String question;		//問題文
	private TaskResultDto result;
	private TaskDetailDto detail;
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
	public Date getTerminationDate() {
		return TerminationDate;
	}
	/**
	 * @param terminationDate セットする terminationDate
	 */
	public void setTerminationDate(Date terminationDate) {
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
	/**
	 * @return detail
	 */
	public TaskDetailDto getDetail() {
		return detail;
	}
	/**
	 * @param detail セットする detail
	 */
	public void setDetail(TaskDetailDto detail) {
		this.detail = detail;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}


}
