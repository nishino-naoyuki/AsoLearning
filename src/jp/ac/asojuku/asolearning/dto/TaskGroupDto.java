/**
 *
 */
package jp.ac.asojuku.asolearning.dto;

/**
 * タスクグループのDTO
 * @author nishino
 *
 */
public class TaskGroupDto {
	private int id;
	private String name;
	/**
	 * @return id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id セットする id
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name セットする name
	 */
	public void setName(String name) {
		this.name = name;
	}



}
