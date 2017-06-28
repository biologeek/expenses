package io.biologeek.expenses.domain.beans.operations;

import java.util.Currency;
import java.util.Date;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
import io.biologeek.expenses.domain.beans.OperationAgent;
import io.biologeek.expenses.domain.beans.OperationAgent;

/**
 * An {@link Operation} is an exchange between an OperationAgent and a receiver. It is
 * attached to an account
 * 
 * @author xcaron
 *
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Operation {

	@Id
	@GeneratedValue
	private Long id;
	@ManyToOne(fetch = FetchType.EAGER)
	OperationAgent beneficiary;
	@ManyToOne(fetch = FetchType.EAGER)
	OperationAgent emitter;

	@ManyToOne(fetch = FetchType.LAZY)
	private Account account;

	private Double amount;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "operation_category")
	Category category;

	/**
	 * The date the operation was created
	 */
	private Date creationDate;
	/**
	 * The date the operation was updated
	 */
	private Date updateDate;
	private int version;
	/**
	 * The date the operation was effectively made
	 */
	private Date effectiveDate;

	private String description;

	@Enumerated(EnumType.STRING)
	private OperationType operationType;

	@Convert(converter = CurrencyConverter.class)
	Currency currency;

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public OperationAgent getBeneficiary() {
		return beneficiary;
	}

	public void setBeneficiary(OperationAgent beneficiary) {
		this.beneficiary = beneficiary;
	}

	public OperationAgent getEmitter() {
		return emitter;
	}

	public void setEmitter(OperationAgent OperationAgent) {
		this.emitter = OperationAgent;
	}

	public Double getAmount() {
		if (this.operationType.getSign() < 0 && amount.doubleValue() > 0) {
			return -amount;
		} else if (this.operationType.getSign() > 0 && amount.doubleValue() > 0) {
			return amount;
		} else if (this.operationType.getSign() < 0 && amount.doubleValue() < 0) {
			return amount;
		} else {
			return -amount;
		}
	}

	public void setAmount(Double amount) {
		this.amount = amount;
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

	public OperationType getOperationType() {
		return operationType;
	}

	public void setOperationType(OperationType operationType) {
		this.operationType = operationType;
	}

}
