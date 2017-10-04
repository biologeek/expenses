package io.biologeek.expenses.converter;

import io.biologeek.expenses.data.converters.CurrencyConverter;
import io.biologeek.expenses.domain.beans.Account;
import io.biologeek.expenses.domain.beans.operations.Operation;
import io.biologeek.expenses.domain.beans.operations.OperationType;
import io.biologeek.expenses.domain.beans.operations.RegularOperation;
import io.biologeek.expenses.domain.beans.operations.UsualOperation;

public class OperationToModelConverter {

	/**
	 * Converts common properties of an operation to model
	 * 
	 * @param expense toConvert Operation
	 * @param result converted model bean
	 * @return
	 */
	public static Operation convert(io.biologeek.expenses.api.beans.Operation expense, Operation result) {
		Account account = new Account();
		account.setId(expense.getAccount());
		result.setId(expense.getId());
		result.setAccount(account);
		result.setAmount(expense.getAmount().doubleValue());
		result.setCategory(CategoryConverter.convert(expense.getCategory()));
		result.setCreationDate(expense.getCreationDate());
		result.setUpdateDate(expense.getUpdateDate());
		result.setCurrency(new CurrencyConverter().convertToEntityAttribute(expense.getCurrency()));
		result.setDescription(expense.getDescription());
		result.setEffectiveDate(expense.getEffectiveDate());
		if (expense.getType() != null)
			result.setOperationType(OperationType.valueOf(expense.getType().name()));
		return result;
	}

	public static RegularOperation convert(io.biologeek.expenses.api.beans.RegularOperation expense) {
		RegularOperation result = new RegularOperation();
		result = (RegularOperation) convert(expense, result);
		result.setInterval(IntervalConverter.convert(expense.getInterval()));		
		return result;
	}
	
	public static UsualOperation convert(io.biologeek.expenses.api.beans.Operation op) {
		UsualOperation res = new UsualOperation();
		return (UsualOperation) convert(op, res);
	}

}
