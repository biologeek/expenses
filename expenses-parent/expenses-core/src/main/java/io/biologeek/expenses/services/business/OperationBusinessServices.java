package io.biologeek.expenses.services.business;

import java.util.Set;

import io.biologeek.expenses.domain.beans.operations.Operation;
import io.biologeek.expenses.domain.beans.operations.OperationStatus;
import io.biologeek.expenses.domain.beans.operations.UsualOperation;

public abstract class OperationBusinessServices<T extends Operation> {
	public abstract Set<UsualOperation> processOperationAndGenerateConcrete(T operationToProcess);
	
	/**
	 * Cancels and operation. By default, will cancel operation and all its children. 
	 * @param operation to cancel
	 */
	public void cancelOperation(T operation) {
		operation.status(OperationStatus.CANCELLED)//
		.getChildrenOperations()//
			.stream().map(t -> t.status(OperationStatus.CANCELLED));
	}
}
