package io.biologeek.expenses.utils;

import java.util.Calendar;
import java.util.Date;

public class DateUtils {

	public static boolean areSameDate(Date date1, Date date2) {
		Calendar cal = Calendar.getInstance();

		cal.setTime(date1);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(date2);
		return cal.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)
				&& cal.get(Calendar.MONTH) == cal2.get(Calendar.MONTH)
				&& cal.get(Calendar.DAY_OF_MONTH) == cal2
						.get(Calendar.DAY_OF_MONTH);
	}
	public static boolean areSameMonth(Date date1, Date date2) {
		Calendar cal = Calendar.getInstance();

		cal.setTime(date1);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(date2);
		return cal.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)
				&& cal.get(Calendar.MONTH) == cal2.get(Calendar.MONTH);
	}

	public static boolean areSameYear(Date date1, Date date2) {
		Calendar cal = Calendar.getInstance();

		cal.setTime(date1);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(date2);
		return cal.get(Calendar.YEAR) == cal2.get(Calendar.YEAR);
	}
	
	public static Date dateFromArgs(int year, int month, int day){
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.DAY_OF_MONTH, day);
		return cal.getTime();
	}

}
