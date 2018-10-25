package io.biologeek.expenses.api.beans;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonTypeName;


@JsonTypeName("T")
public class TemporaryOperation extends Operation {
	private Date totalReimbursmentDate;
	private Double totalReimbursedAmount;
	List<Refund> reimbursments;

	public Date getTotalReimbursmentDate() {
		return totalReimbursmentDate;
	}

	public void setTotalReimbursmentDate(Date totalReimbursmentDate) {
		this.totalReimbursmentDate = totalReimbursmentDate;
	}

	public Double getTotalReimbursedAmount() {
		return totalReimbursedAmount;
	}

	public void setTotalReimbursedAmount(Double totalReimbursedAmount) {
		this.totalReimbursedAmount = totalReimbursedAmount;
	}

	public List<Refund> getReimbursments() {
		return reimbursments;
	}

	public void setReimbursments(List<Refund> reimbursments) {
		this.reimbursments = reimbursments;
	}
}
