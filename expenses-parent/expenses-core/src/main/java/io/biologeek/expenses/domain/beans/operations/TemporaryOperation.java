package io.biologeek.expenses.domain.beans.operations;

public interface TemporaryOperation {
	/**
	 * Method that will allow to solve the temporary operation. For example
	 * reimbursing a debt, a loan, ...
	 * 
	 * @param sum trasferred sum
	 */
	public void solveTemporaryOperation(Double sum);
}
