package io.biologeek.expenses.domain.beans.operations;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * A {@link Refund} is a part of the refund of a {@link Debt} or {@link Loan}
 */
@Entity
public class Refund {

	private Date reimbursmentDate;
	private Double reimbursedAmount;

	@ManyToOne
	@JoinColumn(name="reimbursments")
	Operation operation;
	
	public Date getReimbursmentDate() {
		return reimbursmentDate;
	}

	public void setReimbursmentDate(Date reimbursmentDate) {
		this.reimbursmentDate = reimbursmentDate;
	}

	public Double getReimbursedAmount() {
		return reimbursedAmount;
	}

	public void setReimbursedAmount(Double reimbursedAmount) {
		this.reimbursedAmount = reimbursedAmount;
	}

}
