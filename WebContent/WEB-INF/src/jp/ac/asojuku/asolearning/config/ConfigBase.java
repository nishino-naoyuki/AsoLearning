/**
 *
 */
package jp.ac.asojuku.asolearning.config;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import jp.ac.asojuku.asolearning.exception.AsoLearningSystemErrException;

/**
 * 設定ファイルベースクラス
 * @author nishino
 *
 */
public abstract class ConfigBase {

	private Properties config = new Properties();



	public ConfigBase() throws AsoLearningSystemErrException{
		InputStream inputStream;
		try {
			inputStream = this.getClass().getClassLoader().getResourceAsStream(getConfigName());
			config.load(inputStream);

		} catch (FileNotFoundException e) {
			// システムエラー
			throw new AsoLearningSystemErrException(e);
		} catch (IOException e) {
			// システムエラー
			throw new AsoLearningSystemErrException(e);
		}

	}

	/**
	 * コンフィグファイルから情報取得する
	 * @return
	 */
	public String getProperty(String paramName){

		return config.getProperty(paramName);
	}


	/**
	 * コンフィグファイルから情報取得する
	 * @return
	 */
	public Float getProperty(String paramName,Float defaultVal){
		Float retVal = defaultVal;

		try{
			retVal = Float.parseFloat(config.getProperty(paramName));
		}catch(NumberFormatException e){
			retVal = defaultVal;
		}

		return retVal;
	}


	abstract protected String getConfigName();
}
