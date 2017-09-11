package io.biologeek.expenses.domain.beans.operations;

import java.util.Date;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.persistence.Transient;

import io.biologeek.expenses.domain.beans.Interval;

/**
 * An {@link Operation} that occurs regularly.
 * 
 * A regular Operation is thus a set of {@link UsualOperation} separated by an interval.
 * 
 * 
 */
public class RegularOperation extends Operation implements Regular, Cloneable {
	
	
	private Interval interval;
	@Transient
	private SortedSet<UsualOperation> concreteOperations;


	@Override
	public void setInterval(Interval interval) {
		this.interval = interval;
	}

	@Override
	public Interval getInterval() {
		return interval;
	}

	@Override
	public Date getFirstOccurence() {
		return concreteOperations.first().getEffectiveDate();
	}

	@Override
	public Date getLastOccurence() {
		return concreteOperations.last().getEffectiveDate();
	}
	
	@Override
	public Operation getInstantOperation(Date dateOfTheDay) {
		for (UsualOperation op : concreteOperations) {
			if (op.getEffectiveDate().equals(dateOfTheDay))
				return op;
		}
		return null;
	}

	public SortedSet<UsualOperation> getConcreteOperations() throws CloneNotSupportedException {
		if (concreteOperations != null && concreteOperations.size() > 0)
			return concreteOperations;
		return null;
	}

	public void setConcreteOperations(SortedSet<UsualOperation> concreteOperations) {
		this.concreteOperations = concreteOperations;
	}

	@Override
	/**
	 * TODO : Find a way to compare RegularOperations
	 */
	public int compareTo(Operation o) {
		return 0;
	}
	
	public Operation clone() {
		return (Operation) this;
	}

}
