/**
 *
 */
package jp.ac.asojuku.asolearning.csv.model;

/**
 * ユーザー登録CSV
 * @author nishino
 *
 */
public class UserCSV {

	//ロールID
	private int roleId;
	//学籍番号/社員番号
	private String name;
	//メールアドレス
	private String mailAddress;
	//ニックネーム
	private String nickName;
	//学科
	private int courseId;
	//パスワード
	private String password;
	//入学年度
	private String admissionYear;
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMailAddress() {
		return mailAddress;
	}
	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public int getCourseId() {
		return courseId;
	}
	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getAdmissionYear() {
		return admissionYear;
	}
	public void setAdmissionYear(String admissionYear) {
		this.admissionYear = admissionYear;
	}

}
