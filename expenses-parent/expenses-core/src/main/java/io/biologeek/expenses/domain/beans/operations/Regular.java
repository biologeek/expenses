package io.biologeek.expenses.domain.beans.operations;

import io.biologeek.expenses.domain.beans.Interval;

import java.util.Date;

/**
 * An {@link Operation} that occurs regularly
 * 
 * 
 */
public interface Regular {

	/**
	 * Sets interval to regular {@link Operation}
	 * 
	 * @param interval
	 */
	public void setInterval(Interval interval);

	/**
	 * Returns interval of regular {@link Operation}
	 * 
	 * @param interval
	 */
	public Interval getInterval();

	public Date getFirstOccurence();

	public Date getLastOccurence();

	public Operation getInstantOperation(Date dateOfTheDay);
}
