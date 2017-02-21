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
	private int userId;

	private String mailAdress;
	private String name;
	private String nickName;
	private Date accountExpryDate;
	private Date passwordExpriryDate;
	private int courseId;
	private String courseName;
	private int roleId;
	private String roleName;
	private boolean isFirstFlg;
	private int certifyErrCnt;
	private boolean isLockFlg;
	private int admissionYear;
	private int graduateYear;
	private int repeatYearCount;
	private String password;

	/**
	 * @return userId
	 */
	public int getUserId() {
		return userId;
	}
	/**
	 * @param userId セットする userId
	 */
	public void setUserId(int userId) {
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
	public int getCourseId() {
		return courseId;
	}
	/**
	 * @param courseId セットする courseId
	 */
	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}
	/**
	 * @return roleId
	 */
	public int getRoleId() {
		return roleId;
	}
	/**
	 * @param roleId セットする roleId
	 */
	public void setRoleId(int roleId) {
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
	public int getCertifyErrCnt() {
		return certifyErrCnt;
	}
	/**
	 * @param certifyErrCnt セットする certifyErrCnt
	 */
	public void setCertifyErrCnt(int certifyErrCnt) {
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
	public int getAdmissionYear() {
		return admissionYear;
	}
	/**
	 * @param admissionYear セットする admissionYear
	 */
	public void setAdmissionYear(int admissionYear) {
		this.admissionYear = admissionYear;
	}
	/**
	 * @return graduateYear
	 */
	public int getGraduateYear() {
		return graduateYear;
	}
	/**
	 * @param graduateYear セットする graduateYear
	 */
	public void setGraduateYear(int graduateYear) {
		this.graduateYear = graduateYear;
	}
	/**
	 * @return repeatYearCount
	 */
	public int getRepeatYearCount() {
		return repeatYearCount;
	}
	/**
	 * @param repeatYearCount セットする repeatYearCount
	 */
	public void setRepeatYearCount(int repeatYearCount) {
		this.repeatYearCount = repeatYearCount;
	}
	/**
	 * @return courseName
	 */
	public String getCourseName() {
		return courseName;
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
