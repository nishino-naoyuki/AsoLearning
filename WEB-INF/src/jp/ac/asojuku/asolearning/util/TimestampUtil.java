/**
 *
 */
package jp.ac.asojuku.asolearning.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 * TimeStampのユーティリティ
 * @author nishino
 *
 */
public class TimestampUtil {

	public static String formattedTimestamp(Timestamp timestamp, String timeFormat) {
        return new SimpleDateFormat(timeFormat).format(timestamp);
    }

    public static Timestamp current() {
        return new Timestamp(System.currentTimeMillis());
    }

}
