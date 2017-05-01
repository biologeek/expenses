package io.biologeek.expenses.domain.beans.operations;

import io.biologeek.expenses.domain.beans.Interval;

public class RegularIncome extends Income implements RegularOperation {

	private Interval interval;

	public void setInterval(Interval interval) {
		this.interval = interval;
	}

	public Interval getInterval() {
		return interval;
	}

}
