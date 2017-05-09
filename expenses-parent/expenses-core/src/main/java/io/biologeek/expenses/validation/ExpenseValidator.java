package io.biologeek.expenses.validation;

import java.util.List;

import io.biologeek.expenses.domain.beans.Category;
import io.biologeek.expenses.domain.beans.operations.Expense;
import io.biologeek.expenses.exceptions.BusinessException;

public class ExpenseValidator {
	public static void validateCategory(Expense expense, List<Category> categories) {

	}

	public static void validateExpense(Expense expense) throws BusinessException {
		if (expense.getAccount() == null)
			throw new BusinessException("expense.not_attached");
		if (expense.getCurrency() == null)
			throw new BusinessException("expense.currency_null");
		if (expense.getAmount() == null)
			throw new BusinessException("expense.amount_null");
	}
}
