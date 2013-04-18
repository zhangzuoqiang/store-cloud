package com.graby.store.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
	
	public static Date getMoning(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		return c.getTime();
	}

	public static Date getEnd(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, 23);
		c.set(Calendar.MINUTE, 59);
		c.set(Calendar.SECOND, 59);
		return c.getTime();
	}

	public static String format(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int wd = cal.get(Calendar.DAY_OF_WEEK);
		String x = null;
		switch (wd) {
		case 1:x = "星期日";break;
		case 2:x = "星期一";break;
		case 3:x = "星期二";break;
		case 4:x = "星期三";break;
		case 5:x = "星期四";break;
		case 6:x = "星期五";	break;
		case 7:x = "星期六";break;
		}
		SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy年MM月dd日 ");
		String strDate = simpleFormat.format(date) + x;
		return strDate;
	}
}
