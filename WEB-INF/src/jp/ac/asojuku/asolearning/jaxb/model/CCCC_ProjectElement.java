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
	Module_detailElement module_detail;
	ProceduralDetail procedural_detail;

	public Module_detailElement getModule_detail() {
		return module_detail;
	}

	public void setModule_detail(Module_detailElement module_detail) {
		this.module_detail = module_detail;
	}

	/**
	 * @return procedural_detail
	 */
	public ProceduralDetail getProcedural_detail() {
		return procedural_detail;
	}

	/**
	 * @param procedural_detail セットする procedural_detail
	 */
	public void setProcedural_detail(ProceduralDetail procedural_detail) {
		this.procedural_detail = procedural_detail;
	}


}
