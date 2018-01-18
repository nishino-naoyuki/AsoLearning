/**
 *
 */
package jp.ac.asojuku.asolearning.condition;

/**
 * @author nishino
 *
 */
public class SearchTaskResultCondition {

	private Integer ansConditionEasy;
	private Integer ansConditionNormal;
	private Integer ansConditionHard;

	private float totalConditionEasy;
	private float totalConditionNormal;
	private float totalConditionHard;

	public SearchTaskResultCondition(){
		ansConditionEasy = 0;
		ansConditionNormal = 0;
		ansConditionHard = 0;

		totalConditionEasy = 0;
		totalConditionNormal = 0;
		totalConditionHard = 0;
	}

	/**
	 * @return ansConditionEasy
	 */
	public Integer getAnsConditionEasy() {
		return ansConditionEasy;
	}
	/**
	 * @param ansConditionEasy セットする ansConditionEasy
	 */
	public void setAnsConditionEasy(Integer ansConditionEasy) {
		this.ansConditionEasy = ansConditionEasy;
	}
	/**
	 * @return ansConditionNormal
	 */
	public Integer getAnsConditionNormal() {
		return ansConditionNormal;
	}
	/**
	 * @param ansConditionNormal セットする ansConditionNormal
	 */
	public void setAnsConditionNormal(Integer ansConditionNormal) {
		this.ansConditionNormal = ansConditionNormal;
	}
	/**
	 * @return ansConditionHard
	 */
	public Integer getAnsConditionHard() {
		return ansConditionHard;
	}
	/**
	 * @param ansConditionHard セットする ansConditionHard
	 */
	public void setAnsConditionHard(Integer ansConditionHard) {
		this.ansConditionHard = ansConditionHard;
	}
	/**
	 * @return totalConditionEasy
	 */
	public float getTotalConditionEasy() {
		return totalConditionEasy;
	}
	/**
	 * @param totalConditionEasy セットする totalConditionEasy
	 */
	public void setTotalConditionEasy(Integer totalConditionEasy) {
		this.totalConditionEasy = totalConditionEasy;
	}
	/**
	 * @return totalConditionNormal
	 */
	public float getTotalConditionNormal() {
		return totalConditionNormal;
	}
	/**
	 * @param totalConditionNormal セットする totalConditionNormal
	 */
	public void setTotalConditionNormal(Integer totalConditionNormal) {
		this.totalConditionNormal = totalConditionNormal;
	}
	/**
	 * @return totalConditionHard
	 */
	public float getTotalConditionHard() {
		return totalConditionHard;
	}
	/**
	 * @param totalConditionHard セットする totalConditionHard
	 */
	public void setTotalConditionHard(Integer totalConditionHard) {
		this.totalConditionHard = totalConditionHard;
	}

	public void addAnsConditionEasy(){
		ansConditionEasy++;
	}
	public void addAnsConditionNormal(){
		ansConditionNormal++;
	}
	public void addAnsConditionHard(){
		ansConditionHard++;
	}

	public void addTotalConditionEasy(float score){
		totalConditionEasy+=score;
	}
	public void addTotalConditionNormal(float score){
		totalConditionNormal+=score;
	}
	public void addTotalConditionHard(float score){
		totalConditionHard+=score;
	}


}
