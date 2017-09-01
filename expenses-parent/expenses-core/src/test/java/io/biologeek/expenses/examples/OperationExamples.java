package io.biologeek.expenses.examples;

import java.util.Currency;
import java.util.Date;

import io.biologeek.expenses.domain.beans.operations.Operation;
import io.biologeek.expenses.domain.beans.operations.OperationType;
import io.biologeek.expenses.utils.DateUtils;

public class OperationExamples {

	

	public static Operation anExpense(){
		Operation ope1 = new Operation();
		ope1.setAmount(-10D);
		ope1.setOperationType(OperationType.EXPENSE);
		ope1.setCategory(CategoryExamples.aHomeCategory());
		ope1.setCreationDate(DateUtils.benginningOfDay(2017, 05, 01));
		ope1.setEffectiveDate(DateUtils.benginningOfDay(2017, 05, 01));
		ope1.setCurrency(Currency.getInstance("EUR"));		
		return ope1;
	}
	public static Operation anOperation(Double amount, Date date){
		Operation ope1 = new Operation();
		ope1.setAmount(amount);
		ope1.setCategory(CategoryExamples.aHomeCategory());
		ope1.setCreationDate(date);
		ope1.setEffectiveDate(date);
		ope1.setCurrency(Currency.getInstance("EUR"));
		ope1.setOperationType(amount.doubleValue() < 0 ? OperationType.EXPENSE : OperationType.INCOME);
		
		return ope1;
	}
	public static Operation anOperationInDollars(Double amount){
		Operation ope1 = new Operation();
		ope1.setAmount(amount);
		ope1.setCategory(CategoryExamples.aHomeCategory());
		ope1.setCreationDate(DateUtils.benginningOfDay(2017, 05, 01));
		ope1.setEffectiveDate(DateUtils.benginningOfDay(2017, 05, 01));
		ope1.setCurrency(Currency.getInstance("USD"));
		ope1.setOperationType(amount.doubleValue() < 0 ? OperationType.EXPENSE : OperationType.INCOME);
		
		return ope1;
	}
}
