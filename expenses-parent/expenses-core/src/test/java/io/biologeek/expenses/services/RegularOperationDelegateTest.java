package io.biologeek.expenses.services;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import io.biologeek.expenses.domain.beans.operations.RegularOperation;
import io.biologeek.expenses.examples.OperationExamples;

@RunWith(MockitoJUnitRunner.class)
public class RegularOperationDelegateTest {

	RegularOperationDelegate sut;	
	RegularOperation op = OperationExamples.aRegularOperation();
	
	@Before
	public void init() {
		sut = new RegularOperationDelegate();
	}
	
	@Test
	public void shouldBuildUsualOperationTree() throws CloneNotSupportedException {
		
		RegularOperation res = sut.buildUsualOperationTree(op);
		
		Assert.assertTrue(res.getConcreteOperations() != null);
		Assert.assertEquals(5, res.getConcreteOperations().size());
		Assert.assertEquals(res.getConcreteOperations().first().getEffectiveDate(), op.getInterval().getFirstDate());
		Assert.assertEquals(res.getConcreteOperations().last().getEffectiveDate(), op.getInterval().getLastDate());
	}
	
}
