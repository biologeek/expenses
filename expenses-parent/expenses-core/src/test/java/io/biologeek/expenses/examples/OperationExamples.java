package io.biologeek.expenses.examples;

import java.util.Currency;

import io.biologeek.expenses.domain.beans.operations.Operation;
import io.biologeek.expenses.domain.beans.operations.OperationType;
import io.biologeek.expenses.utils.DateUtils;

public class OperationExamples {

	

	public static Operation anExpense(){
		Operation ope1 = new Operation();
		ope1.setAmount(10D);
		ope1.setCategory(CategoryExamples.aHomeCategory());
		ope1.setCreationDate(DateUtils.dateFromArgs(2017, 05, 01));
		ope1.setEffectiveDate(DateUtils.dateFromArgs(2017, 05, 01));
		ope1.setCurrency(Currency.getInstance("EUR"));		
		return ope1;
	}
	public static Operation anOperation(Double amount){
		Operation ope1 = new Operation();
		ope1.setAmount(amount);
		ope1.setCategory(CategoryExamples.aHomeCategory());
		ope1.setCreationDate(DateUtils.dateFromArgs(2017, 05, 01));
		ope1.setEffectiveDate(DateUtils.dateFromArgs(2017, 05, 01));
		ope1.setCurrency(Currency.getInstance("EUR"));
		ope1.setOperationType(amount.doubleValue() < 0 ? OperationType.EXPENSE : OperationType.INCOME);
		
		return ope1;
	}
}
