package io.biologeek.expenses.services;

import java.util.ArrayList;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;
import java.util.Currency;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import io.biologeek.expenses.domain.beans.balances.BalanceUnit;
import io.biologeek.expenses.domain.beans.operations.Operation;
import io.biologeek.expenses.examples.OperationExamples;
import io.biologeek.expenses.utils.DateUtils;

@RunWith(MockitoJUnitRunner.class)
public class OperationServiceTest {

	CurrencyDelegate currecyDelegate;
	
	OperationService sut;
	
	
	@Before
	public void before() {		
		sut = new OperationService();
		initMocks();
		initCommonBehaviour();
	}
	public void initMocks(){
		currecyDelegate = mock(CurrencyDelegateImpl.class);
	}
	
	public void initCommonBehaviour() {
		sut.currrencyDelegate = currecyDelegate;
	
		when(currecyDelegate.convert(anyDouble(), any(Currency.class), any(Currency.class)))//
			.thenReturn(5.22D);
	}
	
	@Test
	public void shouldBuildUnitWithNoCurrencyConversion() {
		SortedSet<Operation> operations = new TreeSet<>();
		operations.add(OperationExamples.anOperation(10D, DateUtils.dateFromArgs(2017, 05, 01, 12, 0, 0)));
		operations.add(OperationExamples.anOperation(-1.2D, DateUtils.dateFromArgs(2017, 05, 01, 13, 0, 0)));
		operations.add(OperationExamples.anOperation(1.6D, DateUtils.dateFromArgs(2017, 05, 01, 23, 0, 0)));
		
		BalanceUnit balance = sut.buildBalanceForUnitOfTime(operations, DateUtils.benginningOfDay(2017, 05, 01), DateUtils.endOfTheDay(2017, 05, 01));
		
		Assert.assertEquals(10.4, balance.getBalanceValue());
	}
	
	@Test
	public void shouldBuildUnitWithNoCurrencyConversionAt23h59() {
		SortedSet<Operation> operations = new TreeSet<>();
		operations.add(OperationExamples.anOperation(10D, DateUtils.midDay(2017, 05, 01)));
		operations.add(OperationExamples.anOperation(-1.2D, DateUtils.dateFromArgs(2017, 05, 01, 13, 0, 0)));
		operations.add(OperationExamples.anOperation(1.6D, DateUtils.endOfTheDay(2017, 05, 01)));
		
		BalanceUnit balance = sut.buildBalanceForUnitOfTime(operations, DateUtils.benginningOfDay(2017, 05, 01), DateUtils.endOfTheDay(2017, 05, 01));
		
		Assert.assertEquals(10.4, balance.getBalanceValue());
	}
	
	@Test
	public void shouldBuildUnitWithNoCurrencyConversionAtBeginningOfDay() {
		SortedSet<Operation> operations = new TreeSet<>();
		operations.add(OperationExamples.anOperation(10D, DateUtils.midDay(2017, 05, 01)));
		operations.add(OperationExamples.anOperation(-1.2D, DateUtils.dateFromArgs(2017, 05, 01, 13, 0, 0)));
		operations.add(OperationExamples.anOperation(1.6D, DateUtils.benginningOfDay(2017, 05, 01)));
		
		BalanceUnit balance = sut.buildBalanceForUnitOfTime(operations, DateUtils.benginningOfDay(2017, 05, 01), DateUtils.endOfTheDay(2017, 05, 01));
		
		Assert.assertEquals(10.4, balance.getBalanceValue());
	}
	
	@Test
	public void shouldBuildUnitWithNoCurrencyConversionAnd1WrongDate() {
		SortedSet<Operation> operations = new TreeSet<>();
		operations.add(OperationExamples.anOperation(10D, DateUtils.midDay(2017, 05, 01)));
		operations.add(OperationExamples.anOperation(-1.2D, DateUtils.dateFromArgs(2017, 05, 01, 13, 0, 0)));
		operations.add(OperationExamples.anOperation(1.6D, DateUtils.benginningOfDay(2017, 05, 02)));// WRONG DATE
		
		BalanceUnit balance = sut.buildBalanceForUnitOfTime(operations, DateUtils.benginningOfDay(2017, 05, 01), DateUtils.endOfTheDay(2017, 05, 01));
		
		Assert.assertEquals(8.8, balance.getBalanceValue());
	}
}
