package br.com.telematica.siloapi.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Utils {

	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static SimpleDateFormat sdfbase = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

	public static String sdfBaseDateforString() {
		var date = new Date();
		return sdfbase.format(date);
	}

	public static String sdfDateforString(Date date) {
		return sdf.format(date);
	}

	public static Date sdfStringforDate(String date) throws Exception {
		try {
			return sdf.parse(date);
		} catch (Exception e) {
			throw new Exception("Error parsing date: " + e.getMessage());
		}
	}

	public static Date sdfDateTimeZone(String msidth) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
		sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

		try {
			Date date = sdf.parse(msidth);
			System.out.println("Date: " + date);
			return date;
		} catch (ParseException e) {
			throw new ParseException("Error parsing date: " + e.getMessage(), 0);
		}
	}

}
