package io.biologeek.expenses.api.beans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;

/**
 * @author 
 *
 */
@JsonAutoDetect
@JsonSubTypes({@Type(value=RegularOperation.class), @Type(value=TemporaryOperation.class)})
public class Operation implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7925429032845927724L;
	private Long id;
	User beneficiary;
	User emitter;

	private Long account;
	private String description;
	@JsonFormat(shape=Shape.OBJECT)
	private OperationType type;
	@JsonFormat(shape=Shape.SCALAR)
	private BigDecimal amount;
	private String currency;
	private Date effectiveDate;
	
	private String operationTypeName;

	Category category;
	
	@JsonFormat(shape=Shape.OBJECT)
	Nomenclature nomenclature;

	private Date creationDate;
	private Date updateDate;
	private Integer version;
	private boolean modifiable;
	
	private List<Operation> children;

	public boolean isModifiable() {
		return modifiable;
	}

	public String getOperationTypeName() {
		return operationTypeName;
	}

	public void setOperationTypeName(String operationTypeName) {
		this.operationTypeName = operationTypeName;
	}

	public Nomenclature getNomenclature() {
		return nomenclature;
	}

	public void setNomenclature(Nomenclature nomenclature) {
		this.nomenclature = nomenclature;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public User getBeneficiary() {
		return beneficiary;
	}

	public void setBeneficiary(User beneficiary) {
		this.beneficiary = beneficiary;
	}

	public OperationType getType() {
		return type;
	}

	public void setType(OperationType type) {
		this.type = type;
	}

	public User getOperationAgent() {
		return emitter;
	}

	public void setEmitter(User emitter) {
		this.emitter = emitter;
	}

	public Long getAccount() {
		return account;
	}

	public void setAccount(Long account) {
		this.account = account;
	}

	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public User getEmitter() {
		return emitter;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
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

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	/*
	 * Builder
	 * 
	 */

	public Operation account(Long convert) {
		this.account = convert;
		return this;
	}

	public Operation updateDate(Date convert) {
		this.updateDate = convert;
		return this;
	}

	public Operation amount(BigDecimal convert) {
		this.amount = convert;
		return this;
	}

	public Operation beneficiary(User convert) {
		this.beneficiary = convert;
		return this;
	}

	public Operation emitter(User convert) {
		this.emitter = convert;
		return this;
	}

	public Operation category(Category convert) {
		this.category = convert;
		return this;
	}

	public Operation creationDate(Date convert) {
		this.creationDate = convert;
		return this;
	}

	public Operation id(Long convert) {
		this.id = convert;
		return this;
	}

	public Operation version(Integer convert) {
		this.version = convert;
		return this;
	}

	public Operation currency(String convert) {
		this.currency = convert;
		return this;
	}

	public Operation description(String description2) {
		this.description = description2;
		return this;
	}

	public void setModifiable(boolean modifiable) {
		this.modifiable = modifiable;
		
	}

	public List<Operation> getChildren() {
		return children;
	}

	public void setChildren(List<Operation> children) {
		this.children = children;
	}
}
