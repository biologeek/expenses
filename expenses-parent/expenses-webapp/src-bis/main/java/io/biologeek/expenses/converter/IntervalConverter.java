package io.biologeek.expenses.converter;

import io.biologeek.expenses.domain.beans.Interval;

public class IntervalConverter {

	public static Interval convert(io.biologeek.expenses.api.beans.Interval interval) {
		Interval res = new Interval();
		res.setFirstDate(interval.getFirst());
		res.setLastDate(interval.getLast());
		res.setInterval(interval.getInterval());
		res.setUnit(interval.getUnit());
		return res;
	}

}
