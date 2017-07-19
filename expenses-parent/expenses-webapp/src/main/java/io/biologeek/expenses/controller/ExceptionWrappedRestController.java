package io.biologeek.expenses.controller;

import org.springframework.web.bind.annotation.RestController;

import io.biologeek.expenses.exceptions.KeyTranslatedException;

@RestController
/**
 * Wraps errors from application to a nice object sent to API users
 *
 */
public abstract class ExceptionWrappedRestController {

	protected ExceptionWrapper wrapException(KeyTranslatedException ex) {
		ExceptionWrapper result = new ExceptionWrapper();

		result.setMessage(ex.getMessage());
		result.setTranslationKey(ex.getKey());

		Throwable it = null;
		if (ex.getCause() != null) {
			it = ex.getCause();
			while (it.getCause() != null) {
				it = it.getCause();
			}
		}
		if (it == null)
			result.setExceptionClass(ex.getClass().getName());
		else
			result.setExceptionClass(it.getClass().getName());
		return result;
	}
}
