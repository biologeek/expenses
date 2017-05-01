package io.biologeek.expenses.domain.beans.operations;

import java.util.Date;

import javax.persistence.Entity;

@Entity
public class Debt extends Expense implements TemporaryOperation {
	private Date reimbursmentDate;
	private Double reimbursedAmount;

	public void solveTemporaryOperation(Double sum) {
		this.setReimbursmentDate(new Date());
		this.setReimbursedAmount(sum);
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
