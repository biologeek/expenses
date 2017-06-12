package io.biologeek.expenses.domain.beans.operations;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * A {@link Refund} is a part of the refund of a {@link Debt} or {@link TemporaryOperation}
 */
@Entity
public class Refund {

	@Id@GeneratedValue
	private Long id;
	private Date reimbursmentDate;
	private Double reimbursedAmount;

	@ManyToOne
	@JoinColumn(name="reimbursments")
	Operation operation;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Operation getOperation() {
		return operation;
	}

	public void setOperation(Operation operation) {
		this.operation = operation;
	}

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
