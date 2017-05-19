package io.biologeek.expenses.api.beans;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect
public class Refund {
	private Long id;
	private Date reimbursmentDate;
	private Double reimbursedAmount;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
