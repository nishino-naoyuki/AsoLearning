package jp.ac.asojuku.asolearning.jaxb.model;

import javax.xml.bind.annotation.XmlAttribute;

public class ValueElement {

	@XmlAttribute(name="value")
	private int value;

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

}
