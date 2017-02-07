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
	private static final String JUDGE_CLASS = "judge.class";
	private static final String UPLOAD_DIR = "upload.directory";
	private static final String SHELL_PATH = "shell.path";
	private static final String RESULT_DIR = "result.directory";

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
	/**
	 * 判定クラスの取得
	 * @return
	 */
	public String getJudgeClass(){
		return getProperty(JUDGE_CLASS);
	}
	/**
	 * アップロードディレクトリ
	 * @return
	 */
	public String getUploadDirectory(){
		return getProperty(UPLOAD_DIR);
	}
	/**
	 * シェルの実行パス
	 * @return
	 */
	public String getShellPath(){
		return getProperty(SHELL_PATH);
	}
	/**
	 * 結果の出力フォルダ
	 * @return
	 */
	public String getResultDirectory(){
		return getProperty(RESULT_DIR);
	}
}
