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
	private static final String ANSWER_DIR = "answer.directory";
	private static final String INPUT_DIR = "input.directory";
	private static final String TEMP_DIR = "temp.directory";
	private static final String DISPLAY_PREFIX = "display";
	private static final String PASSWORD_POLICY = "password.policy";
	private static final String NICKNAME_KY = "nickname.enc.ky";
	private static final String NICKNAME_IV = "nickname.enc.iv";
	private static final String PWD_EXPIRY = "password.expiry";
	private static final String PWD_LOCK_LIMIT = "pwd.lock.limit";
	private static final String CSV_FILE_ENCODE = "csv.file.encode";
	private static final String CSV_DIR = "csv.directory";
	private static final String RANKING_EASY = "ranking.offset.easy";
	private static final String RANKING_NORAML = "ranking.offset.normal";
	private static final String RANKING_DIFFICAL = "ranking.offset.diffical";
	private static final String DB_STRING = "db.connect.string";
	private static final String JSP_DIR = "jsp.directory";

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


	public String getPwdLockLmit(){
		return getProperty(PWD_LOCK_LIMIT);
	}
	/**
	 * パスワード有効日数の取得
	 * @return
	 */
	public String getPwdExpiry(){
		return getProperty(PWD_EXPIRY);
	}
	/**
	 * パスワードソルトの取得
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
	/**
	 * 答えがあるフォルダ
	 * @return
	 */
	public String getAnswerDirectory(){
		return getProperty(ANSWER_DIR);
	}
	/**
	 * 答えがあるフォルダ
	 * @return
	 */
	public String getInputDirectory(){
		return getProperty(INPUT_DIR);
	}
	/**
	 * テンポラリフォルダ
	 * @return
	 */
	public String getTempDirectory(){
		return getProperty(TEMP_DIR);
	}
	/**
	 * 画面のアクセス可能ロール文字列を取得する
	 * @param dispId
	 * @return
	 */
	public String getDisplayPermit(String dispId){
		return getProperty(DISPLAY_PREFIX+dispId);

	}
	/**
	 * パスワードポリシーの取得
	 * @return
	 */
	public String getPasswordPolicy(){
		return getProperty(PASSWORD_POLICY);
	}
	/**
	 * ニックネームのKey
	 * @return
	 */
	public String getNickNameKy(){
		return getProperty(NICKNAME_KY);
	}
	/**
	 * ニックネームのIV（初期ペクトる）
	 * @return
	 */
	public String getNickNameIv(){
		return getProperty(NICKNAME_IV);
	}
	/**
	 * CSVファイルの文字コード
	 * @return
	 */
	public String getCsvFileEncode(){
		return getProperty(CSV_FILE_ENCODE);
	}
	/**
	 * CSVファイルの出力フォルダ
	 * @return
	 */
	public String getCsvDir(){
		return getProperty(CSV_DIR);
	}
	/**
	 * 難易度「簡単」の点数ゲタ
	 * @return
	 */
	public float getRankingEasy(){
		return getProperty(RANKING_EASY,1.0f);
	}
	/**
	 * 難易度「普通」の点数ゲタ
	 * @return
	 */
	public float getRankingNormal(){
		return getProperty(RANKING_NORAML,1.0f);
	}
	/**
	 * 難易度「難しい」の点数ゲタ
	 * @return
	 */
	public float getRankingDiffical(){
		return getProperty(RANKING_DIFFICAL,1.0f);
	}
	/**
	 * CDB接続文字列
	 * @return
	 */
	public String getDBString(){
		return getProperty(DB_STRING);
	}
	/**
	 * JSPのフォルダ取得
	 * @return
	 */
	public String getJspDirString(){
		return getProperty(JSP_DIR);
	}
}
