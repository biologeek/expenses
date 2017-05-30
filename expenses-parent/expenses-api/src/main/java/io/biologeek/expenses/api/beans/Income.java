package io.biologeek.expenses.api.beans;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect
public class Income extends Operation {

	private String currency;
	private Interval interval;

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Interval getInterval() {
		return interval;
	}

	public void setInterval(Interval interval) {
		this.interval = interval;

	}
}
