/**
 *
 */
package jp.ac.asojuku.asolearning.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * @author nishino
 *
 */
public class InfomationDto {

	List<String> infoList = new ArrayList<>();

	public List<String> getInfoList() {
		return infoList;
	}

	public void setInfoList(List<String> infoList) {
		this.infoList = infoList;
	}


	public void addInfoList(String info){
		infoList.add(info);
	}
}
