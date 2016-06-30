package com.ddyyyg.shop.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.SimpleTimeZone;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by QuestZhang on 16/6/7.
 */

public class TimeUtil {

	// 时间格式模板
	/** yyyy-MM-dd */
	public static final String TIME_FORMAT_ONE = "yyyy-MM-dd";
	/** yyyy-MM-dd HH:mm */
	public static final String TIME_FORMAT_TWO = "yyyy-MM-dd HH:mm";
	/** yyyy-MM-dd HH:mmZ */
	public static final String TIME_FORMAT_THREE = "yyyy-MM-dd HH:mmZ";
	/** yyyy-MM-dd HH:mm:ss */
	public static final String TIME_FORMAT_FOUR = "yyyy-MM-dd HH:mm:ss";
	/** yyyy-MM-dd HH:mm:ss.SSSZ */
	public static final String TIME_FORMAT_FIVE = "yyyy-MM-dd HH:mm:ss.SSSZ";
	/** yyyy-MM-dd'T'HH:mm:ss.SSSZ */
	public static final String TIME_FORMAT_SIX = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
	/** HH:mm:ss */
	public static final String TIME_FORMAT_SEVEN = "HH:mm:ss";
	/** HH:mm:ss.SS */
	public static final String TIME_FORMAT_EIGHT = "HH:mm:ss.SS";
	/** yyyy.MM.dd */
	public static final String TIME_FORMAT_9 = "yyyy.MM.dd";
	/** MM月dd日 */
	public static final String TIME_FORMAT_10 = "MM月dd日";
	public static final String TIME_FORMAT_11 = "MM-dd HH:mm";
	public static final String TIME_FORMAT_12 = "yyMM";
	public static final String TIME_FORMAT_13 = "yyyyMMdd-HH";
	/** HH:mm */
	public static final String TIME_FORMAT_14 = "HH:mm";
	public static final String TIME_FORMAT_15 = "MM-dd";
	public static final String TIME_FORMAT_16 = "yy-MM-dd";
	public static final String TIME_FORMAT_17="dd/MM E HH:mm";

	/** yyyy-MM-dd'T'HH:mm:ss.SSSZ */
	// 时间常量
	private static final int SECOUND_OF_HOUR = 3600;
	private static final int SECOUND_OF_MIN = 60;
	private static final String TIME_FORMAT_TRADE_NO = "yyyyMMddHHmmss";

	/**
	 * 使用时间作为当前订单
	 */
	public static String getOutTradeNo() {
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat(TIME_FORMAT_TRADE_NO,
				Locale.SIMPLIFIED_CHINESE);
		return dateFormat.format(date);
	}

	/**
	 * 根据时间格式获得当前时间
	 */
	public static String getCurrentTime(String formater) {
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat(formater,
				Locale.SIMPLIFIED_CHINESE);
		return dateFormat.format(date);
	}

	/** 格式化时间 */
	public static String formatTime(long time, String format) {
		return new SimpleDateFormat(format).format(new Date(time));
	}

	/** 判断是否是合法的时间 */
	public static boolean isValidDate(String dateString, String format) {
		return parseTime(dateString, format) > -1;
	}

	/** 日期转换 */
	public static long parseTime(String dateString, String format) {
		if (dateString == null || dateString.length() == 0) {
			return -1;
		}
		try {
			return new SimpleDateFormat(format).parse(dateString).getTime();
		} catch (ParseException e) {

		}
		return -1;
	}

	public static int getDaysBetween(String date1, String date2, String format) {
		return getDaysBetween(parseTime(date1, format),
				parseTime(date2, format));
	}

	public static int getDaysBetween(long date1, long date2) {
		Calendar c1 = Calendar.getInstance();
		c1.setTimeInMillis(date1);
		c1.set(Calendar.HOUR_OF_DAY, 0);
		c1.set(Calendar.MINUTE, 0);
		c1.set(Calendar.SECOND, 0);
		c1.set(Calendar.MILLISECOND, 0);

		Calendar c2 = Calendar.getInstance();
		c2.setTimeInMillis(date2);
		c2.set(Calendar.HOUR_OF_DAY, 0);
		c2.set(Calendar.MINUTE, 0);
		c2.set(Calendar.SECOND, 0);
		c2.set(Calendar.MILLISECOND, 0);

		return (int) ((c2.getTimeInMillis() - c1.getTimeInMillis()) / (24 * 3600 * 1000));
	}

	/**
	 * Unix时间戳转换成日期
	 */
	public static String TimeStamp2Date(String timestampString, String formater) {
		Long timestamp = Long.parseLong(timestampString) * 1000;
		String date = new SimpleDateFormat(formater, Locale.SIMPLIFIED_CHINESE)
				.format(new Date(timestamp));
		return date;
	}

	/**
	 * Unix 将当前日期转换时间戳
	 * @return
	 */
	public static String Date2TimeStamp(){
		long ts = 0;
		try {
			ts = new java.text.SimpleDateFormat(TIME_FORMAT_TRADE_NO).parse(getBeijingTime()).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return String.valueOf(ts/1000);
	}

	public static long getTodayTimeMillis() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTimeInMillis();
	}

	public static String getTimeByLong4Msg(long tLong) {
		String strDate = "";
		tLong = tLong * 1000;
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(tLong);
		cal.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm yyyy-MM-dd");
		// strDate = cal.getTime().toLocaleString();
		strDate = sdf.format(cal.getTime());
		return strDate;
	}

	/**
	 * 获取默认格式的时间
	 * 
	 * @return "MM-dd HH:mm"
	 */
	public static String getDefaultTime() {
		String strDate = "";
		long currentTime = System.currentTimeMillis();
		currentTime = currentTime * 1000;
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(currentTime);
		cal.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm");
		// strDate = cal.getTime().toLocaleString();
		strDate = sdf.format(cal.getTime());
		return strDate;
	}

	/**
	 * 用户自己设置指定时间格式的日期
	 * 
	 * @param format
	 *            日期格式
	 * @return 日期
	 */
	public static String getTimeByLong(String format) {
		String strDate = "";
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(System.currentTimeMillis());
		cal.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		strDate = sdf.format(cal.getTime());
		return strDate;
	}

	/**
	 * 去除字符串中的空格、回车、换行符、制表符
	 */
	public static String replaceBlank(String str) {
		String dest = "";
		if (str != null) {
			Pattern p = Pattern.compile("\\s*|\t|\r|\n");
			Matcher m = p.matcher(str);
			dest = m.replaceAll("");
		}
		return dest;
	}

	/**
	 * String转long
	 */
	public static long parseLong(String str, long defaultValue) {
		try {
			return Long.parseLong(str);
		} catch (Exception e) {
			return defaultValue;
		}
	}

	/**
	 * 判断字符串是否有效
	 * 
	 * @deprecated
	 */
	public static boolean isValidate(String str) {
		return str != null && str.length() > 0;
	}


	/**
	 * 返回指定日期距离19700101的时间差（毫秒）
	 */
	public static String get15TimeStap() {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_MONTH, -16);
		return Long.toString(c.getTimeInMillis());
	}

	/**
	 * 给定秒钟时间转成小时，分钟，秒
	 * 
	 * @param
	 * @return
	 */
	public static String durationTime(String secStr) {
		if (secStr != null && secStr != "") {
			try {
				int second = Integer.parseInt(secStr);
				int h = 0;
				int d = 0;
				int s = 0;
				int temp = second % 3600;
				if (second > 3600) {
					h = second / 3600;
					if (temp != 0) {
						if (temp > 60) {
							d = temp / 60;
							if (temp % 60 != 0) {
								s = temp % 60;
							}
						} else {
							s = temp;
						}
					}
				} else {
					d = second / 60;
					if (second % 60 != 0) {
						s = second % 60;
					}
				}
				if (h == 0 && d != 0) {
					return d + "分" + s + "秒";
				} else if (h == 0 && d == 0) {
					return s + "秒";
				}
				return h + "时" + d + "分" + s + "秒";
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		return "0秒";
	}

	/**
	 * 取北京时间
	 * @return
	 */
	public static String getBeijingTime(){
		return getFormatedDateString(8);
	}

	/**
	 * 取班加罗尔时间
	 * @return
	 */
	public static String getBangaloreTime(){
		return getFormatedDateString(5.5f);
	}

	/**
	 * 取纽约时间
	 * @return
	 */
	public static String getNewyorkTime(){
		return getFormatedDateString(-5);
	}

	/**
	 * 此函数非原创，从网上搜索而来，timeZoneOffset原为int类型，为班加罗尔调整成float类型
	 * timeZoneOffset表示时区，如中国一般使用东八区，因此timeZoneOffset就是8
	 * @param timeZoneOffset
	 * @return
	 */
	public static String getFormatedDateString(float timeZoneOffset){
		if (timeZoneOffset > 13 || timeZoneOffset < -12) {
			timeZoneOffset = 0;
		}

		int newTime=(int)(timeZoneOffset * 60 * 60 * 1000);
		TimeZone timeZone;
		String[] ids = TimeZone.getAvailableIDs(newTime);
		if (ids.length == 0) {
			timeZone = TimeZone.getDefault();
		} else {
			timeZone = new SimpleTimeZone(newTime, ids[0]);
		}

		SimpleDateFormat sdf = new SimpleDateFormat(TIME_FORMAT_TRADE_NO);
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		sdf.setTimeZone(timeZone);
		return sdf.format(new Date());
	}
}
