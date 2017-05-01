package io.biologeek.expenses.domain.beans;

import java.util.List;

import javax.persistence.OneToMany;

/**
 * A Person is a physical Entity, meaning a Person that has a name, ... in
 * opposition with a {@link Organization}
 * 
 * @author xcaron
 *
 */
@javax.persistence.Entity
public class Person extends Entity {

	private String firstName;
	private String lastName;
	private Integer age;
	private String mailAddress;
	private String phoneNumber;
	
	@OneToMany(mappedBy="mainContact")
	private List<Organization> organization;

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
