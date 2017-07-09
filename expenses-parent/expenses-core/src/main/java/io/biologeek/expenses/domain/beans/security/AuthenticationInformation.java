package io.biologeek.expenses.domain.beans.security;

import javax.persistence.Embeddable;

/**
 * Object that stores informations that are necesary for the athentication of a user
 *
 */
@Embeddable
public class AuthenticationInformation {

	private String login;
	private String password;

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
