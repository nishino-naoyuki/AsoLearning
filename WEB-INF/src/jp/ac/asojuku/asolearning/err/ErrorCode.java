/**
 *
 */
package jp.ac.asojuku.asolearning.err;

/**
 * エラーコード
 * @author nishino
 *
 */
public enum ErrorCode {

	SUCCESS("0000") ,
	//ログイン(00xx)
	ERR_LOGIN("0001"),
	//会員登録(01xx)
	ERR_MEMBER_ENTRY_MAILADDRESS("0101"),
	ERR_MEMBER_ENTRY_NAME("0102"),
	ERR_MEMBER_ENTRY_ADDRESS("0103"),
	ERR_MEMBER_ENTRY_TELNUMBER("0104"),
	ERR_MEMBER_ENTRY_MAILADDRESS_ISNEED("0105"),
	ERR_MEMBER_ENTRY_NAME_ISNEED("0106"),
	ERR_MEMBER_ENTRY_ADDRESS_ISNEED("0107"),
	ERR_MEMBER_ENTRY_TELNUMBER_ISNEED("0108"),
	ERR_MEMBER_ENTRY_PASSWORD_NOTMATCH("0109"),
	ERR_MEMBER_ENTRY_PASSWORD_LENGTH("0110"),
	ERR_MEMBER_ENTRY_DUPLICATE_MEIL("0111"),

	//その他のエラー
	ERR_SESSION_INVLIDATE("9901"),
	ERR_SYSTEM_ERROR("9999"),
	;

	private String code;

	private ErrorCode(String code){ this.code = code; }

	public String getCode(){ return code; }

}
