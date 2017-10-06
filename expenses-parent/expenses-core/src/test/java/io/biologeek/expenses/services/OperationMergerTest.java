package io.biologeek.expenses.services;

import java.util.Currency;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import io.biologeek.expenses.domain.beans.operations.Operation;
import io.biologeek.expenses.domain.beans.operations.OperationType;
import io.biologeek.expenses.domain.beans.operations.UsualOperation;
import io.biologeek.expenses.examples.CategoryExamples;
import io.biologeek.expenses.examples.OperationExamples;
import io.biologeek.expenses.utils.DateUtils;

public class OperationMergerTest {

	private OperationMerger sut;

	@Before
	public void init() {	
		sut = new OperationMerger();
	}
	
	@Test
	public void shouldMergeAllProperties() {
		Operation op = OperationExamples.anExpense();
		
		Operation merged = sut.merge(op, new UsualOperation().id(1L));
		

		Assert.assertEquals(10D, merged.getAmount(), 0.1);
		Assert.assertEquals(OperationType.EXPENSE, merged.getOperationType());
		Assert.assertEquals(1, merged.getVersion());
		Assert.assertTrue(DateUtils.areSameDate(DateUtils.benginningOfDay(2017, 05, 01), merged.getCreationDate()));
		Assert.assertTrue(DateUtils.areSameDate(DateUtils.benginningOfDay(2017, 05, 01), merged.getEffectiveDate()));
		Assert.assertEquals(CategoryExamples.aHomeCategory(), merged.getCategory());
		Assert.assertEquals(Currency.getInstance("EUR"), merged.getCurrency());
		Assert.assertEquals(1L, merged.getId().longValue());
	}
}
