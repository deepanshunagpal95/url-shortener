package com.airtel.urlshortener.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
/**
 * 
 * @author deepanshunagpal
 * @since 22-06-2021
 *
 */
public class DateUtils {

	public static SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

	public static Date getBackDate(int numberOfDays) {
		LocalDate today = LocalDate.now();
		LocalDate dateToApply = today.minusDays(numberOfDays);
		return Date.from(dateToApply.atStartOfDay(ZoneId.systemDefault()).toInstant());
	}

	public static Date getBackDate(Date date, int numberOfDays) {
		LocalDate today = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate dateToApply = today.minusDays(numberOfDays);
		return Date.from(dateToApply.atStartOfDay(ZoneId.systemDefault()).toInstant());
	}

	public static Date getFutureDate(int numberOfDays) {
		LocalDate today = LocalDate.now();
		LocalDate dateToApply = today.plusDays(numberOfDays);
		return Date.from(dateToApply.atStartOfDay(ZoneId.systemDefault()).toInstant());
	}

	public static Date getFutureDateWithMinutes(Date date, int minutes) {
		long timeStamp = date.getTime();
		timeStamp = timeStamp + (minutes * 60000);
		return new Date(timeStamp);
	}

	public static String getDateString(Date date) {
		return formatter.format(date);
	}

	public static Date getDate(String dateString) {
		try {
			return formatter.parse(dateString);
		} catch (ParseException e) {
			return null;
		}
	}

	public static String formatDate(Date date, String format) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		return dateFormat.format(date);
	}

	public static Date getFormattedDate(String dateString, String format) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		try {
			return dateFormat.parse(dateString);
		} catch (ParseException e) {
			return null;
		}
	}

}
