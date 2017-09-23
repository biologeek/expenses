package io.biologeek.expenses.domain.beans.balances;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Predicate;

import io.biologeek.expenses.domain.beans.Balance;
import io.biologeek.expenses.domain.beans.Category;
import io.biologeek.expenses.domain.beans.operations.Operation;
import io.biologeek.expenses.utils.Constants;

/**
 * Object that compiles all operations of t to a single value with details
 * for each category
 * 
 * 
 *
 */
public class BalanceUnit extends Balance {

	private Date balanceDate;
	private BigDecimal balanceValue;
	private Currency balanceCurrency = Currency.getInstance(Constants.DEFAULT_CURRENCY);

	public BalanceUnit() {
		super();
		balanceValue = new BigDecimal("0");
		balanceValue.setScale(2, RoundingMode.HALF_EVEN);
	}

	public Date getBalanceDate() {
		return balanceDate;
	}

	public void setBalanceDate(Date balanceDate) {
		this.balanceDate = balanceDate;
	}

	/**
	 * Calculates balance date to the middle of the period between begin and end of balance
	 * 
	 * @param balanceBegin begin of period covered by balance
	 * @param balanceEnd end of period covered by balance
	 */
	public void computeBalanceDate(Date balanceBegin, Date balanceEnd) {
		Calendar calBegin = Calendar.getInstance();
		calBegin.setTime(balanceBegin);
		
		Calendar calEnd = Calendar.getInstance();
		calEnd.setTime(balanceEnd);
		
		Calendar res = Calendar.getInstance();
		res.setTimeInMillis(calBegin.getTimeInMillis() 
				+ new BigDecimal(calEnd.getTimeInMillis() - calBegin.getTimeInMillis()).divide(new BigDecimal(2.0)).longValue());
		this.balanceDate = res.getTime();
	}

	public BigDecimal getBalanceValue() {
		return balanceValue;
	}

	public void setBalanceValue(BigDecimal balanceValue) {
		this.balanceValue.setScale(2, RoundingMode.DOWN);
		this.balanceValue = balanceValue;
	}

	public Currency getBalanceCurrency() {
		return balanceCurrency;
	}

	public void setBalanceCurrency(Currency balanceCurrency) {
		this.balanceCurrency = balanceCurrency;
	}
}