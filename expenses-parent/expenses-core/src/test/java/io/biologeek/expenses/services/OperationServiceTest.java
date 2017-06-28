package io.biologeek.expenses.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import io.biologeek.expenses.config.ApplicationConfig;
import io.biologeek.expenses.domain.beans.balances.FullPeriodicBalance;
import io.biologeek.expenses.domain.beans.operations.Operation;
import io.biologeek.expenses.examples.OperationExamples;
import io.biologeek.expenses.exceptions.ModelException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { ApplicationConfig.class })
public class OperationServiceTest {

	OperationService sut;
	
	
	@Before
	public void init(){
		sut = new OperationService();
	}

	@Test
	public void shouldBuildFullBalanceWithCategoryDetail() throws ModelException {
		List<Operation> operations = new ArrayList<>();
		operations.add(OperationExamples.anExpense());
		operations.add(OperationExamples.anOperation(10D));

		FullPeriodicBalance balance = sut.buildFullBalanceWithCategoryDetail(operations);
		Assert.assertTrue(balance.getDailyBalances().size() == 1);
		Assert.assertEquals(BigDecimal.ZERO, balance.getDailyBalances().get(0).getBalanceValue());
	}
	
	

}
