package io.biologeek.expenses.api.beans;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * In case of regular operation interval is not null and appears in JSON<BR>
 * <BR>
 * For unitary operation, interval is null and not shown
 */
@JsonInclude(value = Include.NON_NULL)
public class Expense extends Operation {

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
