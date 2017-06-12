package io.biologeek.expenses.domain.beans;

import io.biologeek.expenses.utils.DateUtils;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Embeddable;

@Embeddable
public class Interval {
	
	Date firstDate;
	int interval;
	/**
	 * Corresponds to {@link Calendar} unit
	 */
	int unit;

	Date lastDate;

	public Date getFirstDate() {
		return firstDate;
	}

	public void setFirstDate(Date firstDate) {
		this.firstDate = firstDate;
	}

	public Date getLastDate() {
		return lastDate;
	}

	public void setLastDate(Date lastDate) {
		this.lastDate = lastDate;
	}

	public int getInterval() {
		return interval;
	}

	public void setInterval(int interval) {
		this.interval = interval;
	}

	public int getUnit() {
		return unit;
	}

	public void setUnit(int unit) {
		this.unit = unit;
	}

	public Interval interval() {
		this.interval = interval;
		return this;
	}

	public Interval unit(int unit) {
		this.unit = unit;
		return this;
	}
	
	public boolean isOutdated(){
		Calendar now = Calendar.getInstance();
		Calendar last = Calendar.getInstance();
		last.setTime(lastDate);
		last.add(unit, interval);
		
		return last.after(now);
	}

	public boolean isAnOperationOfTheDay(Date balanceDate) {
		Calendar cal = Calendar.getInstance();
		
		cal.setTime(lastDate);
		cal.add(unit, interval);
		Date theoreticalNextDate = cal.getTime()
		return DateUtils.areSameDate(balanceDate, theoreticalNextDate);
	}
	
}
