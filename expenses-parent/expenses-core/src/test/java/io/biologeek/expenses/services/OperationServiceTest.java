package io.biologeek.expenses.services;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyDouble;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.SortedSet;
import java.util.TreeSet;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import io.biologeek.expenses.domain.beans.balances.BalanceUnit;
import io.biologeek.expenses.domain.beans.operations.Operation;
import io.biologeek.expenses.examples.AccountExamples;
import io.biologeek.expenses.examples.OperationExamples;
import io.biologeek.expenses.exceptions.BusinessException;
import io.biologeek.expenses.exceptions.ValidationException;
import io.biologeek.expenses.repositories.OperationsRepository;
import io.biologeek.expenses.utils.DateUtils;

@RunWith(MockitoJUnitRunner.class)
public class OperationServiceTest {

	CurrencyDelegate currecyDelegate;
	
	OperationService sut;
	
	OperationsRepository operationsRepository;
	
	ArgumentCaptor<Double> captor;
	
	
	@Before
	public void before() {		
		sut = new OperationService();
		
		operationsRepository = mock(OperationsRepository.class);
		sut.operationsRepository = operationsRepository;
		captor = ArgumentCaptor.forClass(Double.class);
		initMocks();
		initCommonBehaviour();
	}
	public void initMocks(){
		currecyDelegate = mock(CurrencyDelegateImpl.class);
	}
	
	public void initCommonBehaviour() {
		sut.currrencyDelegate = currecyDelegate;
	
		when(currecyDelegate.convert(anyDouble(), any(Currency.class), any(Currency.class)))//
			.thenAnswer(new Answer<Double>() {

				@Override
				public Double answer(InvocationOnMock invocation) throws Throwable {
					return (Double) invocation.getArguments()[0];
				}
				
			});
	}
	
	@Test
	public void shouldBuildUnitWithNoCurrencyConversion() {
		SortedSet<Operation> operations = new TreeSet<>();
		operations.add(OperationExamples.anOperation(10D, DateUtils.dateFromArgs(2017, 05, 01, 12, 0, 0)));
		operations.add(OperationExamples.anOperation(-1.2D, DateUtils.dateFromArgs(2017, 05, 01, 13, 0, 0)));
		operations.add(OperationExamples.anOperation(1.6D, DateUtils.dateFromArgs(2017, 05, 01, 23, 0, 0)));
		
		BalanceUnit balance = sut.buildBalanceForUnitOfTime(operations, DateUtils.benginningOfDay(2017, 05, 01), DateUtils.endOfTheDay(2017, 05, 01));
		
		Assert.assertEquals(BigDecimal.valueOf(10.4), balance.getBalanceValue());
	}
	
	@Test
	public void shouldBuildUnitWithNoCurrencyConversionAt23h59() {
		SortedSet<Operation> operations = new TreeSet<>();
		operations.add(OperationExamples.anOperation(10D, DateUtils.midDay(2017, 05, 01)));
		operations.add(OperationExamples.anOperation(-1.2D, DateUtils.dateFromArgs(2017, 05, 01, 13, 0, 0)));
		operations.add(OperationExamples.anOperation(1.6D, DateUtils.endOfTheDay(2017, 05, 01)));
		
		BalanceUnit balance = sut.buildBalanceForUnitOfTime(operations, DateUtils.benginningOfDay(2017, 05, 01), DateUtils.endOfTheDay(2017, 05, 01));
		
		Assert.assertEquals(BigDecimal.valueOf(10.4), balance.getBalanceValue());
	}
	
	@Test
	public void shouldBuildUnitWithNoCurrencyConversionAtBeginningOfDay() {
		SortedSet<Operation> operations = new TreeSet<>();
		operations.add(OperationExamples.anOperation(10D, DateUtils.midDay(2017, 05, 01)));
		operations.add(OperationExamples.anOperation(-1.2D, DateUtils.dateFromArgs(2017, 05, 01, 13, 0, 0)));
		operations.add(OperationExamples.anOperation(1.6D, DateUtils.benginningOfDay(2017, 05, 01)));
		
		BalanceUnit balance = sut.buildBalanceForUnitOfTime(operations, DateUtils.benginningOfDay(2017, 05, 01), DateUtils.endOfTheDay(2017, 05, 01));
		
		Assert.assertEquals(BigDecimal.valueOf(10.4), balance.getBalanceValue());
	}
	
	@Test
	public void shouldBuildUnitWithNoCurrencyConversionAnd1WrongDate() {
		SortedSet<Operation> operations = new TreeSet<>();
		operations.add(OperationExamples.anOperation(10D, DateUtils.midDay(2017, 05, 01)));
		operations.add(OperationExamples.anOperation(-1.2D, DateUtils.dateFromArgs(2017, 05, 01, 13, 0, 0)));
		operations.add(OperationExamples.anOperation(1.6D, DateUtils.benginningOfDay(2017, 05, 02)));// WRONG DATE
		
		BalanceUnit balance = sut.buildBalanceForUnitOfTime(operations, DateUtils.benginningOfDay(2017, 05, 01), DateUtils.endOfTheDay(2017, 05, 01));
		
		Assert.assertEquals(BigDecimal.valueOf(8.8), balance.getBalanceValue());
	}

	@Test
	public void shouldAddExpenseToAccount() throws ValidationException, BusinessException {
		Operation op = OperationExamples.anExpense().id(null);
		op.setAccount(AccountExamples.aPhysicalPersonAccount());
		when(operationsRepository.save(op)).thenReturn(OperationExamples.anExpense().account(AccountExamples.aPhysicalPersonAccount()).id(1L));
		Operation result = sut.addOperationToAccount(op);
		
		Assert.assertEquals(1L, result.getId().longValue());
		
	}

	@Test(expected=ValidationException.class)
	public void shouldNotAddExpenseToAccountGivenNoCategory() throws ValidationException, BusinessException {
		Operation op = OperationExamples.anExpense().id(null);
		op.setCategory(null);
		op.setAccount(AccountExamples.aPhysicalPersonAccount());
		when(operationsRepository.save(op)).thenReturn(OperationExamples.anExpense().account(AccountExamples.aPhysicalPersonAccount()).id(1L));
		Operation result = sut.addOperationToAccount(op);
		
		
	}

	@Test(expected=ValidationException.class)
	public void shouldAddExpenseToAccount_GivenNoAccount() throws ValidationException, BusinessException {
		Operation op = OperationExamples.anExpense().id(null);
		op.setAccount(null);
		when(operationsRepository.save(op)).thenReturn(OperationExamples.anExpense().account(AccountExamples.aPhysicalPersonAccount()).id(1L));
		Operation result = sut.addOperationToAccount(op);
		
		Assert.assertEquals(1L, result.getId().longValue());
		
	}
}
