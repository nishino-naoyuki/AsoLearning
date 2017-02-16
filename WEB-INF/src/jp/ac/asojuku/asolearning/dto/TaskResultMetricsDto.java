/**
 *
 */
package jp.ac.asojuku.asolearning.dto;

/**
 * @author nishino
 *
 */
public class TaskResultMetricsDto {

	/** 最高複雑度. */
	private Integer maxMvg;

	/** 平均複雑度. */
	private Float avrMvg;

	/** 最大メソッド行数. */
	private Integer maxLoc;

	/** 平均メソッド行数. */
	private Float avrLoc;

	/** 最高複雑殿得点. */
	private Float maxMvgScore;

	/** 最大メソッド行数の点数. */
	private Float maxLocScore;

	/** 平均複雑度の点数. */
	private Float avrMvgScore;

	/** 平均メソッド行数点数. */
	private Float avrLocScore;

	/**
	 * @return maxMvg
	 */
	public Integer getMaxMvg() {
		return maxMvg;
	}

	/**
	 * @param maxMvg セットする maxMvg
	 */
	public void setMaxMvg(Integer maxMvg) {
		this.maxMvg = maxMvg;
	}

	/**
	 * @return avrMvg
	 */
	public Float getAvrMvg() {
		return avrMvg;
	}

	/**
	 * @param avrMvg セットする avrMvg
	 */
	public void setAvrMvg(Float avrMvg) {
		this.avrMvg = avrMvg;
	}

	/**
	 * @return maxLoc
	 */
	public Integer getMaxLoc() {
		return maxLoc;
	}

	/**
	 * @param maxLoc セットする maxLoc
	 */
	public void setMaxLoc(Integer maxLoc) {
		this.maxLoc = maxLoc;
	}

	/**
	 * @return avrLoc
	 */
	public Float getAvrLoc() {
		return avrLoc;
	}

	/**
	 * @param avrLoc セットする avrLoc
	 */
	public void setAvrLoc(Float avrLoc) {
		this.avrLoc = avrLoc;
	}

	/**
	 * @return maxMvgScore
	 */
	public Float getMaxMvgScore() {
		return maxMvgScore;
	}

	/**
	 * @param maxMvgScore セットする maxMvgScore
	 */
	public void setMaxMvgScore(Float maxMvgScore) {
		this.maxMvgScore = maxMvgScore;
	}

	/**
	 * @return maxLocScore
	 */
	public Float getMaxLocScore() {
		return maxLocScore;
	}

	/**
	 * @param maxLocScore セットする maxLocScore
	 */
	public void setMaxLocScore(Float maxLocScore) {
		this.maxLocScore = maxLocScore;
	}

	/**
	 * @return avrMvgScore
	 */
	public Float getAvrMvgScore() {
		return avrMvgScore;
	}

	/**
	 * @param avrMvgScore セットする avrMvgScore
	 */
	public void setAvrMvgScore(Float avrMvgScore) {
		this.avrMvgScore = avrMvgScore;
	}

	/**
	 * @return avrLocScore
	 */
	public Float getAvrLocScore() {
		return avrLocScore;
	}

	/**
	 * @param avrLocScore セットする avrLocScore
	 */
	public void setAvrLocScore(Float avrLocScore) {
		this.avrLocScore = avrLocScore;
	}


}
