package io.biologeek.expenses.converter;

import io.biologeek.expenses.data.converters.CurrencyConverter;
import io.biologeek.expenses.domain.beans.Account;
import io.biologeek.expenses.domain.beans.operations.Operation;
import io.biologeek.expenses.domain.beans.operations.OperationType;

public class OperationToModelConverter {

	public static Operation convert(io.biologeek.expenses.api.beans.Operation expense) {
		Operation result = new Operation();
		Account account = new Account();
		account.setId(expense.getAccount());
		result.setAccount(account);
		result.setAmount(expense.getAmount());
		result.setCategory(CategoryConverter.convert(expense.getCategory()));
		result.setCreationDate(expense.getCreationDate());
		result.setUpdateDate(expense.getUpdateDate());
		result.setCurrency(new CurrencyConverter().convertToEntityAttribute(expense.getCurrency()));
		result.setDescription(expense.getDescription());
		result.setEffectiveDate(expense.getEffectiveDate());
		result.setOperationType(OperationType.valueOf(expense.getType().getName()));
		return result;
	}

}
