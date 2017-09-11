package io.biologeek.expenses.examples;

import java.util.Calendar;
import java.util.Currency;
import java.util.Date;

import io.biologeek.expenses.domain.beans.Category;
import io.biologeek.expenses.domain.beans.Interval;
import io.biologeek.expenses.domain.beans.operations.Operation;
import io.biologeek.expenses.domain.beans.operations.OperationType;
import io.biologeek.expenses.domain.beans.operations.RegularOperation;
import io.biologeek.expenses.domain.beans.operations.UsualOperation;
import io.biologeek.expenses.utils.DateUtils;

public class OperationExamples {

	

	public static Operation anExpense(){
		Operation ope1 = new UsualOperation();
		ope1.setAmount(-10D);
		ope1.setOperationType(OperationType.EXPENSE);
		ope1.setCategory(CategoryExamples.aHomeCategory());
		ope1.setCreationDate(DateUtils.benginningOfDay(2017, 05, 01));
		ope1.setEffectiveDate(DateUtils.benginningOfDay(2017, 05, 01));
		ope1.setCurrency(Currency.getInstance("EUR"));		
		return ope1;
	}
	public static Operation anOperation(Double amount, Date date){
		Operation ope1 = new UsualOperation();
		ope1.setAmount(amount);
		ope1.setCategory(CategoryExamples.aHomeCategory());
		ope1.setCreationDate(date);
		ope1.setEffectiveDate(date);
		ope1.setCurrency(Currency.getInstance("EUR"));
		ope1.setOperationType(amount.doubleValue() < 0 ? OperationType.EXPENSE : OperationType.INCOME);
		
		return ope1;
	}
	public static Operation anOperationInDollars(Double amount){
		Operation ope1 = new UsualOperation();
		ope1.setAmount(amount);
		ope1.setCategory(CategoryExamples.aHomeCategory());
		ope1.setCreationDate(DateUtils.benginningOfDay(2017, 05, 01));
		ope1.setEffectiveDate(DateUtils.benginningOfDay(2017, 05, 01));
		ope1.setCurrency(Currency.getInstance("USD"));
		ope1.setOperationType(amount.doubleValue() < 0 ? OperationType.EXPENSE : OperationType.INCOME);
		
		return ope1;
	}
	
	
	
	public static RegularOperation aRegularOperation() {
		RegularOperation reg = new RegularOperation();
		
		reg.setAmount(1000D);
		reg.setCategory(CategoryExamples.aHomeCategory());
		reg.setCreationDate(DateUtils.benginningOfDay(2017, 05, 01));
		reg.setInterval(anInterval());
		reg.setCurrency(Currency.getInstance("EUR"));
		reg.setDescription("Mon salaire");
		reg.setId(1L);
		reg.setOperationType(OperationType.INCOME);		
		return reg;
	}
	private static Interval anInterval() {
		Interval interval = new Interval();
		interval.setFirstDate(DateUtils.benginningOfDay(2017, 0, 01));
		interval.setLastDate(DateUtils.benginningOfDay(2017, 04, 01));
		interval.setInterval(1);
		interval.setUnit(Calendar.MONTH);
		return interval;
	}
}
