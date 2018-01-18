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
	public List<MemberFunction> member_function;


}
