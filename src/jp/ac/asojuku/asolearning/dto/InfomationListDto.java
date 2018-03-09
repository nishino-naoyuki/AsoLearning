/**
 *
 */
package jp.ac.asojuku.asolearning.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author nishino
 *
 */
public class InfomationListDto implements Serializable{

	private List<String> infoList = new ArrayList<>();
	private Integer taskNum;			//課題の数
	private Integer finishTaskNum;		//終了済みの課題の数
	private Integer mustTaskNum;			//必須の課題の数
	private Integer finishMustTaskNum;		//終了済みの必須課題の数
	private List<String> unAnswerList = new ArrayList<>();


	public List<String> getInfoList() {
		return infoList;
	}

	public void setInfoList(List<String> infoList) {
		this.infoList = infoList;
	}


	public void addInfoList(String info){
		infoList.add(info);
	}

	/**
	 * @return taskNum
	 */
	public Integer getTaskNum() {
		return taskNum;
	}

	/**
	 * @param taskNum セットする taskNum
	 */
	public void setTaskNum(Integer taskNum) {
		this.taskNum = taskNum;
	}

	/**
	 * @return finishTaskNum
	 */
	public Integer getFinishTaskNum() {
		return finishTaskNum;
	}

	/**
	 * @param finishTaskNum セットする finishTaskNum
	 */
	public void setFinishTaskNum(Integer finishTaskNum) {
		this.finishTaskNum = finishTaskNum;
	}

	/**
	 * @return mustTaskNum
	 */
	public Integer getMustTaskNum() {
		return mustTaskNum;
	}

	/**
	 * @param mustTaskNum セットする mustTaskNum
	 */
	public void setMustTaskNum(Integer mustTaskNum) {
		this.mustTaskNum = mustTaskNum;
	}

	/**
	 * @return finishMustTaskNum
	 */
	public Integer getFinishMustTaskNum() {
		return finishMustTaskNum;
	}

	/**
	 * @param finishMustTaskNum セットする finishMustTaskNum
	 */
	public void setFinishMustTaskNum(Integer finishMustTaskNum) {
		this.finishMustTaskNum = finishMustTaskNum;
	}

	/**
	 * @return unAnswerList
	 */
	public List<String> getUnAnswerList() {
		return unAnswerList;
	}

	/**
	 * @param unAnswerList セットする unAnswerList
	 */
	public void setUnAnswerList(List<String> unAnswerList) {
		this.unAnswerList = unAnswerList;
	}
	public void addUnAnswerList(String info){
		unAnswerList.add(info);
	}

}
