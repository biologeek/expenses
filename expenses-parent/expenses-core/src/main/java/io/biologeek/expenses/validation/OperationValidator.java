package io.biologeek.expenses.validation;

import java.util.List;

import io.biologeek.expenses.domain.beans.Category;
import io.biologeek.expenses.domain.beans.operations.Operation;
import io.biologeek.expenses.exceptions.BusinessException;
import io.biologeek.expenses.exceptions.ValidationException;

public class OperationValidator {
	public static void validateCategory(Operation operation, List<Category> categories) {

	}

	public static void validateOperation(Operation operation) throws ValidationException {
		if (operation.getAccount() == null)
			throw new ValidationException("operation.not_attached");
		if (operation.getCurrency() == null)
			throw new ValidationException("operation.currency_null");
		if (operation.getAmount() == null)
			throw new ValidationException("operation.amount_null");
		if (operation.getCategory() == null)
			throw new ValidationException("operation.no_category");
	}
}
