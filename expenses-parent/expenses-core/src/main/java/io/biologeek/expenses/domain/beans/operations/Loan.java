package io.biologeek.expenses.domain.beans.operations;

import java.util.Date;

/**
 * A loan is a positive operation but it is also a non permanent one
 * @author xcaron
 *
 */
public class Loan extends Income implements TemporaryOperation {

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
