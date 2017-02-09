/**
 *
 */
package jp.ac.asojuku.asolearning.jaxb.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;

/**
 * @author nishino
 *
 */
public class ProceduralDetail {

	@XmlElement(name = "member_function")
	private List<MemberFunction> memberFunctionList;

	/**
	 * @return memberFunctionList
	 */
	public List<MemberFunction> getMemberFunctionList() {
		return memberFunctionList;
	}

	/**
	 * @param memberFunctionList セットする memberFunctionList
	 */
	public void setMemberFunctionList(List<MemberFunction> memberFunctionList) {
		this.memberFunctionList = memberFunctionList;
	}


}
