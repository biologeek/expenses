package io.biologeek.expenses.controller;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect
public class ExceptionWrapper {

	private String message;
	private String translationKey;
	private String exceptionClass;

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
