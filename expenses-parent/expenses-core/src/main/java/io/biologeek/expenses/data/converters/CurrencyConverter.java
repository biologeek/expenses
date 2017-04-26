package io.biologeek.expenses.data.converters;

import java.util.Currency;

import javax.persistence.AttributeConverter;

/**
 * Used to map {@link java.util.Currency} to a string in database
 * @author xcaron
 *
 */
public class CurrencyConverter implements AttributeConverter<Currency, String>{

	public String convertToDatabaseColumn(Currency attribute) {
		return attribute.getCurrencyCode();
	}

	public Currency convertToEntityAttribute(String dbData) {
		return Currency.getInstance(dbData);
	}

}
