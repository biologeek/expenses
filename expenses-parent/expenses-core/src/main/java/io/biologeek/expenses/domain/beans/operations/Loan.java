package io.biologeek.expenses.domain.beans.operations;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import io.biologeek.expenses.exceptions.BusinessException;

/**
 * A loan is a positive operation but it is also a non permanent one. It can be
 * reimbursed in more than one time.
 * 
 * @author xcaron
 *
 */
@Entity
public class Loan extends Income implements TemporaryOperation {
	private Date totalReimbursmentDate;
	private Double totalReimbursedAmount;

	@OneToMany(mappedBy="operation")
	List<Refund> reimbursments;

	public List<Refund> getReimbursments() {
		return reimbursments;
	}

	public void setReimbursments(List<Refund> reimbursments) {
		this.reimbursments = reimbursments;
	}

	public void solveTemporaryOperation(Double sum) throws BusinessException {
		this.setTotalReimbursmentDate(new Date());
		if (!this.getTotalReimbursedAmount().equals(this.getAmount()))
			throw new BusinessException("loan.not.refund");
	}

	public Double getTotalReimbursedAmount() {
		return totalReimbursedAmount;
	}

	public Date getTotalReimbursmentDate() {
		return totalReimbursmentDate;
	}

	public void setTotalReimbursmentDate(Date totalReimbursmentDate) {
		this.totalReimbursmentDate = totalReimbursmentDate;
	}

}
