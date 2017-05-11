package io.biologeek.expenses.domain.beans.operations;

import java.util.Currency;

import javax.persistence.Convert;
import javax.persistence.Entity;

import io.biologeek.expenses.data.converters.CurrencyConverter;

@Entity
public class Expense extends Operation {

	@Convert(converter = CurrencyConverter.class)
	Currency currency;

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

}
