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
 * @author xcaron
 *
 */
public class DailyBalance extends Balance {

	private Date balanceDate;
	private BigDecimal balanceValue;
	private Currency balanceCurrency;

	private CategoryBalance categoryBalance;
	
	public DailyBalance() {
		super();
		categoryBalance = new CategoryBalance();
	}

	public CategoryBalance getCategoryBalance() {
		return categoryBalance;
	}

	public void setCategoryBalance(CategoryBalance categoryBalance) {
		this.categoryBalance = categoryBalance;
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

	/**
	 * Finds the {@link Category} correponding to the operation if it exists
	 * 
	 * @param op
	 * @return
	 */
	public Entry<Category, BigDecimal> findCategory(final Operation op) {
		return this.getCategories().entrySet().stream()
				.filter(new Predicate<Entry<Category, BigDecimal>>() {
					@Override
					public boolean test(Entry<Category, BigDecimal> t) {
						return t.getKey().equals(op.getCategory());
					}
				}).findFirst().get();

	}
}
