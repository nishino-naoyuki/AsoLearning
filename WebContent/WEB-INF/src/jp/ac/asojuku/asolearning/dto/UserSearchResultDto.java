package jp.ac.asojuku.asolearning.dto;

import java.util.ArrayList;
import java.util.List;

public class UserSearchResultDto {

	private UserDto userDto;
	private List<TaskResultDto> resultList = new ArrayList<TaskResultDto>();

	public UserDto getUserDto() {
		return userDto;
	}
	public void setUserDto(UserDto userDto) {
		this.userDto = userDto;
	}
	public List<TaskResultDto> getResultList() {
		return resultList;
	}
	public void setResultList(List<TaskResultDto> resultList) {
		this.resultList = resultList;
	}
	public void addResultList(TaskResultDto result){
		resultList.add(result);
	}
}
