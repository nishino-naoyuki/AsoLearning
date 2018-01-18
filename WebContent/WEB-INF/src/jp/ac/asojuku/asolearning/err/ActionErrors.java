/**
 *
 */
package jp.ac.asojuku.asolearning.err;

import java.util.ArrayList;
import java.util.List;

import jp.ac.asojuku.asolearning.config.MessageProperty;
import jp.ac.asojuku.asolearning.exception.AsoLearningSystemErrException;

/**
 * エラー保持クラス
 * @author nishino
 *
 */
public class ActionErrors {

	private List<ActionError> errList;

	public ActionErrors(){
		errList = new ArrayList<ActionError>();
	}

	public void add(ActionError errObj){

		errList.add(errObj);
	}

	public void add(ErrorCode code ,String message){

		errList.add( new ActionError(code,message));
	}


	public void add(ErrorCode code ) throws AsoLearningSystemErrException{

		String errMsg = MessageProperty.getInstance().getErrorMsgFromErrCode(code);

		errList.add( new ActionError(code,errMsg));
	}

	public List<ActionError> getList(){
		return errList;
	}

	/**
	 * エラーがあるかどうか
	 * @return
	 */
	public boolean isHasErr(){
		return ( errList.size() == 0 ? false:true );
	}
}
