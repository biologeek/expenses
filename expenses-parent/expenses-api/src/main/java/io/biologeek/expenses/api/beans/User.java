package io.biologeek.expenses.api.beans;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonAutoDetect
@JsonTypeName(value="user")
public class User extends Entity {
	private String username;
	private String firstName;
	private String lastName;
	private Integer age;
	private String mailAddress;
	private String phoneNumber;
	private List<Account> accounts;
	private String sessionToken;
	private String password;

	public User() {
		this.type ="user";
	}
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getSessionToken() {
		return sessionToken;
	}

	public void setSessionToken(String sessionToken) {
		this.sessionToken = sessionToken;
	}

	public List<Account> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<Account> accounts) {
		this.accounts = accounts;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public User firstName(String firstName2) {
		this.firstName = firstName2;
		return this;
	}

	public User lastName(String firstName2) {
		this.lastName = firstName2;
		return this;
	}

	public User age(Integer age2) {
		this.age = age2;
		return this;
	}

	public User mailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
		return this;
	}

	public User phoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
		return this;
	}

	public User id(Long id) {
		this.id = id;
		return this;
	}

	public User accounts(List<Account> collect) {
		this.accounts = collect;
		return this;
	}

	public User authToken(String authToken) {
		this.setSessionToken(authToken);
		return this;
	}

	public User username(String username2) {
		this.setUsername(username2);
		return this;
	}

	public User agentId(Long agentId2) {
		this.agentId = agentId2;
		return this;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

}