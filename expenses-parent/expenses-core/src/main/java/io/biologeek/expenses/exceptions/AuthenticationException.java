package io.biologeek.expenses.exceptions;

public class AuthenticationException extends KeyTranslatedException {

	public AuthenticationException(String message) {
		super(message);
	}
}
