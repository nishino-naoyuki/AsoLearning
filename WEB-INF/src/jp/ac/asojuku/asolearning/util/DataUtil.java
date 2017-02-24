package jp.ac.asojuku.asolearning.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DataUtil {

	public static String formattedDate(Date date, String timeFormat) {
		if( date == null ){
			return null;
		}
        return new SimpleDateFormat(timeFormat).format(date);
    }
}
