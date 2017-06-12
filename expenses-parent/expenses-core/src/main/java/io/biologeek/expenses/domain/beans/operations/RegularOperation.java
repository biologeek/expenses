package io.biologeek.expenses.domain.beans.operations;

import io.biologeek.expenses.domain.beans.Interval;

import java.util.Date;

import javax.persistence.Embedded;

public class RegularOperation extends Operation implements Regular {

	@Embedded
	private Interval interval;

	public void setInterval(Interval interval) {
		this.interval = interval;
	}

	public Interval getInterval() {
		return interval;
	}

	@Override
	public Date getFirstOccurence() {
		return interval.getFirstDate();
	}

	@Override
	public Date getLastOccurence() {
		return interval.getLastDate();
	}

	@Override
	public Operation getInstantOperation( Date dateOfTheDay) {
		this.setEffectiveDate(dateOfTheDay);
		return (Operation) this;
	}
}
