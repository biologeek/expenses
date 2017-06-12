package io.biologeek.expenses.services;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import io.biologeek.expenses.domain.beans.balances.FullPeriodicBalance;
import io.biologeek.expenses.domain.beans.operations.Operation;
import io.biologeek.expenses.examples.OperationExamples;

@RunWith(SpringJUnit4ClassRunner.class)
public class OperationServiceTest {

	OperationService sut;
	@Test
	public void shouldBuildFullBalanceWithCategoryDetail() {
		List<Operation> operations = new ArrayList<>();
		operations.add(OperationExamples.anExpense());
		operations.add(OperationExamples.anOperation(10D));
		
		FullPeriodicBalance balance = sut.buildFullBalanceWithCategoryDetail(operations);
		Assert.assertTrue(balance.getDailyBalances().get(index)
		
		
		
	}

}
