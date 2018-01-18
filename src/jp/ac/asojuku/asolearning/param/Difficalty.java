/**
 *
 */
package jp.ac.asojuku.asolearning.param;

/**
 * 難易度
 * @author nishino
 *
 */
public enum Difficalty {

	EASY(0,"簡単"),
	NORMAL(1,"普通"),
	DIFFCAL(2,"難しい");

	//ステータス
	private int id;
	private String msg;

	/**
	 * @return id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return msg1
	 */
	public String getMsg() {
		return msg;
	}


	private Difficalty(int id, String msg) {
		this.id = id;
		this.msg = msg;
	}

	public boolean equals(Integer id){
		if(id == null){
			return false;
		}

		return (this.id == id);
	}

	public static Difficalty search(int id){
		if( EASY.equals(id)){
			return EASY;
		}else if( NORMAL.equals(id)){
			return NORMAL;
		}

		return DIFFCAL;
	}

	public static boolean check(int id){
		boolean ret = false;

		if( EASY.equals(id)){
			ret = true;
		}else if( NORMAL.equals(id)){
			ret = true;
		}else if( DIFFCAL.equals(id)){
			ret = true;
		}

		return ret;
	}
}
