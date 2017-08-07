package io.biologeek.expenses.exceptions;

public class KeyTranslatedException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 439085319116854357L;
	private String key;

	public KeyTranslatedException(String message) {
		super(message);
		this.key = message;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

}
