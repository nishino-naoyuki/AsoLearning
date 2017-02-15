/**
 *
 */
package jp.ac.asojuku.asolearning.util;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.commons.lang3.StringUtils;

/**
 * sql.dateのユーティリティ
 *
 * @author nishino
 *
 */
public class SqlDateUtil {

	/**
	 * 文字列からSQLのDate型への変換
	 *
	 * @param dateString
	 * @param format
	 * @return
	 * @throws ParseException
	 */
	public static Date getDateFrom(String dateString,String format) throws ParseException{

		if( StringUtils.isEmpty(dateString)){
			return null;
		}

		SimpleDateFormat sdf = new SimpleDateFormat(format);
		java.util.Date formatDate = sdf.parse(dateString);// 変換処理

		return new Date(formatDate.getTime());
	}
}
