package io.biologeek.expenses.controller;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect
public class ExceptionWrapper extends Exception {

	private String message;
	private String translationKey;
	private String exceptionClass;

	public ExceptionWrapper(Exception e) {
		this.exceptionClass = e.getClass().getName();
		this.message = e.getMessage();
		this.translationKey = e.getMessage().split(".").length > 1 ? e.getMessage() : null;
	}

	public ExceptionWrapper() {
		super();
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getTranslationKey() {
		return translationKey;
	}

	public void setTranslationKey(String translationKey) {
		this.translationKey = translationKey;
	}

	public String getExceptionClass() {
		return exceptionClass;
	}

	public void setExceptionClass(String exceptionClass) {
		this.exceptionClass = exceptionClass;
	}

}
