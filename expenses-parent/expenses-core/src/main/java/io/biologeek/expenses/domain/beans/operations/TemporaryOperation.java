package io.biologeek.expenses.domain.beans.operations;

import io.biologeek.expenses.exceptions.BusinessException;

public class TemporaryOperation extends Operation implements Temporary{
	/**
	 * Method that will allow to solve the temporary operation. For example
	 * reimbursing a debt, a loan, ...
	 * 
	 * @param sum trasferred sum
	 * @throws BusinessException 
	 */
	public void solveTemporaryOperation(Double sum) throws BusinessException {
	}

	@Override
	public int compareTo(Operation o) {
		// TODO Auto-generated method stub
		return 0;
	}
}
