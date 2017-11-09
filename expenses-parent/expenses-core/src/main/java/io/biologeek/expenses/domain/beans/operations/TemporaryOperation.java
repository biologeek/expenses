package io.biologeek.expenses.domain.beans.operations;

import io.biologeek.expenses.exceptions.BusinessException;

/**
 * A {@link TemporaryOperation} is an operation at a moment that will have null
 * balance at the end.
 * 
 * It implies a first exchange, e.g : lending 100€ to a friend. <br>
 * <br>
 * At that time you may want your friend to give you back the money. - STATE
 * LENDED Once that happened, the operation has 0 € amount as all lended money
 * is back in your wallet. - STATE SOLVED <br>
 * <br>
 * It is composed of several operations. First when you lend money, an automatic
 * {@link UsualOperation} decreases your account with lended sum. <br>
 * <br>
 * As you can be reimbursed once or several times, each reimbursment will
 * increase back the amount until it reaches 0 € or user decides it.
 * 
 * @author xavier
 *
 */
public class TemporaryOperation extends Operation implements Temporary {
	/**
	 * Method that will allow to solve the temporary operation. For example
	 * reimbursing a debt, a loan, ...
	 * 
	 * @param sum
	 *            trasferred sum
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
