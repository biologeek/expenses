package io.biologeek.expenses.domain.beans.operations;

import javax.persistence.Embedded;
import javax.persistence.Entity;

import io.biologeek.expenses.domain.beans.Interval;

/**
 * An income that is received regularly. For example a salary, ...
 * 
 * @author xcaron
 *
 */
@Entity
public class RegularIncome extends Income implements RegularOperation {

	@Embedded
	private Interval interval;

	public void setInterval(Interval interval) {
		this.interval = interval;
	}

	public Interval getInterval() {
		return interval;
	}

}
