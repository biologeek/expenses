package io.biologeek.expenses.domain.beans.balances;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Predicate;

import io.biologeek.expenses.domain.beans.Balance;
import io.biologeek.expenses.domain.beans.Category;
import io.biologeek.expenses.domain.beans.operations.Operation;

/**
 * Object that compiles all operations of the day to a single value with details
 * for each category
 * 
 * 
 *
 */
public class DailyBalance extends Balance {

	private Date balanceDate;
	private BigDecimal balanceValue;
	private Currency balanceCurrency;

	public DailyBalance() {
		super();
	}

	public Date getBalanceDate() {
		return balanceDate;
	}

	public void setBalanceDate(Date balanceDate) {
		this.balanceDate = balanceDate;
	}

	public BigDecimal getBalanceValue() {
		return balanceValue;
	}

	public void setBalanceValue(BigDecimal balanceValue) {
		this.balanceValue = balanceValue;
	}

	public Currency getBalanceCurrency() {
		return balanceCurrency;
	}

	public void setBalanceCurrency(Currency balanceCurrency) {
		this.balanceCurrency = balanceCurrency;
	}
}