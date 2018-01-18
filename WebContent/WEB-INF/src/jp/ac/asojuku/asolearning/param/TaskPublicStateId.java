/**
 *
 */
package jp.ac.asojuku.asolearning.param;

/**
 * 課題の公開ID
 * @author nishino
 *
 */
public enum TaskPublicStateId {

	PRIVATE(0,"非公開",""),
	PUBLIC_MUST(1,"公開（必須）","必須"),
	PUBLIC(2,"公開（任意）","必須");

	//ステータス
	private int id;
	private String msg1;
	private String msg2;

	/**
	 * @return id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return msg1
	 */
	public String getMsg1() {
		return msg1;
	}

	/**
	 * @return msg2
	 */
	public String getMsg2() {
		return msg2;
	}

	private TaskPublicStateId(int id, String msg1, String msg2) {
		this.id = id;
		this.msg1 = msg1;
		this.msg2 = msg2;
	}

	public boolean equals(Integer id){
		if(id == null){
			return false;
		}

		return (this.id == id);
	}

	public static TaskPublicStateId valueOf(int id){
		TaskPublicStateId st = PUBLIC;

		if( PRIVATE.equals(id)){
			st = PRIVATE;
		}else if( PUBLIC_MUST.equals(id)){
			st = PUBLIC_MUST;
		}

		return st;
	}
}
