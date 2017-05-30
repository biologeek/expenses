package io.biologeek.expenses.domain.beans.operations;

import io.biologeek.expenses.domain.beans.Interval;

/**
 * An {@link Operation} that occurs regularly
 * 
 * @author xcaron
 */
public interface RegularOperation {

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
}
