package io.biologeek.expenses.domain.beans.operations;

import java.util.Currency;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
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
import javax.persistence.OneToMany;

import io.biologeek.expenses.data.converters.CurrencyConverter;
import io.biologeek.expenses.domain.beans.Account;
import io.biologeek.expenses.domain.beans.Category;
import io.biologeek.expenses.domain.beans.OperationAgent;

/**
 * An {@link Operation} is an exchange between an emitter and a receiver. It is
 * attached to an account
 * 
 * 
 *
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Operation implements Comparable<Operation> {

	@Id
	@GeneratedValue
	protected Long id;
	@ManyToOne(fetch = FetchType.EAGER)
	protected OperationAgent beneficiary;
	@ManyToOne(fetch = FetchType.EAGER)
	protected OperationAgent emitter;

	@ManyToOne(fetch = FetchType.EAGER)
	protected Account account;

	protected Double amount;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "operation_category")
	protected Category category;
	@Enumerated(EnumType.STRING)
	protected OperationType operationType;

	/**
	 * The date the operation was created
	 */
	protected Date creationDate;
	/**
	 * The date the operation was updated
	 */
	protected Date updateDate;
	protected int version;
	/**
	 * The date the operation was effectively made
	 */
	protected Date effectiveDate;

	protected String description;

	@Convert(converter = CurrencyConverter.class)
	protected Currency currency;

	/**
	 * If this operation is part of a multipart operation,
	 * parentAccountableOperation if the one one that will be modifiable
	 */
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="operation_parent")
	protected Operation parentAccountableOperation;
	
	@OneToMany(mappedBy="parentAccountableOperation", cascade=CascadeType.ALL)
	protected List<Operation> childrenOperations;
	
	protected OperationStatus status;
	
	public OperationStatus getStatus() {
		return status;
	}

	public void setStatus(OperationStatus status) {
		this.status = status;
	}

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

	public void setEmitter(OperationAgent emitter) {
		this.emitter = emitter;
	}

	public Double getAmount() {
		return Math.abs(amount) * operationType.getSign();
	}

	public void setAmount(Double amount) {
		this.amount = (amount == null ? 0 : Math.abs(amount));
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public OperationType getOperationType() {
		return operationType;
	}

	public void setOperationType(OperationType operationType) {
		this.operationType = operationType;
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

	public Operation amount(Double amount) {
		this.amount = amount;
		return this;
	}

	public Operation currency(Currency currency) {
		this.currency = currency;
		return this;
	}

	public Operation id(Long l) {
		id = l;
		return this;
	}

	public Operation account(Account account) {
		this.account = account;
		return this;
	}

	public Operation emitter(OperationAgent emitter2) {
		this.emitter = emitter2;
		return this;
	}

	public Operation beneficiary(OperationAgent beneficiary2) {
		this.beneficiary = beneficiary2;
		return this;
	}

	public Operation category(Category beneficiary2) {
		this.category = beneficiary2;
		return this;
	}

	public Operation updateDate(Date beneficiary2) {
		this.updateDate = beneficiary2;
		return this;
	}

	public Operation effectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
		return this;
	}

	public Operation creationDate(Date creationDate) {
		this.creationDate = creationDate;
		return this;
	}

	public Operation description(String description) {
		this.description = description;
		return this;
	}

	public Operation operationType(OperationType operationType) {
		this.operationType = operationType;
		return this;
	}

	public Operation version(int version) {
		this.version = version;
		return this;
	}

	public Operation getParentAccountableOperation() {
		return parentAccountableOperation;
	}

	public void setParentAccountableOperation(Operation parentAccountableOperation) {
		this.parentAccountableOperation = parentAccountableOperation;
	}

	public boolean isModifiable() {
		return parentAccountableOperation == null;
	}

	public List<? extends Operation> getChildrenOperations() {
		return childrenOperations;
	}

	public void setChildrenOperations(List<Operation> childrenOperations) {
		this.childrenOperations = childrenOperations;
	}
	
	
	public Operation status(OperationStatus status) {
		this.status = status;
		return this;
	}
}
