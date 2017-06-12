package io.biologeek.expenses.domain.beans.balances;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Date;
import java.util.Map.Entry;
import java.util.function.Predicate;

import io.biologeek.expenses.domain.beans.Balance;
import io.biologeek.expenses.domain.beans.Category;
import io.biologeek.expenses.domain.beans.balances.DailyBalances.DailyBalance;
import io.biologeek.expenses.domain.beans.operations.Operation;
import io.biologeek.expenses.exceptions.ModelException;

/**
 * Object that compiles all operations of the day to a single value with details
 * for each category
 * 
 * @author xcaron
 *
 */

public class DailyBalances extends ArrayList<DailyBalance> {

	SimpleDateFormat dateTimeFrenchPattern = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	SimpleDateFormat dateFrenchPattern = new SimpleDateFormat("dd/MM/yyyy");

	public DailyBalance getByDate(String string) throws ModelException {
		Date date = null;
		try {
			return getByDate(dateFrenchPattern.parse(string));
		} catch (ParseException e) {
			throw new ModelException("error.parse.pattern");
		}
	}

	private DailyBalance getByDate(Date parsed) {
		return (DailyBalance) this.stream().filter(new Predicate<DailyBalance>() {
			@Override
			public boolean test(DailyBalance t) {
				return t.getBalanceDate().equals(parsed);
			}
		});
	}

	public static class DailyBalance extends Balance {

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
			return this.getCategoryBalance().getCategories().entrySet().stream()
					.filter(new Predicate<Entry<Category, BigDecimal>>() {
						@Override
						public boolean test(Entry<Category, BigDecimal> t) {
							return t.getKey().equals(op.getCategory());
						}
					}).findFirst().get();

		}
	}
}
