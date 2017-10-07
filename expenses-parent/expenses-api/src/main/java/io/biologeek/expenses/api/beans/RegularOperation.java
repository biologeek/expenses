package io.biologeek.expenses.api.beans;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect
public class RegularOperation extends Operation {

	
	private Interval interval;

	public Interval getInterval() {
		return interval;
	}

	public void setInterval(Interval interval) {
		this.interval = interval;
	}
	
}
