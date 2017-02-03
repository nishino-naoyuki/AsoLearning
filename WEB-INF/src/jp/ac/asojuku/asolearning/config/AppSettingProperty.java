/**
 *
 */
package jp.ac.asojuku.asolearning.config;

import jp.ac.asojuku.asolearning.exception.AsoLearningSystemErrException;

/**
 * アプリケーションの
 * @author nishino
 *
 */
public class AppSettingProperty extends ConfigBase {

	//シングルトン
	private static AppSettingProperty prop = null;
	private static final String CONFIG_NAME = "app.properties";

	//設定値
	private final String ASP_PWD_SALT = "pwd.hash.salt";	//パスワードソルト

	public AppSettingProperty() throws AsoLearningSystemErrException {
		super();
		// TODO 自動生成されたコンストラクター・スタブ
	}

	/**
	 * インスタンスの取得
	 * @return
	 * @throws BookStoreSystemErrorException
	 */
	public static AppSettingProperty getInstance() throws AsoLearningSystemErrException{

		if( prop == null ){
			prop = new AppSettingProperty();
		}

		return prop;
	}

	//　コンフィグファイルの名前を返す
	protected String getConfigName(){ return CONFIG_NAME; }


	/**
	 * パスワードするとの取得
	 * @return
	 */
	public String getPwdHashSalt(){
		return getProperty(ASP_PWD_SALT);
	}
}
