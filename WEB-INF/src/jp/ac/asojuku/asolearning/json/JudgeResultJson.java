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
	public int score = 0;

	@Override
    public String toString() {
        return "JudgeResultJson [errorMsg=" + errorMsg + ", score=" + score + "]";
    }

}
