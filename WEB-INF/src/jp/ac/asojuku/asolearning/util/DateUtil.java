package jp.ac.asojuku.asolearning.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

	public static String formattedDate(Date date, String timeFormat) {
		if( date == null ){
			return "";
		}
        return new SimpleDateFormat(timeFormat).format(date);
    }

	public static Date plusDay(Date srcDate,int day){
		Calendar cal = Calendar.getInstance();

		cal.setTime(srcDate);

		cal.add(Calendar.DATE, day);

		return cal.getTime();
	}

}
