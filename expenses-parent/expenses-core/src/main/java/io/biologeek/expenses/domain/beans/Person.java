package io.biologeek.expenses.domain.beans;

import java.util.List;

import javax.persistence.OneToMany;

/**
 * A Person is a physical Entity, meaning a Person that has a name, ... in
 * opposition with a {@link Organization}
 * 
 * 
 *
 */
@javax.persistence.Entity
public class Person extends Entity {

	protected String firstName;
	protected String lastName;
	protected Integer age;
	protected String mailAddress;
	protected String phoneNumber;
	
	@OneToMany(mappedBy="mainContact")
	protected List<Organization> organization;

	public String getMailAddress() {
		return mailAddress;
	}

	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}
}
