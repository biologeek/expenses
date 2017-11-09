package io.biologeek.expenses.beans;

import io.biologeek.expenses.domain.beans.operations.Operation;
import io.biologeek.expenses.domain.beans.operations.OperationType;
import io.biologeek.expenses.domain.beans.operations.RegularOperation;
import io.biologeek.expenses.domain.beans.operations.UsualOperation;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;

public class OperationTest {

	@Test
	public void shouldBeModifiable_givenUsual(){
		
		Operation op = new UsualOperation();
		op.setOperationType(OperationType.EXPENSE);
		
		Assert.assertTrue(op.isModifiable());
	}

	
	@Test
	public void shouldNotBeModifiable_givenRegularWithChildren(){
		
		Operation op = new RegularOperation();
		op.setChildrenOperations(new ArrayList<>());
		op.getChildrenOperations().add(new UsualOperation());
		op.setOperationType(OperationType.EXPENSE);
		
		Assert.assertTrue(op.isModifiable());
	}
	
	@Test
	public void shouldNotBeModifiable_givenTemporaryWithParent(){
		
		Operation op = new RegularOperation();
		op.setParentOperation(new UsualOperation());
		op.setOperationType(OperationType.EXPENSE);
		
		Assert.assertFalse(op.isModifiable());
	}

}
