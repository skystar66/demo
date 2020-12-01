package com.liveme.demo.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeFormat {
	public static final String TIME_FORMAT_H = "yyyy-MM-dd HH:mm:ss";
	public static final String TIME_FORMAT_D = "yyyyMMddHHmmss";
	public static final String DATE_FORMAT = "yyyy-MM-dd";
	public static final String YEAR = "yyyy";
	public static final String MONTH = "MM";
	public static final String DAY = "dd";
	public static final String HOUR = "HH";
	public static final String MINUTE = "mm";
	public static final String SECOND = "ss";
	public static final String YYYYMMDD = "yyyyMMdd";

	public static String getFormatDate(Date date, String format) {
		String dateStr = null;
		try {
			if (date != null) {
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
				dateStr = simpleDateFormat.format(date);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return dateStr;
	}

	public static Date convertDate(String dateStr, String format) {
		Date date = null;
		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
			date = simpleDateFormat.parse(dateStr);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return date;
	}

//	public static boolean getDayDiffFromToday(Date dt, int type) {
//		Date today = new Date();
//		today.setHours(23);
//		today.setMinutes(59);
//		today.setSeconds(59);
//
//		long diff = today.getTime() - dt.getTime();
//		if (diff < 0L)
//			diff = 0L;
//		long days = diff / 86400000L;
//		if ((type == 0) && (days == 0L))
//			return true;
//		if ((type == 1) && (days > 0L) && (days <= 7L)) {
//			return true;
//		}
//		return (type == 2) && (days > 7L);
//	}

	/**
	 * Java 比较时间大小
	 */
	public static int compDate(String date1, String date2) {
		// System.out.println(date1 + "  " + date2);

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		try {
			c1.setTime(df.parse(date1));
			c2.setTime(df.parse(date2));
		} catch (ParseException e) {
			System.err.println("格式不正确");
		}
		int result = c1.compareTo(c2);
		/**
		 * if (result == 0) System.out.println("c1相等c2"); else if (result < 0)
		 * System.out.println("c1小于c2"); else System.out.println("c1大于c2");
		 **/
		return result;
	}

	/**
	 * 返回多少小时前的日期
	 * 
	 * @param hour
	 * @return
	 */
	public static String beforeHourToNowDate(int hour) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) - hour);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.format(calendar.getTime());
	}

	/**
	 * 查询几分钟前的时间
	 * 返回的是字符串型的时间，输入的是String day, int x
	 * @param day
	 * @param x
	 * @return
	 */
	public static String addDateMinut(String day, int x){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 24小时制
		// 引号里面个格式也可以是 HH:mm:ss或者HH:mm等等，很随意的，不过在主函数调用时，要和输入的变量day格式一致
		Date date = null;
		try {
			date = format.parse(day);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		if (date == null)
			return "";
		//System.out.println("front:" + format.format(date)); // 显示输入的日期
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.SECOND, x);// 24小时制
		date = cal.getTime();
		//System.out.println("after:" + format.format(date)); // 显示更新后的日期
		cal = null;
		return format.format(date);
	}
	
	/**
	 * 返回几分钟后的时间
	 * @param minute
	 */
	public static Date addMinute(int minute) {
		long curren = System.currentTimeMillis();
		curren += minute * 60 * 1000;

		Date da = new Date(curren);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		Date d = dateFormat.format(da);
//		System.out.println();
		return da;
	}
	
	/**
	 * 返回几分钟后的Long时间
	 * @param minute
	 */
	public static Long addMinuteLongTime(int minute) {
		long curren = System.currentTimeMillis();
		curren += minute * 60 * 1000;
		return curren;
	}
	
	// long转换为Date类型
	 	// currentTime要转换的long类型的时间
	 	// formatType要转换的时间格式yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日 HH时mm分ss秒
	 	public static Date longToDate(long currentTime, String formatType) throws ParseException {
	 		Date dateOld = new Date(currentTime); // 根据long类型的毫秒数生命一个date类型的时间
	 		String sDateTime = dateToString(dateOld, formatType); // 把date类型的时间转换为string
	 		Date date = stringToDate(sDateTime, formatType); // 把String类型转换为Date类型
	 		return date;
	 	}
	 	
	 	// string类型转换为date类型
	 	// strTime要转换的string类型的时间，formatType要转换的格式yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日
	 	// HH时mm分ss秒，
	 	// strTime的时间格式必须要与formatType的时间格式相同
	 	public static Date stringToDate(String strTime, String formatType) throws ParseException {
	 		SimpleDateFormat formatter = new SimpleDateFormat(formatType);
	 		Date date = null;
	 		date = formatter.parse(strTime);
	 		return date;
	 	}

    	// date类型转换为String类型
 	// formatType格式为yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日 HH时mm分ss秒
 	// data Date类型的时间
 	public static String dateToString(Date data, String formatType) {
 		return new SimpleDateFormat(formatType).format(data);
 	}
 	
	public static String formatTime(Long ms) {
		Integer ss = 1000;
		Integer mi = ss * 60;
		Integer hh = mi * 60;
		Integer dd = hh * 24;

		Long day = ms / dd;
		Long hour = (ms - day * dd) / hh;
		Long minute = (ms - day * dd - hour * hh) / mi;
		Long second = (ms - day * dd - hour * hh - minute * mi) / ss;
		Long milliSecond = ms - day * dd - hour * hh - minute * mi - second * ss;

		StringBuffer sb = new StringBuffer();
		if (day > 0) {
			sb.append(day + "天");
		}
		if (hour > 0) {
			sb.append(hour + "小时");
		}
		if (minute > 0) {
			sb.append(minute + "分");
		}
		if (second > 0) {
			sb.append(second + "秒");
		}
		if (milliSecond > 0) {
			sb.append(milliSecond + "毫秒");
		}
		return sb.toString();
	}

	public static void main(String[] args) throws ParseException {
		// System.out.println(compDate("2008-01-25 09:12:09", "2008-01-29 09:12:11"));
		String front4MinTime = addDateMinut(new Date().toLocaleString(), -4);
		Date date = convertDate(front4MinTime, TIME_FORMAT_H);
		System.out.println(date.getTime() + "	" + System.currentTimeMillis());
		System.out.println(addMinute(5));
		long now = System.currentTimeMillis();
		Date n = longToDate(now, TIME_FORMAT_H);
		System.out.println(n);
	}
}