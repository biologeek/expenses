package io.biologeek.expenses.domain.beans;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import io.biologeek.expenses.domain.beans.operations.Operation;

@Entity
public class Account {

	@Id
	@GeneratedValue
	private Long id;
	private String name;
	
	@ManyToOne(fetch=FetchType.LAZY)
	private RegisteredUser owner;
	private Long number;
	@OneToMany(mappedBy = "account")
	private List<Operation> expenses;
	
	
	private Date creationDate;
	private Date updateDate;
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Account id(Long id) {
		this.id = id;
		return this;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public Account name(String name) {
		this.name = name;
		return this;
	}

	public RegisteredUser getOwner() {
		return owner;
	}

	public void setOwner(RegisteredUser owner) {
		this.owner = owner;
	}
	public Account owner(RegisteredUser owner) {
		this.owner = owner;
		return this;
	}

	public Long getNumber() {
		return number;
	}

	public void setNumber(Long number) {
		this.number = number;
	}

	public Account number(Long number) {
		this.number = number;
		return this;
	}

	public List<Operation> getExpenses() {
		return expenses;
	}

	public void setExpenses(List<Operation> expenses) {
		this.expenses = expenses;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Account creationDate(Date creationDate) {
		this.creationDate = creationDate;
		return this;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public Account updateDate(Date updateDate) {
		this.updateDate = updateDate;
		return this;
	}

}
