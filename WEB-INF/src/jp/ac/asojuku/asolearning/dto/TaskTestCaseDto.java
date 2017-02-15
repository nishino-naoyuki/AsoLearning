/**
 *
 */
package jp.ac.asojuku.asolearning.dto;

/**
 * テストケース
 * @author nishino
 *
 */
public class TaskTestCaseDto {

	private Integer testcaseId;

	/** 配点. */
	private Integer allmostOfMarks;

	/** 正解出力ファイル名. */
	private String outputFileName;

	/** 入力ファイル名. */
	private String inputFileName;

	/**
	 * @return allmostOfMarks
	 */
	public Integer getAllmostOfMarks() {
		return allmostOfMarks;
	}

	/**
	 * @param allmostOfMarks セットする allmostOfMarks
	 */
	public void setAllmostOfMarks(Integer allmostOfMarks) {
		this.allmostOfMarks = allmostOfMarks;
	}

	/**
	 * @return outputFileName
	 */
	public String getOutputFileName() {
		return outputFileName;
	}

	/**
	 * @param outputFileName セットする outputFileName
	 */
	public void setOutputFileName(String outputFileName) {
		this.outputFileName = outputFileName;
	}

	/**
	 * @return inputFileName
	 */
	public String getInputFileName() {
		return inputFileName;
	}

	/**
	 * @param inputFileName セットする inputFileName
	 */
	public void setInputFileName(String inputFileName) {
		this.inputFileName = inputFileName;
	}

	/**
	 * @return testcaseId
	 */
	public Integer getTestcaseId() {
		return testcaseId;
	}

	/**
	 * @param testcaseId セットする testcaseId
	 */
	public void setTestcaseId(Integer testcaseId) {
		this.testcaseId = testcaseId;
	}
}
