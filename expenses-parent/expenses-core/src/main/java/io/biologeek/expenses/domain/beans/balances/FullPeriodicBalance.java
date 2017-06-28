package io.biologeek.expenses.domain.beans.balances;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import io.biologeek.expenses.domain.beans.Balance;
import io.biologeek.expenses.domain.beans.Category;
import io.biologeek.expenses.domain.beans.operations.Operation;

public class FullPeriodicBalance extends Balance {

	private DailyBalances dailyBalances;

	private Date begin;
	private Date end;

	private Map<Category, BigDecimal> categories;

	public FullPeriodicBalance() {
		dailyBalances = new DailyBalances();
	}
	
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

	public void setDailyBalances(DailyBalances dailyBalances) {
		this.dailyBalances = dailyBalances;
	}

	public DailyBalances getDailyBalances() {
		return dailyBalances;
	}

	public FullPeriodicBalance dailyBalances(DailyBalances dailyBalances) {
		this.setDailyBalances(dailyBalances);
		return this;
	}

}
