package io.biologeek.expenses.domain.beans.balances;

import io.biologeek.expenses.domain.beans.Balance;
import io.biologeek.expenses.domain.beans.Category;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class FullPeriodicBalance extends Balance {

	private List<DailyBalance> dailyBalances;

	private Date begin;
	private Date end;

	private Map<Category, BigDecimal> categories;

	public Map<Category, BigDecimal> getCategories() {
		return categories;
	}

	public void setCategories(Map<Category, BigDecimal> categories) {
		this.categories = categories;
	}

	public Date getBegin() {
		return begin;
	}

	public void setBegin(Date begin) {
		this.begin = begin;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	public void setDailyBalances(List<DailyBalance> dailyBalances) {
		this.dailyBalances = dailyBalances;
	}

	public List<DailyBalance> getDailyBalances() {
		return dailyBalances;
	}

}