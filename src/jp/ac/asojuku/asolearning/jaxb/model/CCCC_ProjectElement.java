/**
 *
 */
package jp.ac.asojuku.asolearning.jaxb.model;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author nishino
 *
 */
@XmlRootElement(name="CCCC_Project")
public class CCCC_ProjectElement {

	//@XmlElement(name="module_detail")
	//Module_detailElement module_detail;
	public ProceduralDetail procedural_detail;

	//public Module_detailElement getModule_detail() {
	//	return module_detail;
	//}

	//public void setModule_detail(Module_detailElement module_detail) {
	//	this.module_detail = module_detail;
	//}



}
