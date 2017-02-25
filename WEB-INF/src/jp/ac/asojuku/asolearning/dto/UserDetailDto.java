/**
 *
 */
package jp.ac.asojuku.asolearning.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author nishino
 *
 */
public class UserDetailDto {

	private Integer userId;
	private String mailAdress;
	private String name;
	private String nickName;
	private Date accountExpryDate;
	private Date passwordExpriryDate;
	private Integer courseId;
	private String courseName;
	private Integer roleId;
	private String roleName;
	private boolean isFirstFlg;
	private Integer certifyErrCnt;
	private boolean isLockFlg;
	private String admissionYear;
	private String graduateYear;
	private String repeatYearCount;
	private String password;
	private Integer grade;
	private List<TaskResultDto> resultList = new ArrayList<>();

	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getMailAdress() {
		return mailAdress;
	}
	public void setMailAdress(String mailAdress) {
		this.mailAdress = mailAdress;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public Date getAccountExpryDate() {
		return accountExpryDate;
	}
	public void setAccountExpryDate(Date accountExpryDate) {
		this.accountExpryDate = accountExpryDate;
	}
	public Date getPasswordExpriryDate() {
		return passwordExpriryDate;
	}
	public void setPasswordExpriryDate(Date passwordExpriryDate) {
		this.passwordExpriryDate = passwordExpriryDate;
	}
	public Integer getCourseId() {
		return courseId;
	}
	public void setCourseId(Integer courseId) {
		this.courseId = courseId;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public boolean isFirstFlg() {
		return isFirstFlg;
	}
	public void setFirstFlg(boolean isFirstFlg) {
		this.isFirstFlg = isFirstFlg;
	}
	public Integer getCertifyErrCnt() {
		return certifyErrCnt;
	}
	public void setCertifyErrCnt(Integer certifyErrCnt) {
		this.certifyErrCnt = certifyErrCnt;
	}
	public boolean isLockFlg() {
		return isLockFlg;
	}
	public void setLockFlg(boolean isLockFlg) {
		this.isLockFlg = isLockFlg;
	}
	public String getAdmissionYear() {
		return admissionYear;
	}
	public void setAdmissionYear(String admissionYear) {
		this.admissionYear = admissionYear;
	}
	public String getGraduateYear() {
		return graduateYear;
	}
	public void setGraduateYear(String graduateYear) {
		this.graduateYear = graduateYear;
	}
	public String getRepeatYearCount() {
		return repeatYearCount;
	}
	public void setRepeatYearCount(String repeatYearCount) {
		this.repeatYearCount = repeatYearCount;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Integer getGrade() {
		return grade;
	}
	public void setGrade(Integer grade) {
		this.grade = grade;
	}
	public List<TaskResultDto> getResultList() {
		return resultList;
	}
	public void setResultList(List<TaskResultDto> resultList) {
		this.resultList = resultList;
	}
	public void addResultList(TaskResultDto resultDto){
		resultList.add(resultDto);
	}


}
