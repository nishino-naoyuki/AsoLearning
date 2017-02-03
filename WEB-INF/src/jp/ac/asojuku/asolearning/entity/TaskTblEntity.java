package jp.ac.asojuku.asolearning.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * 課題テーブル モデルクラス.
 * 
 * @author generated by ERMaster
 * @version $Id$
 */
public class TaskTblEntity implements Serializable {

	/** serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** タスクID. */
	private Integer taskId;

	/** 課題名. */
	private String name;

	/** 問題文. */
	private String taskQuestion;

	/** 作成者. */
	private Integer createUserId;

	/** ENTRY_DATE. */
	private Date entryDate;

	/** 更新日付. */
	private Date updateTim;

	/** 締め切り. */
	private Date terminationDate;

	/** 結果テーブル 一覧. */
	private Set<ResultTblEntity> resultTblSet;

	/** 課題公開テーブル 一覧. */
	private Set<TaskPublicTblEntity> taskPublicTblSet;

	/** テストケーステーブル 一覧. */
	private Set<TestcaseTableEntity> testcaseTableSet;

	/**
	 * コンストラクタ.
	 */
	public TaskTblEntity() {
		this.resultTblSet = new HashSet<ResultTblEntity>();
		this.taskPublicTblSet = new HashSet<TaskPublicTblEntity>();
		this.testcaseTableSet = new HashSet<TestcaseTableEntity>();
	}

	/**
	 * タスクID を設定します.
	 * 
	 * @param taskId
	 *            タスクID
	 */
	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}

	/**
	 * タスクID を取得します.
	 * 
	 * @return タスクID
	 */
	public Integer getTaskId() {
		return this.taskId;
	}

	/**
	 * 課題名 を設定します.
	 * 
	 * @param name
	 *            課題名
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 課題名 を取得します.
	 * 
	 * @return 課題名
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * 問題文 を設定します.
	 * 
	 * @param taskQuestion
	 *            問題文
	 */
	public void setTaskQuestion(String taskQuestion) {
		this.taskQuestion = taskQuestion;
	}

	/**
	 * 問題文 を取得します.
	 * 
	 * @return 問題文
	 */
	public String getTaskQuestion() {
		return this.taskQuestion;
	}

	/**
	 * 作成者 を設定します.
	 * 
	 * @param createUserId
	 *            作成者
	 */
	public void setCreateUserId(Integer createUserId) {
		this.createUserId = createUserId;
	}

	/**
	 * 作成者 を取得します.
	 * 
	 * @return 作成者
	 */
	public Integer getCreateUserId() {
		return this.createUserId;
	}

	/**
	 * ENTRY_DATE を設定します.
	 * 
	 * @param entryDate
	 *            ENTRY_DATE
	 */
	public void setEntryDate(Date entryDate) {
		this.entryDate = entryDate;
	}

	/**
	 * ENTRY_DATE を取得します.
	 * 
	 * @return ENTRY_DATE
	 */
	public Date getEntryDate() {
		return this.entryDate;
	}

	/**
	 * 更新日付 を設定します.
	 * 
	 * @param updateTim
	 *            更新日付
	 */
	public void setUpdateTim(Date updateTim) {
		this.updateTim = updateTim;
	}

	/**
	 * 更新日付 を取得します.
	 * 
	 * @return 更新日付
	 */
	public Date getUpdateTim() {
		return this.updateTim;
	}

	/**
	 * 締め切り を設定します.
	 * 
	 * @param terminationDate
	 *            締め切り
	 */
	public void setTerminationDate(Date terminationDate) {
		this.terminationDate = terminationDate;
	}

	/**
	 * 締め切り を取得します.
	 * 
	 * @return 締め切り
	 */
	public Date getTerminationDate() {
		return this.terminationDate;
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
	 * 課題公開テーブル 一覧を設定します.
	 * 
	 * @param taskPublicTblSet
	 *            課題公開テーブル 一覧
	 */
	public void setTaskPublicTblSet(Set<TaskPublicTblEntity> taskPublicTblSet) {
		this.taskPublicTblSet = taskPublicTblSet;
	}

	/**
	 * 課題公開テーブル を追加します.
	 * 
	 * @param taskPublicTbl
	 *            課題公開テーブル
	 */
	public void addTaskPublicTbl(TaskPublicTblEntity taskPublicTbl) {
		this.taskPublicTblSet.add(taskPublicTbl);
	}

	/**
	 * 課題公開テーブル 一覧を取得します.
	 * 
	 * @return 課題公開テーブル 一覧
	 */
	public Set<TaskPublicTblEntity> getTaskPublicTblSet() {
		return this.taskPublicTblSet;
	}

	/**
	 * テストケーステーブル 一覧を設定します.
	 * 
	 * @param testcaseTableSet
	 *            テストケーステーブル 一覧
	 */
	public void setTestcaseTableSet(Set<TestcaseTableEntity> testcaseTableSet) {
		this.testcaseTableSet = testcaseTableSet;
	}

	/**
	 * テストケーステーブル を追加します.
	 * 
	 * @param testcaseTable
	 *            テストケーステーブル
	 */
	public void addTestcaseTable(TestcaseTableEntity testcaseTable) {
		this.testcaseTableSet.add(testcaseTable);
	}

	/**
	 * テストケーステーブル 一覧を取得します.
	 * 
	 * @return テストケーステーブル 一覧
	 */
	public Set<TestcaseTableEntity> getTestcaseTableSet() {
		return this.testcaseTableSet;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((taskId == null) ? 0 : taskId.hashCode());
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
		TaskTblEntity other = (TaskTblEntity) obj;
		if (taskId == null) {
			if (other.taskId != null) {
				return false;
			}
		} else if (!taskId.equals(other.taskId)) {
			return false;
		}
		return true;
	}

}
