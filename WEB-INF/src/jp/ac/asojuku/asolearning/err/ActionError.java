package jp.ac.asojuku.asolearning.err;

public class ActionError {

	private ErrorCode code;
	private String message;

	public ActionError(ErrorCode code ,String message){
		this.code = code;
		this.message = message;
	}
	/**
	 * @return code
	 */
	public ErrorCode getCode() {
		return code;
	}
	/**
	 * @param code セットする code
	 */
	public void setCode(ErrorCode code) {
		this.code = code;
	}
	/**
	 * @return message
	 */
	public String getMessage() {
		return message;
	}
	/**
	 * @param message セットする message
	 */
	public void setMessage(String message) {
		this.message = message;
	}


}
