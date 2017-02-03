/**
 *
 */
package jp.ac.asojuku.asolearning.util;

import org.apache.commons.codec.digest.DigestUtils;

import jp.ac.asojuku.asolearning.config.AppSettingProperty;
import jp.ac.asojuku.asolearning.exception.AsoLearningSystemErrException;

/**
 * ハッシュクラス
 * @author nishino
 *
 */
public class Digest {


	public static String create(String msg){
		// 16進数文字列でMD5値を取得する
		String hexString = DigestUtils.md5Hex(msg);

		return hexString;
	}

	/**
	 * パスワードのハッシュ値を算出する
	 * パスワードのハッシュは
	 * 　ソルト+メアド+パスワード+ソルト
	 * で算出する
	 * @param mailadress
	 * @param pwd
	 * @return
	 * @throws AsoLearningSystemErrException
	 */
	public static String createPassword(String mailadress,String pwd) throws AsoLearningSystemErrException{

		//パスワードソルトを取得
		String pwdHashSalt = AppSettingProperty.getInstance().getPwdHashSalt();
		// 16進数文字列でMD5値を取得する
		String hexString = DigestUtils.md5Hex(pwdHashSalt+mailadress+pwd+pwdHashSalt);

		return hexString;
	}
}
