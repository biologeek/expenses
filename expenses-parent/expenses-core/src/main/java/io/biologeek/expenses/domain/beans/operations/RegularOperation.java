package io.biologeek.expenses.domain.beans.operations;

import java.util.Date;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.persistence.Transient;

import io.biologeek.expenses.domain.beans.Interval;
import io.biologeek.expenses.validation.OperationValidator;

/**
 * An {@link Operation} that occurs regularly. It is composed of several
 * {@link UsualOperation} that represent the concrete sums of money that will be
 * spent or received at defined intervals. <br>
 * <br>
 * Only {@link UsualOperation}s composing this type will be counted in balances.
 * This operation is only used to be parametered. <br>
 * <br>
 * Only this one is modifiable and its children should not be modified by user.
 * After validation by {@link OperationValidator} object, it can be modified
 * internally following business rules
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

	public SortedSet<UsualOperation> getConcreteOperations()
			throws CloneNotSupportedException {
		if (concreteOperations != null && concreteOperations.size() > 0)
			return concreteOperations;
		return null;
	}

	public void setConcreteOperations(
			SortedSet<UsualOperation> concreteOperations) {
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
