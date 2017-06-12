package io.biologeek.expenses.validation;

import io.biologeek.expenses.domain.beans.Category;
import io.biologeek.expenses.domain.beans.operations.Operation;
import io.biologeek.expenses.exceptions.BusinessException;

import java.util.List;

public class OperationValidator {
	public static void validateCategory(Operation operation, List<Category> categories) {

	}

	public static void validateOperation(Operation operation) throws BusinessException {
		if (operation.getAccount() == null)
			throw new BusinessException("operation.not_attached");
		if (operation.getCurrency() == null)
			throw new BusinessException("operation.currency_null");
		if (operation.getAmount() == null)
			throw new BusinessException("operation.amount_null");
	}
}
