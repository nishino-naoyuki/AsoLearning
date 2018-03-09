/**
 *
 */
package jp.ac.asojuku.asolearning.dto;

import java.io.Serializable;

/**
 * タスクグループのDTO
 * @author nishino
 *
 */
public class TaskGroupDto implements Serializable{
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
