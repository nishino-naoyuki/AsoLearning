package jp.ac.asojuku.asolearning.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * ユーザーテーブル モデルクラス.
 *
 * @author generated by ERMaster
 * @version $Id$
 */
public class UserTblEntity implements Serializable {

	/** serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** USER_ID. */
	private Integer userId;

	/** メールアドレス. */
	private String mailadress;

	/** ログインパスワード. */
	private String password;

	/** 名前. */
	private String name;

	/** ニックネーム. */
	private String nickName;

	/** アカウント有効期限. */
	private Date accountExpryDate;

	/** パスワード有効期限. */
	private Date passwordExpirydate;

	/** 学科マスター. */
	private CourseMasterEntity courseMaster;

	/** 役割マスタ. */
	private RoleMasterEntity roleMaster;

	/** 初ログインフラグ. */
	private Integer isFirstFlg;

	/** 認証失敗カウント. */
	private Integer certifyErrCnt;

	/** アカウントロックフラグ. */
	private Integer isLockFlg;

	/** 入学年度. */
	private Integer admissionYear;

	/** 卒業年度. */
	private Integer graduateYear;

	/** 留年回数. */
	private Integer repeatYearCount;

	/** 退学年度. */
	private Integer giveUpYear;

	/** 登録日付. */
	private Date entryDate;

	/** 更新日付. */
	private Date updateDate;

	/** 履歴テーブル 一覧. */
	private Set<HistoryTblEntity> historyTblSet;

	/** 結果テーブル 一覧. */
	private Set<ResultTblEntity> resultTblSet;

	/** 学年（SQLで計算） */
	private Integer grade;

	/**
	 * コンストラクタ.
	 */
	public UserTblEntity() {
		this.historyTblSet = new LinkedHashSet<HistoryTblEntity>();
		this.resultTblSet = new LinkedHashSet<ResultTblEntity>();
	}

	/**
	 * USER_ID を設定します.
	 *
	 * @param userId
	 *            USER_ID
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	/**
	 * USER_ID を取得します.
	 *
	 * @return USER_ID
	 */
	public Integer getUserId() {
		return this.userId;
	}

	/**
	 * メールアドレス を設定します.
	 *
	 * @param mailadress
	 *            メールアドレス
	 */
	public void setMailadress(String mailadress) {
		this.mailadress = mailadress;
	}

	/**
	 * メールアドレス を取得します.
	 *
	 * @return メールアドレス
	 */
	public String getMailadress() {
		return this.mailadress;
	}

	/**
	 * ログインパスワード を設定します.
	 *
	 * @param password
	 *            ログインパスワード
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * ログインパスワード を取得します.
	 *
	 * @return ログインパスワード
	 */
	public String getPassword() {
		return this.password;
	}

	/**
	 * 名前 を設定します.
	 *
	 * @param name
	 *            名前
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 名前 を取得します.
	 *
	 * @return 名前
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * ニックネーム を設定します.
	 *
	 * @param nickName
	 *            ニックネーム
	 */
	public void setNickName(String nickName) {

		this.nickName = nickName;
	}

	/**
	 * ニックネーム を取得します.
	 *
	 * @return ニックネーム
	 */
	public String getNickName() {
		return this.nickName;
	}

	/**
	 * アカウント有効期限 を設定します.
	 *
	 * @param accountExpryDate
	 *            アカウント有効期限
	 */
	public void setAccountExpryDate(Date accountExpryDate) {
		this.accountExpryDate = accountExpryDate;
	}

	/**
	 * アカウント有効期限 を取得します.
	 *
	 * @return アカウント有効期限
	 */
	public Date getAccountExpryDate() {
		return this.accountExpryDate;
	}

	/**
	 * パスワード有効期限 を設定します.
	 *
	 * @param passwordExpirydate
	 *            パスワード有効期限
	 */
	public void setPasswordExpirydate(Date passwordExpirydate) {
		this.passwordExpirydate = passwordExpirydate;
	}

	/**
	 * パスワード有効期限 を取得します.
	 *
	 * @return パスワード有効期限
	 */
	public Date getPasswordExpirydate() {
		return this.passwordExpirydate;
	}

	/**
	 * 学科マスター を設定します.
	 *
	 * @param courseMaster
	 *            学科マスター
	 */
	public void setCourseMaster(CourseMasterEntity courseMaster) {
		this.courseMaster = courseMaster;
	}

	/**
	 * 学科マスター を取得します.
	 *
	 * @return 学科マスター
	 */
	public CourseMasterEntity getCourseMaster() {
		return this.courseMaster;
	}

	/**
	 * 役割マスタ を設定します.
	 *
	 * @param roleMaster
	 *            役割マスタ
	 */
	public void setRoleMaster(RoleMasterEntity roleMaster) {
		this.roleMaster = roleMaster;
	}

	/**
	 * 役割マスタ を取得します.
	 *
	 * @return 役割マスタ
	 */
	public RoleMasterEntity getRoleMaster() {
		return this.roleMaster;
	}

	/**
	 * 初ログインフラグ を設定します.
	 *
	 * @param isFirstFlg
	 *            初ログインフラグ
	 */
	public void setIsFirstFlg(Integer isFirstFlg) {
		this.isFirstFlg = isFirstFlg;
	}

	/**
	 * 初ログインフラグ を取得します.
	 *
	 * @return 初ログインフラグ
	 */
	public Integer getIsFirstFlg() {
		return this.isFirstFlg;
	}

	/**
	 * 認証失敗カウント を設定します.
	 *
	 * @param certifyErrCnt
	 *            認証失敗カウント
	 */
	public void setCertifyErrCnt(Integer certifyErrCnt) {
		this.certifyErrCnt = certifyErrCnt;
	}

	/**
	 * 認証失敗カウント を取得します.
	 *
	 * @return 認証失敗カウント
	 */
	public Integer getCertifyErrCnt() {
		return this.certifyErrCnt;
	}

	/**
	 * アカウントロックフラグ を設定します.
	 *
	 * @param isLockFlg
	 *            アカウントロックフラグ
	 */
	public void setIsLockFlg(Integer isLockFlg) {
		this.isLockFlg = isLockFlg;
	}

	/**
	 * アカウントロックフラグ を取得します.
	 *
	 * @return アカウントロックフラグ
	 */
	public Integer getIsLockFlg() {
		return this.isLockFlg;
	}

	/**
	 * 入学年度 を設定します.
	 *
	 * @param admissionYear
	 *            入学年度
	 */
	public void setAdmissionYear(Integer admissionYear) {
		this.admissionYear = admissionYear;
	}

	/**
	 * 入学年度 を取得します.
	 *
	 * @return 入学年度
	 */
	public Integer getAdmissionYear() {
		return this.admissionYear;
	}

	/**
	 * 卒業年度 を設定します.
	 *
	 * @param graduateYear
	 *            卒業年度
	 */
	public void setGraduateYear(Integer graduateYear) {
		this.graduateYear = graduateYear;
	}

	/**
	 * 卒業年度 を取得します.
	 *
	 * @return 卒業年度
	 */
	public Integer getGraduateYear() {
		return this.graduateYear;
	}

	/**
	 * @return grade
	 */
	public Integer getGrade() {
		return grade;
	}

	/**
	 * @param grade セットする grade
	 */
	public void setGrade(Integer grade) {
		this.grade = grade;
	}

	/**
	 * 留年回数 を設定します.
	 *
	 * @param repeatYearCount
	 *            留年回数
	 */
	public void setRepeatYearCount(Integer repeatYearCount) {
		this.repeatYearCount = repeatYearCount;
	}

	/**
	 * 留年回数 を取得します.
	 *
	 * @return 留年回数
	 */
	public Integer getRepeatYearCount() {
		return this.repeatYearCount;
	}

	/**
	 * 退学年度 を設定します.
	 *
	 * @param giveUpYear
	 *            退学年度
	 */
	public void setGiveUpYear(Integer giveUpYear) {
		this.giveUpYear = giveUpYear;
	}

	/**
	 * 退学年度 を取得します.
	 *
	 * @return 退学年度
	 */
	public Integer getGiveUpYear() {
		return this.giveUpYear;
	}

	/**
	 * 登録日付 を設定します.
	 *
	 * @param entryDate
	 *            登録日付
	 */
	public void setEntryDate(Date entryDate) {
		this.entryDate = entryDate;
	}

	/**
	 * 登録日付 を取得します.
	 *
	 * @return 登録日付
	 */
	public Date getEntryDate() {
		return this.entryDate;
	}

	/**
	 * 更新日付 を設定します.
	 *
	 * @param updateDate
	 *            更新日付
	 */
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	/**
	 * 更新日付 を取得します.
	 *
	 * @return 更新日付
	 */
	public Date getUpdateDate() {
		return this.updateDate;
	}

	/**
	 * 履歴テーブル 一覧を設定します.
	 *
	 * @param historyTblSet
	 *            履歴テーブル 一覧
	 */
	public void setHistoryTblSet(Set<HistoryTblEntity> historyTblSet) {
		this.historyTblSet = historyTblSet;
	}

	/**
	 * 履歴テーブル を追加します.
	 *
	 * @param historyTbl
	 *            履歴テーブル
	 */
	public void addHistoryTbl(HistoryTblEntity historyTbl) {
		this.historyTblSet.add(historyTbl);
	}

	/**
	 * 履歴テーブル 一覧を取得します.
	 *
	 * @return 履歴テーブル 一覧
	 */
	public Set<HistoryTblEntity> getHistoryTblSet() {
		return this.historyTblSet;
	}

	/**
	 * 結果テーブル 一覧を設定します.
	 *
	 * @param resultTblSet
	 *            結果テーブル 一覧
	 */
	public void setResultTblSet(Set<ResultTblEntity> resultTblSet) {
		this.resultTblSet = resultTblSet;
	}

	/**
	 * 結果テーブル を追加します.
	 *
	 * @param resultTbl
	 *            結果テーブル
	 */
	public void addResultTbl(ResultTblEntity resultTbl) {
		this.resultTblSet.add(resultTbl);
	}

	/**
	 * 結果テーブル 一覧を取得します.
	 *
	 * @return 結果テーブル 一覧
	 */
	public Set<ResultTblEntity> getResultTblSet() {
		return this.resultTblSet;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		UserTblEntity other = (UserTblEntity) obj;
		if (userId == null) {
			if (other.userId != null) {
				return false;
			}
		} else if (!userId.equals(other.userId)) {
			return false;
		}
		return true;
	}

}
