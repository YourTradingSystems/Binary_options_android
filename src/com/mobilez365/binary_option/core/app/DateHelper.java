package com.mobilez365.binary_option.core.app;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * User: ZOG
 * Date: 06.05.14
 * Time: 10:39
 */
public abstract class DateHelper {
	//	f - format
	private static final String fTimeChart		= "HH:mm:ss";
	private static final String fTickDate		= "yyyy-MM-dd'T'HH:mm:ss'Z'";

	/**
	 * Formats time in milliseconds to string representation.
	 * @param _time time in milliseconds.
	 * @return string time in format templateTimeChart.
	 */
	public static final String getTimeString(final long _time) {

		final Date date = new Date(_time);

		return new SimpleDateFormat(fTimeChart).format(date);
	}

	/**
	 * Returns millis from tick date string. Tick date must be in format fTickDate.
	 * @param _tickDate tick date string.
	 * @return time in millis.
	 * @throws ParseException
	 */
	public static final long getMillisFromTickDate(final String _tickDate) throws ParseException {

		final SimpleDateFormat sdf = new SimpleDateFormat(fTickDate);
		sdf.setTimeZone(TimeZone.getTimeZone("GMT-1"));
		final Date date = sdf.parse(_tickDate);

		return date.getTime() / 1000;
	}
}
