package io.biologeek.expenses.data.converters;

import java.util.Currency;
import java.util.Set;

import javax.persistence.AttributeConverter;

/**
 * Used to map {@link java.util.Currency} to a string in database
 * 
 *
 */
public class CurrencyConverter implements AttributeConverter<Currency, String>{

	public String convertToDatabaseColumn(Currency attribute) {
		return attribute.getCurrencyCode();
	}

	public Currency convertToEntityAttribute(String dbData) {
		Set<Currency> currencies = Currency.getAvailableCurrencies();
		for (Currency cur : currencies) {
			if (cur.getCurrencyCode().equals(dbData) || cur.getSymbol().equals(dbData)) {
				return cur;
			}
		}
		return null;
	}

	public static String convert(Currency currency) {
		return currency.getSymbol();
	}

}
