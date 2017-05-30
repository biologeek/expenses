package io.biologeek.expenses.services;

import java.util.Currency;

public interface CurrencyDelegate {
	public Double convert(Double amount, Currency fromUnit, Currency toUnit);

}
