package io.biologeek.expenses.domain.beans;

public class Interval {
	double interval;
	int unit;

	public double getInterval() {
		return interval;
	}

	public void setInterval(double interval) {
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
}
