package io.biologeek.expenses.services;

import io.biologeek.expenses.domain.beans.operations.Operation;
import io.biologeek.expenses.examples.CategoryExamples;
import io.biologeek.expenses.utils.DateUtils;

import java.util.Currency;

public class OperationServiceTest {

	public void shouldBuildFullBalanceWithCategoryDetail() {
		Operation ope1 = new Operation();
		
		ope1.setAmount(10D);
		ope1.setCategory(CategoryExamples.aHomeCategory());
		ope1.setCreationDate(DateUtils.dateFromArgs(2017, 05, 01));
		ope1.setEffectiveDate(DateUtils.dateFromArgs(2017, 05, 01));
		ope1.setCurrency(Currency.getInstance("EUR"));
		
	}

}
