package io.biologeek.expenses.domain.beans.operations;

import io.biologeek.expenses.exceptions.BusinessException;

public interface Temporary {
	/**
	 * Method that will allow to solve the temporary operation. For example
	 * reimbursing a debt, a loan, ...
	 * 
	 * @param sum trasferred sum
	 * @throws BusinessException 
	 */
	public void solveTemporaryOperation(Double sum) throws BusinessException;
}
