/**
 *
 */
package jp.ac.asojuku.asolearning.util;

/**
 * @author nishino
 *
 */
public class HtmlUtil {

	/**
	 * 改行を＜BR>に変換する
	 * @param text
	 * @return
	 */
	public static String nl2be(String text){

		if( text == null ){
			return "";
		}

		text = text.replaceAll("\n", "<BR>");
		text = text.replaceAll("\r\n", "<BR>");
		text = text.replaceAll("\t", "&nbsp&nbsp&nbsp&nbsp");

		return text;
	}
}
