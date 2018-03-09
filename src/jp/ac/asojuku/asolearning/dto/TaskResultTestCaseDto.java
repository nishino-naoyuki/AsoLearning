/**
 *
 */
package jp.ac.asojuku.asolearning.dto;

import java.io.Serializable;

/**
 * @author nishino
 *
 */
public class TaskResultTestCaseDto implements Serializable{

	/** テストケースID. */
	private Integer testcaseId;

	/** 得点. */
	private Integer score;

	/** メッセージ. */
	private String message;

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

	/**
	 * @return score
	 */
	public Integer getScore() {
		return score;
	}

	/**
	 * @param score セットする score
	 */
	public void setScore(Integer score) {
		this.score = score;
	}

	/**
	 * @return message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message セットする message
	 */
	public void setMessage(String message) {
		this.message = message;
	}
}
