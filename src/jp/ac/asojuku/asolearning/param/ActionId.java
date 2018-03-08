/**
 *
 */
package jp.ac.asojuku.asolearning.param;

import jp.ac.asojuku.asolearning.exception.AsoLearningSystemErrException;

/**
 * 動作ログのアクションID
 * マスターと一致する必要あり
 * @author nishino
 *
 */
public enum ActionId {
	LOGIN(0,"ログイン"),//ログイン
	LOGOUT(1,"ログアウト"),//ログアウト
	TASK_JUDG(2,"課題判定"),//課題判定
	TASK_CREATE(3,"課題作成"),//課題作成
	TASK_UPDATE(4,"課題編集"),////課題編集
	USER_CREATE(5,"ユーザー作成"),//ユーザー作成
	USER_UPDATE(6,"ユーザー編集"),//ユーザー編集
	CSV_CREATE(7,"CSV登録（作成）"),//CSV登録（作成）
	PWD_CHANGE(8,"パスワード変更"),//パスワード変更
	NICK_NAME(9,"ニックネーム変更"),//ニックネーム変更
	GRADUATE(10,"卒業処理"),//卒業処理
	REPEAT_YEAR(11,"留年処理"),//留年処理
	GIVEUP(12,"退学処理")//退学処理
	;

	//ステータス
	private int id;
	private String name;

	/**
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return id
	 */
	public int getId() {
		return id;
	}



	private ActionId(int id,String name) {
		this.id = id;
		this.name= name;
	}

	public boolean equals(Integer id){
		if(id == null){
			return false;
		}

		return (this.id == id);
	}

	/**
	 * 引数で受け取った数値からActionIDを取得する
	 *
	 * @param id
	 * @return
	 */
	public static ActionId getByInteger(Integer id) throws AsoLearningSystemErrException{
		ActionId result = null;

		for(ActionId aid : values()){
			if( aid.equals(id) ){
				result = aid;
				break;
			}
		}

		return result;
	}


	public static boolean check(int id){
		boolean ret = false;

		if( LOGIN.equals(id)){
			ret = true;
		}else if( LOGOUT.equals(id)){
			ret = true;
		}else if( TASK_JUDG.equals(id)){
			ret = true;
		}else if( TASK_CREATE.equals(id)){
			ret = true;
		}else if( TASK_UPDATE.equals(id)){
			ret = true;
		}else if( USER_CREATE.equals(id)){
			ret = true;
		}else if( USER_UPDATE.equals(id)){
			ret = true;
		}else if( CSV_CREATE.equals(id)){
			ret = true;
		}else if( PWD_CHANGE.equals(id)){
			ret = true;
		}else if( NICK_NAME.equals(id)){
			ret = true;
		}else if( GRADUATE.equals(id)){
			ret = true;
		}else if( REPEAT_YEAR.equals(id)){
			ret = true;
		}else if( GIVEUP.equals(id)){
			ret = true;
		}

		return ret;
	}
}
