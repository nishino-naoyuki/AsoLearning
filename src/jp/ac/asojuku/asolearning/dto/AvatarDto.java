/**
 *
 */
package jp.ac.asojuku.asolearning.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jp.ac.asojuku.asolearning.param.AvatarKind;

/**
 * @author nishino
 *
 */
public class AvatarDto implements Serializable{
	private AvatarKind kind;
	private List<Element> avatarList = new ArrayList<Element>();

	/**
	 * @return kind
	 */
	public AvatarKind getKind() {
		return kind;
	}

	/**
	 * @param kind セットする kind
	 */
	public void setKind(AvatarKind kind) {
		this.kind = kind;
	}

	/**
	 * @return avatarList
	 */
	public List<Element> getAvatarList() {
		return avatarList;
	}

	/**
	 * @param avatarList セットする avatarList
	 */
	public void setAvatarList(List<Element> avatarList) {
		this.avatarList = avatarList;
	}

	public void addAvatarList(Element element){
		avatarList.add(element);
	}
	public class Element implements Serializable{
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
}
