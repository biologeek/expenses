package io.biologeek.expenses.api.beans;

public class RegularIncome extends Income {
	private Interval interval;

	public Interval getInterval() {
		return interval;
	}

	public void setInterval(Interval interval) {
		this.interval = interval;
	}

}
