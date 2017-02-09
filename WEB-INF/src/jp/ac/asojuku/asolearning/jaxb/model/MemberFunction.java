/**
 *
 */
package jp.ac.asojuku.asolearning.jaxb.model;

/**
 * @author nishino
 *
 */
public class MemberFunction {

	private String name;
	private ValueElement lines_of_code;
	private ValueElement McCabes_cyclomatic_complexity;
	private ValueElement lines_of_comment;
	private ValueElement lines_of_code_per_line_of_comment;
	private ValueElement McCabes_cyclomatic_complexity_per_line_of_comment;
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
	/**
	 * @return lines_of_code
	 */
	public ValueElement getLines_of_code() {
		return lines_of_code;
	}
	/**
	 * @param lines_of_code セットする lines_of_code
	 */
	public void setLines_of_code(ValueElement lines_of_code) {
		this.lines_of_code = lines_of_code;
	}
	/**
	 * @return mcCabes_cyclomatic_complexity
	 */
	public ValueElement getMcCabes_cyclomatic_complexity() {
		return McCabes_cyclomatic_complexity;
	}
	/**
	 * @param mcCabes_cyclomatic_complexity セットする mcCabes_cyclomatic_complexity
	 */
	public void setMcCabes_cyclomatic_complexity(ValueElement mcCabes_cyclomatic_complexity) {
		McCabes_cyclomatic_complexity = mcCabes_cyclomatic_complexity;
	}
	/**
	 * @return lines_of_comment
	 */
	public ValueElement getLines_of_comment() {
		return lines_of_comment;
	}
	/**
	 * @param lines_of_comment セットする lines_of_comment
	 */
	public void setLines_of_comment(ValueElement lines_of_comment) {
		this.lines_of_comment = lines_of_comment;
	}
	/**
	 * @return lines_of_code_per_line_of_comment
	 */
	public ValueElement getLines_of_code_per_line_of_comment() {
		return lines_of_code_per_line_of_comment;
	}
	/**
	 * @param lines_of_code_per_line_of_comment セットする lines_of_code_per_line_of_comment
	 */
	public void setLines_of_code_per_line_of_comment(ValueElement lines_of_code_per_line_of_comment) {
		this.lines_of_code_per_line_of_comment = lines_of_code_per_line_of_comment;
	}
	/**
	 * @return mcCabes_cyclomatic_complexity_per_line_of_comment
	 */
	public ValueElement getMcCabes_cyclomatic_complexity_per_line_of_comment() {
		return McCabes_cyclomatic_complexity_per_line_of_comment;
	}
	/**
	 * @param mcCabes_cyclomatic_complexity_per_line_of_comment セットする mcCabes_cyclomatic_complexity_per_line_of_comment
	 */
	public void setMcCabes_cyclomatic_complexity_per_line_of_comment(
			ValueElement mcCabes_cyclomatic_complexity_per_line_of_comment) {
		McCabes_cyclomatic_complexity_per_line_of_comment = mcCabes_cyclomatic_complexity_per_line_of_comment;
	}


}
