package jp.ac.asojuku.asolearning.jaxb.model;

public class Module_detailElement {

	//<description>definition</description>
	//@XmlElement(name="description")
	private String description;

	//<source_reference file="Test.java" line="3" />
	//<lines_of_code value="2" level="0" />
	//@XmlElement(name="lines_of_code")
	private ValueElement lines_of_code;

	//<McCabes_cyclomatic_complexity value="0" level="0" />
	//@XmlElement(name="McCabes_cyclomatic_complexity")
	private ValueElement McCabes_cyclomatic_complexity;

	//<lines_of_comment value="0" level="0" />
	//@XmlElement(name="lines_of_comment")
	private ValueElement lines_of_comment;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ValueElement getLines_of_code() {
		return lines_of_code;
	}

	public void setLines_of_code(ValueElement lines_of_code) {
		this.lines_of_code = lines_of_code;
	}

	public ValueElement getMcCabes_cyclomatic_complexity() {
		return McCabes_cyclomatic_complexity;
	}

	public void setMcCabes_cyclomatic_complexity(ValueElement mcCabes_cyclomatic_complexity) {
		McCabes_cyclomatic_complexity = mcCabes_cyclomatic_complexity;
	}

	public ValueElement getLines_of_comment() {
		return lines_of_comment;
	}

	public void setLines_of_comment(ValueElement lines_of_comment) {
		this.lines_of_comment = lines_of_comment;
	}

	//<lines_of_code_per_line_of_comment value="------" level="0" />
	//<McCabes_cyclomatic_complexity_per_line_of_comment value="------" level="0" />
}
