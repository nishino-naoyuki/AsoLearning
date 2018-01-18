/**
 *
 */
package jp.ac.asojuku.asolearning.param;

/**
 * お知らせ情報公開ID
 * @author nishino
 *
 */
public enum InfoPublicStateId {

	PRIVATE(0,"非公開",""),
	PUBLIC(1,"公開","");

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

	private InfoPublicStateId(int id, String msg1, String msg2) {
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

	public static InfoPublicStateId valueOf(int id){
		InfoPublicStateId st = PUBLIC;

		if( PRIVATE.equals(id)){
			st = PRIVATE;
		}

		return st;
	}
}
