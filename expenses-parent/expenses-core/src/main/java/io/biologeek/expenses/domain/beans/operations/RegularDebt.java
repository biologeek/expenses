package io.biologeek.expenses.domain.beans.operations;

import javax.persistence.Embedded;
import javax.persistence.Entity;

import io.biologeek.expenses.domain.beans.Interval;

@Entity
public class RegularDebt extends Debt implements RegularOperation {

	@Embedded
	private Interval interval;

	public void setInterval(Interval interval) {
		this.interval = interval;
	}

	public Interval getInterval() {
		return interval;
	}

}
