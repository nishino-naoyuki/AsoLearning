/**
 *
 */
package jp.ac.asojuku.asolearning.judge;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jp.ac.asojuku.asolearning.config.AppSettingProperty;
import jp.ac.asojuku.asolearning.exception.AsoLearningSystemErrException;

/**
 * 判定処理を行うFactoryクラス
 * @author nishino
 *
 */
public class JudgeFactory {

	/**
	 * 設定ファイルより暮らす姪を取得し、判定クラスのインスタンスを帰す
	 * @return
	 * @throws AsoLearningSystemErrException
	 */
	public static Judge getInstance() throws AsoLearningSystemErrException{
		Logger logger = LoggerFactory.getLogger(JudgeFactory.class);

		String className = AppSettingProperty.getInstance().getJudgeClass();

		Class cls;
		Judge judge = null;
		try {
			cls = Class.forName(className);
			judge = (Judge)cls.newInstance();

		} catch (ClassNotFoundException e) {
			logger.error("指定されたクラスが見つかりません", e);
			throw new AsoLearningSystemErrException(e);

		} catch (InstantiationException e) {
			logger.error("インスタンスが生成できません", e);
			throw new AsoLearningSystemErrException(e);

		} catch (IllegalAccessException e) {
			logger.error("IllegalAccessException", e);
			throw new AsoLearningSystemErrException(e);
		}

		return judge;
	}
}
