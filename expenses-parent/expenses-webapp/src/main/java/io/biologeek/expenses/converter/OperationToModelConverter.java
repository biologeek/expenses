package io.biologeek.expenses.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.biologeek.expenses.data.converters.CurrencyConverter;
import io.biologeek.expenses.domain.beans.Account;
import io.biologeek.expenses.domain.beans.operations.Operation;
import io.biologeek.expenses.domain.beans.operations.OperationType;
import io.biologeek.expenses.domain.beans.operations.RegularOperation;
import io.biologeek.expenses.domain.beans.operations.UsualOperation;

@Component
public class OperationToModelConverter {

	@Autowired
	private UserConverter userConverter;

	/**
	 * Converts common properties of an operation to model
	 * 
	 * @param expense toConvert Operation
	 * @param result converted model bean
	 * @return
	 */
	public Operation convert(io.biologeek.expenses.api.beans.Operation expense, Operation result) {
		Account account = new Account();
		account.setId(expense.getAccount());
		result.setId(expense.getId());
		result.setAccount(account);
		result.setAmount(expense.getAmount() == null ? 0 : expense.getAmount().doubleValue());
		result.setCategory(CategoryToModelConverter.convert(expense.getCategory()));
		result.setCreationDate(expense.getCreationDate());
		result.setUpdateDate(expense.getUpdateDate());
		result.setCurrency(new CurrencyConverter().convertToEntityAttribute(expense.getCurrency()));
		result.setDescription(expense.getDescription());
		result.setEffectiveDate(expense.getEffectiveDate());
		result.setBeneficiary(userConverter.toOperationAgent(expense.getBeneficiary()));
		result.setEmitter(userConverter.toOperationAgent(expense.getEmitter()));
		if (expense.getType() != null)
			result.setOperationType(OperationType.valueOf(expense.getType().name()));
		return result;
	}

	public RegularOperation convert(io.biologeek.expenses.api.beans.RegularOperation expense) {
		RegularOperation result = new RegularOperation();
		result = (RegularOperation) convert(expense, result);
		result.setInterval(IntervalConverter.convert(expense.getInterval()));		
		return result;
	}
	
	public UsualOperation convert(io.biologeek.expenses.api.beans.Operation op) {
		UsualOperation res = new UsualOperation();
		return (UsualOperation) convert(op, res);
	}

}
