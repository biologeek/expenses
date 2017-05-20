package io.biologeek.expenses.domain.beans.operations;

import java.util.Currency;

import javax.persistence.Convert;

import io.biologeek.expenses.data.converters.CurrencyConverter;

/**
 * An income represents a positive operation in your account
 * @author xcaron
 *
 */
public class Income extends Operation{

	@Convert(converter = CurrencyConverter.class)
	Currency currency;

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public Income getMe(){
		return this;
	}
}
