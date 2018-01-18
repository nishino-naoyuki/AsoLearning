/**
 *
 */
package jp.ac.asojuku.asolearning.json;

/**
 * @author nishino
 *
 */
public class JudgeResultJson {

	public String errorMsg = "";
	public float score = 0;
	public boolean allOK = false;

	@Override
    public String toString() {
        return "JudgeResultJson [errorMsg=" + errorMsg + ", score=" + score + "]";
    }

}
