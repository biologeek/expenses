package io.biologeek.expenses.api.beans;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;


@JsonAutoDetect
/**
 * Bean containing all necesary to login for a user. Note that 
 * @author 20005828
 *
 */
public class AuthenticationActionBean {
	
	String login, password, token;

	public String getToken() {
		return token;
	}

	public void setToken(String sessionToken) {
		this.token = sessionToken;
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
