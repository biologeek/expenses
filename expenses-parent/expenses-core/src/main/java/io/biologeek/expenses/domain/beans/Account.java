package io.biologeek.expenses.domain.beans;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import io.biologeek.expenses.domain.beans.operations.Expense;
import io.biologeek.expenses.domain.beans.operations.Operation;

@Entity
public class Account {

	@Id@GeneratedValue
	private Long id;
	private String name;
	/**
	 * For the moment, user is only a String. Then it could be an object
	 */
	private String owner;
	private Long number;
	@OneToMany(mappedBy="account")
	private List<Operation> expenses;

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

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public Long getNumber() {
		return number;
	}

	public void setNumber(Long number) {
		this.number = number;
	}

	public List<Operation> getExpenses() {
		return expenses;
	}

	public void setExpenses(List<Operation> expenses) {
		this.expenses = expenses;
	}

}
