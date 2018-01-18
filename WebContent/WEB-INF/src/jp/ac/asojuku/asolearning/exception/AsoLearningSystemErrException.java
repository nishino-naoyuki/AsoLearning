/**
 *
 */
package jp.ac.asojuku.asolearning.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author nishino
 *
 */
public class AsoLearningSystemErrException extends Exception {
	Logger logger = LoggerFactory.getLogger(AsoLearningSystemErrException.class);

	public AsoLearningSystemErrException(String errMsg){
		logger.error("致命的エラー：",errMsg);
	}
	public AsoLearningSystemErrException(Exception e) {
		logger.error("致命的エラー：",e);
	}

}
