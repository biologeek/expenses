package io.biologeek.expenses.api.beans;

import java.util.Date;

public class Interval {
	private int interval;
	private int unit;
	
	private Date first;
	private Date last;

	public Date getFirst() {
		return first;
	}

	public void setFirst(Date first) {
		this.first = first;
	}

	public Date getLast() {
		return last;
	}

	public void setLast(Date last) {
		this.last = last;
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

	public Interval interval(int interval) {
		this.interval = interval;
		return this;
	}

	public Interval unit(int unit) {
		this.unit = unit;
		return this;
	}
}
