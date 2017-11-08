package io.biologeek.expenses.beans;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import io.biologeek.expenses.domain.beans.operations.Operation;
@Entity
@DiscriminatorValue("M")
public class MultiTenantOperation extends Operation {

	@Override
	public int compareTo(Operation o) {
		return this.compareTo(o);
	}

}
