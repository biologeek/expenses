package io.biologeek.expenses.domain.beans.operations;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import io.biologeek.expenses.exceptions.BusinessException;

/**
 * A temporary operation is a non permanent operation. The operation consists in
 * lending or receiving money from someone else and giving back money with 1 or
 * more reimbursments.
 * 
 * @author xavier
 *
 */
@Entity
public class TemporaryOperation extends Operation implements Temporary {
	@Column(nullable=true)
	private Date totalReimbursmentDate;
	@Column(nullable=true)
	private Double totalReimbursedAmount;
	
	@OneToMany(mappedBy = "operation")
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

	public void setTotalReimbursedAmount(Double totalReimbursedAmount) {
		this.totalReimbursedAmount = totalReimbursedAmount;
	}  

}
