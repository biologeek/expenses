package io.biologeek.expenses.domain.beans.balances;

import java.util.Date;

import io.biologeek.expenses.domain.beans.Balance;

/**
 * A {@link PeriodicBalance} consists of a list of daily balances between two dates.
 */
public class PeriodicBalance extends Balance {
	private DailyBalances dailyBalances;

	private Date begin;
	private Date end;
	
	
	public DailyBalances getDailyBalances() {
		return dailyBalances;
	}
	public void setDailyBalances(DailyBalances dailyBalances) {
		this.dailyBalances = dailyBalances;
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
	
	
}
