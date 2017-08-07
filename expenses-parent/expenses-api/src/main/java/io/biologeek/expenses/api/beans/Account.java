package io.biologeek.expenses.api.beans;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect
public class Account {

	private Long id;
	private String name;
	/**
	 * For the moment, user is only a String. Then it could be an object
	 */
	private Long owner;
	private Long number;
	private List<? extends Operation> expenses;

	private Date creationDate;
	private Date updateDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getOwner() {
		return owner;
	}

	public void setOwner(Long user) {
		this.owner = user;
	}

	public Long getNumber() {
		return number;
	}

	public void setNumber(Long number) {
		this.number = number;
	}

	public List<? extends Operation> getExpenses() {
		return expenses;
	}

	public void setExpenses(List<? extends Operation> expenses) {
		this.expenses = expenses;
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
}
