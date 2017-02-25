/**
 *
 */
package jp.ac.asojuku.asolearning.condition;

/**
 * 課題検索条件
 * @author nishino
 *
 */
public class SearchTaskCondition {

	private String taskName;
	private String creator;
	private Integer courseId;
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public Integer getCourseId() {
		return courseId;
	}
	public void setCourseId(Integer courseId) {
		this.courseId = courseId;
	}


}
