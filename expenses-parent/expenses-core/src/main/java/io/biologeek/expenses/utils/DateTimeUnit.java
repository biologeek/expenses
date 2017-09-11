package io.biologeek.expenses.utils;

import java.util.Calendar;
import java.util.Date;


/**
 * Enum containing all common date and time units
 */
public enum DateTimeUnit {

	NANOSECONDS, MILLISECONDS, SECONDS, MINUTES, HOURS, DAYS, MONTHS, YEARS, ERA;

	public static int getCalendarUnit(DateTimeUnit unit) throws UnsupportedOperationException {
		switch (unit) {
		case NANOSECONDS:
			throw new UnsupportedOperationException("interval.invalid");
		case MILLISECONDS:
			return Calendar.MILLISECOND;
		case SECONDS:
			return Calendar.SECOND;
		case MINUTES:
			return Calendar.MINUTE;
		case HOURS:
			return Calendar.HOUR;
		case DAYS:
			return Calendar.DAY_OF_MONTH;
		case MONTHS:
			return Calendar.MONTH;
		case YEARS:
			return Calendar.YEAR;
		case ERA:
			return Calendar.ERA;
		default:
			return Calendar.DAY_OF_MONTH;
		}
	}
}
