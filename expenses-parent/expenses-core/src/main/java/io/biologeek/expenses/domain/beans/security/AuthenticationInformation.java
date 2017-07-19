package io.biologeek.expenses.domain.beans.security;

import java.util.Date;

import javax.persistence.Embeddable;

/**
 * Object that stores informations that are necesary for the athentication of a
 * user
 *
 */
@Embeddable
public class AuthenticationInformation {

	private String login;
	private String password;
	private String authToken;
	private Date tokenGenerationDate;

	public AuthenticationInformation(String login2, String password2) {
		this.login = login2;
		this.password = password2;
	}
	public AuthenticationInformation() {
	}

	public Date getTokenGenerationDate() {
		return tokenGenerationDate;
	}
	public void setTokenGenerationDate(Date tokenGenerationDate) {
		this.tokenGenerationDate = tokenGenerationDate;
	}
	public String getAuthToken() {
		return authToken;
	}

	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}

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
