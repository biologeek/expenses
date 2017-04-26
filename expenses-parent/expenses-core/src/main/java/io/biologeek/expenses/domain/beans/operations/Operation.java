package io.biologeek.expenses.domain.beans.operations;

import java.util.Currency;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;

import io.biologeek.expenses.data.converters.CurrencyConverter;
import io.biologeek.expenses.domain.beans.Account;
import io.biologeek.expenses.domain.beans.Emitter;
import io.biologeek.expenses.domain.beans.Receiver;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Operation {

	@ManyToOne(fetch = FetchType.EAGER)
	Receiver beneficiary;
	@ManyToOne(fetch = FetchType.EAGER)
	Emitter emitter;

	@ManyToOne(fetch = FetchType.LAZY)
	private Account account;

	Double amount;

	@Convert(converter = CurrencyConverter.class)
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
