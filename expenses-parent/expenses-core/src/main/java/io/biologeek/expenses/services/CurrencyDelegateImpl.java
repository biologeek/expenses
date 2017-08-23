package io.biologeek.expenses.services;

import java.util.Currency;

import org.springframework.stereotype.Service;


/**
 * A delegate service that 
 * 
 *
 */
@Service
public class CurrencyDelegateImpl implements CurrencyDelegate {

	public Double convert(Double amount, Currency fromUnit, Currency toUnit) {
		if (amount == null || amount.equals(0d))
			return 0D;
		return this.convertFromService(amount, fromUnit, toUnit);
	}

	private Double convertFromService(Double amount, Currency fromUnit, Currency toUnit) {
		// TODO Auto-generated method stub
		return amount;
	}

}
