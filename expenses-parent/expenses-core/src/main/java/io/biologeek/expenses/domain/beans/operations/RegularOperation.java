package io.biologeek.expenses.domain.beans.operations;

import java.util.Date;

import io.biologeek.expenses.domain.beans.Interval;

/**
 * An {@link Operation} that occurs regularly
 * 
 * 
 */
public class RegularOperation implements Regular {

	@Override
	public void setInterval(Interval interval) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Interval getInterval() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Date getFirstOccurence() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Date getLastOccurence() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Operation getInstantOperation(Date dateOfTheDay) {
		// TODO Auto-generated method stub
		return null;
	}

}
