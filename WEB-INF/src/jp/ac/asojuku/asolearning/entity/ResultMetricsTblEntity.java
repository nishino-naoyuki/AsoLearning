package jp.ac.asojuku.asolearning.entity;

import java.io.Serializable;

/**
 * 結果ソース品質テーブル モデルクラス.
 *
 * @author generated by ERMaster
 * @version $Id$
 */
public class ResultMetricsTblEntity implements Serializable {

	/** serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** 結果テーブル. */
	private ResultTblEntity resultTbl;

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
	 * コンストラクタ.
	 */
	public ResultMetricsTblEntity() {
	}

	/**
	 * 結果テーブル を設定します.
	 *
	 * @param resultTbl
	 *            結果テーブル
	 */
	public void setResultTbl(ResultTblEntity resultTbl) {
		this.resultTbl = resultTbl;
	}

	/**
	 * 結果テーブル を取得します.
	 *
	 * @return 結果テーブル
	 */
	public ResultTblEntity getResultTbl() {
		return this.resultTbl;
	}

	/**
	 * 最高複雑度 を設定します.
	 *
	 * @param maxMvg
	 *            最高複雑度
	 */
	public void setMaxMvg(Integer maxMvg) {
		this.maxMvg = maxMvg;
	}

	/**
	 * 最高複雑度 を取得します.
	 *
	 * @return 最高複雑度
	 */
	public Integer getMaxMvg() {
		return this.maxMvg;
	}

	/**
	 * 平均複雑度 を設定します.
	 *
	 * @param avrMvg
	 *            平均複雑度
	 */
	public void setAvrMvg(Float avrMvg) {
		this.avrMvg = avrMvg;
	}

	/**
	 * 平均複雑度 を取得します.
	 *
	 * @return 平均複雑度
	 */
	public Float getAvrMvg() {
		return this.avrMvg;
	}

	/**
	 * 最大メソッド行数 を設定します.
	 *
	 * @param maxLoc
	 *            最大メソッド行数
	 */
	public void setMaxLoc(Integer maxLoc) {
		this.maxLoc = maxLoc;
	}

	/**
	 * 最大メソッド行数 を取得します.
	 *
	 * @return 最大メソッド行数
	 */
	public Integer getMaxLoc() {
		return this.maxLoc;
	}

	/**
	 * 平均メソッド行数 を設定します.
	 *
	 * @param avrLoc
	 *            平均メソッド行数
	 */
	public void setAvrLoc(Float avrLoc) {
		this.avrLoc = avrLoc;
	}

	/**
	 * 平均メソッド行数 を取得します.
	 *
	 * @return 平均メソッド行数
	 */
	public Float getAvrLoc() {
		return this.avrLoc;
	}

	/**
	 * 最高複雑殿得点 を設定します.
	 *
	 * @param maxMvgScore
	 *            最高複雑殿得点
	 */
	public void setMaxMvgScore(Float maxMvgScore) {
		this.maxMvgScore = maxMvgScore;
	}

	/**
	 * 最高複雑殿得点 を取得します.
	 *
	 * @return 最高複雑殿得点
	 */
	public Float getMaxMvgScore() {
		return this.maxMvgScore;
	}

	/**
	 * 最大メソッド行数の点数 を設定します.
	 *
	 * @param maxLocScore
	 *            最大メソッド行数の点数
	 */
	public void setMaxLocScore(Float maxLocScore) {
		this.maxLocScore = maxLocScore;
	}

	/**
	 * 最大メソッド行数の点数 を取得します.
	 *
	 * @return 最大メソッド行数の点数
	 */
	public Float getMaxLocScore() {
		return this.maxLocScore;
	}

	/**
	 * 平均複雑度の点数 を設定します.
	 *
	 * @param avrMvgScore
	 *            平均複雑度の点数
	 */
	public void setAvrMvgScore(Float avrMvgScore) {
		this.avrMvgScore = avrMvgScore;
	}

	/**
	 * 平均複雑度の点数 を取得します.
	 *
	 * @return 平均複雑度の点数
	 */
	public Float getAvrMvgScore() {
		return this.avrMvgScore;
	}

	/**
	 * 平均メソッド行数点数 を設定します.
	 *
	 * @param avrLocScore
	 *            平均メソッド行数点数
	 */
	public void setAvrLocScore(Float avrLocScore) {
		this.avrLocScore = avrLocScore;
	}

	/**
	 * 平均メソッド行数点数 を取得します.
	 *
	 * @return 平均メソッド行数点数
	 */
	public Float getAvrLocScore() {
		return this.avrLocScore;
	}


}
