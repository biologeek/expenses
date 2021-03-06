package io.biologeek.expenses.domain.beans;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import io.biologeek.expenses.domain.beans.security.AuthenticationInformation;

@javax.persistence.Entity
@Table(schema = "public", name = "user")
public class RegisteredUser extends Person implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = -348165437223530844L;
	@Embedded
	private AuthenticationInformation authentication;
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "owner")
	private List<Account> accounts;
	private String roles;
	@Column(nullable = true)
	private Boolean isActive = false;


	public String getRoles() {
		return roles;
	}

	public void setRoles(String role) {
		this.roles = role;
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

	public void setAge(int age) {
		this.age = age;
	}

	public String getEmail() {
		return mailAddress;
	}

	public void setEmail(String email) {
		this.mailAddress = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public List<Account> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<Account> accounts) {
		this.accounts = accounts;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Set<GrantedAuthority> auth = new HashSet<>();
		for (String role : roles.split(";")) {
			auth.add(new SimpleGrantedAuthority(role));
		}
		return auth;
	}

	@Override
	public String getUsername() {
		return authentication == null ? null : authentication.getLogin();
	}

	public String getLogin() {
		return getUsername();
	}

	public void setLogin(String login) {
		if (this.authentication == null)
			this.authentication = new AuthenticationInformation();
		this.authentication.setLogin(login);
	}

	@Override
	public boolean isAccountNonExpired() {
		return isActive;
	}

	@Override
	public boolean isAccountNonLocked() {
		return isActive;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return isActive;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	@Override
	public boolean isEnabled() {
		return isActive;
	}

	@Override
	public String getPassword() {
		return authentication == null ? null : authentication.getPassword();
	}

	public void setPassword(String password) {
		if (authentication == null)
			authentication = new AuthenticationInformation();
		authentication.setPassword(password);
	}

	public AuthenticationInformation getAuthentication() {
		return authentication;
	}

	public void setAuthentication(AuthenticationInformation authentication) {
		this.authentication = authentication;
	}

	@Override
	public String toString() {
		return "RegisteredUser [authentication=" + authentication + ", accounts=" + accounts + ", roles=" + roles
				+ ", isActive=" + isActive + "]";
	}
}
