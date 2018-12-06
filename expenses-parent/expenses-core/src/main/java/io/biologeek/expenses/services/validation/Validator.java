package io.biologeek.expenses.services.validation;

import io.biologeek.expenses.exceptions.ValidationException;

public interface Validator<T> {
	
	void validate(T toValidate) throws ValidationException;

}
