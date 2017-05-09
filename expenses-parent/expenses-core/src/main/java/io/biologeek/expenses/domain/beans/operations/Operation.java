package io.biologeek.expenses.domain.beans.operations;

import java.util.Currency;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import io.biologeek.expenses.data.converters.CurrencyConverter;
import io.biologeek.expenses.domain.beans.Account;
import io.biologeek.expenses.domain.beans.Category;
import io.biologeek.expenses.domain.beans.Emitter;
import io.biologeek.expenses.domain.beans.Receiver;

/**
 * An {@link Operation} is an exchange between an emitter and a receiver. It is attached to an account 
 * @author xcaron
 *
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Operation {

	@Id
	@GeneratedValue
	private Long id;
	@ManyToOne(fetch = FetchType.EAGER)
	Receiver beneficiary;
	@ManyToOne(fetch = FetchType.EAGER)
	Emitter emitter;

	@ManyToOne(fetch = FetchType.LAZY)
	private Account account;

	private Double amount;

	@Convert(converter = CurrencyConverter.class)
	Currency currency;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="operation_category")
	Category category;

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

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
