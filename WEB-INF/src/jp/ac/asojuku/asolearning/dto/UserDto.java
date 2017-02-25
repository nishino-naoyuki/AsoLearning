/**
 *
 */
package jp.ac.asojuku.asolearning.dto;

import java.util.Date;

/**
 * ユーザー情報
 * @author nishino
 *
 */
public class UserDto {
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

	/**
	 * @return userId
	 */
	public Integer getUserId() {
		return userId;
	}
	/**
	 * @param userId セットする userId
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	/**
	 * @return mailAdress
	 */
	public String getMailAdress() {
		return mailAdress;
	}
	/**
	 * @param mailAdress セットする mailAdress
	 */
	public void setMailAdress(String mailAdress) {
		this.mailAdress = mailAdress;
	}
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
	 * @return nickName
	 */
	public String getNickName() {
		return nickName;
	}
	/**
	 * @param nickName セットする nickName
	 */
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	/**
	 * @return accountExpryDate
	 */
	public Date getAccountExpryDate() {
		return accountExpryDate;
	}
	/**
	 * @param accountExpryDate セットする accountExpryDate
	 */
	public void setAccountExpryDate(Date accountExpryDate) {
		this.accountExpryDate = accountExpryDate;
	}
	/**
	 * @return passwordExpriryDate
	 */
	public Date getPasswordExpriryDate() {
		return passwordExpriryDate;
	}
	/**
	 * @param passwordExpriryDate セットする passwordExpriryDate
	 */
	public void setPasswordExpriryDate(Date passwordExpriryDate) {
		this.passwordExpriryDate = passwordExpriryDate;
	}
	/**
	 * @return courseId
	 */
	public Integer getCourseId() {
		return courseId;
	}
	/**
	 * @param courseId セットする courseId
	 */
	public void setCourseId(Integer courseId) {
		this.courseId = courseId;
	}
	/**
	 * @return roleId
	 */
	public Integer getRoleId() {
		return roleId;
	}
	/**
	 * @param roleId セットする roleId
	 */
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	/**
	 * @return isFirstFlg
	 */
	public boolean isFirstFlg() {
		return isFirstFlg;
	}
	/**
	 * @param isFirstFlg セットする isFirstFlg
	 */
	public void setFirstFlg(boolean isFirstFlg) {
		this.isFirstFlg = isFirstFlg;
	}
	/**
	 * @return certifyErrCnt
	 */
	public Integer getCertifyErrCnt() {
		return certifyErrCnt;
	}
	/**
	 * @param certifyErrCnt セットする certifyErrCnt
	 */
	public void setCertifyErrCnt(Integer certifyErrCnt) {
		this.certifyErrCnt = certifyErrCnt;
	}
	/**
	 * @return isLockFlg
	 */
	public boolean isLockFlg() {
		return isLockFlg;
	}
	/**
	 * @param isLockFlg セットする isLockFlg
	 */
	public void setLockFlg(boolean isLockFlg) {
		this.isLockFlg = isLockFlg;
	}
	/**
	 * @return admissionYear
	 */
	public String getAdmissionYear() {
		return admissionYear;
	}
	/**
	 * @param admissionYear セットする admissionYear
	 */
	public void setAdmissionYear(String admissionYear) {
		this.admissionYear = admissionYear;
	}
	/**
	 * @return graduateYear
	 */
	public String getGraduateYear() {
		return graduateYear;
	}
	/**
	 * @param graduateYear セットする graduateYear
	 */
	public void setGraduateYear(String graduateYear) {
		this.graduateYear = graduateYear;
	}
	/**
	 * @return repeatYearCount
	 */
	public String getRepeatYearCount() {
		return repeatYearCount;
	}
	/**
	 * @param repeatYearCount セットする repeatYearCount
	 */
	public void setRepeatYearCount(String repeatYearCount) {
		this.repeatYearCount = repeatYearCount;
	}
	/**
	 * @return courseName
	 */
	public String getCourseName() {
		return courseName;
	}
	public Integer getGrade() {
		return grade;
	}
	public void setGrade(Integer grade) {
		this.grade = grade;
	}
	/**
	 * @param courseName セットする courseName
	 */
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	/**
	 * @return roleName
	 */
	public String getRoleName() {
		return roleName;
	}
	/**
	 * @param roleName セットする roleName
	 */
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	/**
	 * @return password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password セットする password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

}
