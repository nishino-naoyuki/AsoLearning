/**
 *
 */
package jp.ac.asojuku.asolearning.config;

import jp.ac.asojuku.asolearning.err.ErrorCode;
import jp.ac.asojuku.asolearning.exception.AsoLearningSystemErrException;

/**
 * メッセージプロパティファイルの読み込み
 * @author nishino
 *
 */
public class MessageProperty extends ConfigBase{


	public MessageProperty() throws AsoLearningSystemErrException {
		super();
	}

	//シングルトン
	private static MessageProperty prop = null;
	private static final String CONFIG_NAME = "message.properties";

	/** 設定パラメータ */
	public static final String JUDGE_RET_NOTMATCH = "judge.result.not.match";
	public static final String LOGIN_ERR_LOGINERR = "login.err.loginerr";
	public static final String LOGOUT_MSG = "logout.msg";
	public static final String ERR_PROP_PREFIX = "errmsg";
	public static final String INFO_RECENT_CREATE = "info.recent.create";
	public static final String INFO_RECENT_UPDATE = "info.recent.update";
	public static final String INFO_RECENT_END = "info.recent.end";
	public static final String LOGIN_LOCK = "login.err.lock";

	/**
	 * インスタンスの取得
	 * @return
	 * @throws BookStoreSystemErrorException
	 */
	public static MessageProperty getInstance() throws AsoLearningSystemErrException{

		if( prop == null ){
			prop = new MessageProperty();
		}

		return prop;
	}


	/**
	 * エラーコードよりエラーメッセージを取得する
	 * @param code
	 * @return
	 */
	public String getErrorMsgFromErrCode(ErrorCode code){

		return getProperty(ERR_PROP_PREFIX+code.getCode());
	}

	//　コンフィグファイルの名前を返す
	protected String getConfigName(){ return CONFIG_NAME; }

}
