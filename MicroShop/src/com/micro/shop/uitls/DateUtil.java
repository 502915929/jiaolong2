package com.micro.shop.uitls;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class DateUtil {
	protected static final String TAG = "DateUtil";

	/**
	 * 格式化日期，如果在今日内就只保留时分秒，否则保留完整日期
	 *
	 * @param dateString
	 * @return
	 */
	public static String formatDateString(String dateString) {
		String hourString = null;
		if (isInToday(dateString)) {
			Pattern p = Pattern.compile("([01]?[0-9]|2[0-3]):[0-5][0-9]");
			Matcher m = p.matcher(dateString);
			if (m.find()) {
				hourString = m.group();
			}
		} else {
			hourString = dateString;
		}
		return hourString;
	}

	public static Calendar dateFromString(String dateString) {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
				Locale.getDefault());
		try {
			Date date = sdf.parse(dateString);
			calendar.setTime(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return calendar;
	}

	public static String toYMDString(Calendar calendar) {
		return calendar.get(Calendar.YEAR) + "-"
				+ (calendar.get(Calendar.MONTH) + 1) + "-"
				+ calendar.get(Calendar.DAY_OF_MONTH);
	}

	public static boolean isInToday(String dateString) {
		Calendar cn = Calendar.getInstance();
		Calendar cYesterday = Calendar.getInstance();
		Calendar cTomorrow = Calendar.getInstance();
		Calendar cToday = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
				Locale.getDefault());
		try {
			Date date = sdf.parse(dateString);
			cn.setTime(date);
			cYesterday.set(cToday.get(Calendar.YEAR),
					cToday.get(Calendar.MONTH),
					cToday.get(Calendar.DAY_OF_MONTH) - 1);
			cTomorrow.set(cToday.get(Calendar.YEAR),
					cToday.get(Calendar.MONTH),
					cToday.get(Calendar.DAY_OF_MONTH) + 1);
			if (cTomorrow.after(cn) && cYesterday.before(cn)) {
				return true;
			}
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}

	public static long getCurrentTimeInMillis() {
		return Calendar.getInstance().getTimeInMillis();
	}

	public static String getCurrentFullDate() {
		Calendar calendar = Calendar.getInstance();
		Date date = calendar.getTime();
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
				.format(date);
	}
}
