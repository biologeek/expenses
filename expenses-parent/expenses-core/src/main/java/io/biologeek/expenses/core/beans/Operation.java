package io.biologeek.expenses.core.beans;

import java.util.Currency;

public class Operation {

	
	Receiver beneficiary;
	Emitter emitter;
	
	Double amount;
	Currency currency;
	
	
	public Receiver getBeneficiary() {
		return beneficiary;
	}
	public void setBeneficiary(Receiver beneficiary) {
		this.beneficiary = beneficiary;
	}
	public Emitter getEmitter() {
		return emitter;
	}
	public void setEmitter(Emitter emitter) {
		this.emitter = emitter;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public Currency getCurrency() {
		return currency;
	}
	public void setCurrency(Currency currency) {
		this.currency = currency;
	}
	
}
