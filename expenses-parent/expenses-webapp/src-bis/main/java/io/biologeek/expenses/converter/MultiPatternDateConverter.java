package io.biologeek.expenses.converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.aspectj.weaver.patterns.IScope;

public class MultiPatternDateConverter {

	static SimpleDateFormat ISO_DATE_SDF = new SimpleDateFormat("yyyy-MM-dd");
	static SimpleDateFormat FR_DATE_SDF = new SimpleDateFormat("dd/MM/yyyy");
	static SimpleDateFormat ISO_DATETIME_SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	static SimpleDateFormat FR_DATETIME_SDF = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	
	/**
	 * Converts string to date using multiple common patterns
	 * 
	 * @param toConvert
	 * @return corresponding {@link Date} object or null
	 */
	public static Date convert(String toConvert) {
		Date result = new Date();
		
		try {
			result = ISO_DATE_SDF.parse(toConvert);
		} catch (ParseException e) {
			try {
				result = FR_DATE_SDF.parse(toConvert);
			} catch (ParseException e1) {
				try {
					result = ISO_DATETIME_SDF.parse(toConvert);
				} catch (ParseException e2) {
					try {
						result = FR_DATETIME_SDF.parse(toConvert);
					} catch (ParseException e3) {
						return null;
					}
				}
			}
		}
		return result;
		
	}

}
