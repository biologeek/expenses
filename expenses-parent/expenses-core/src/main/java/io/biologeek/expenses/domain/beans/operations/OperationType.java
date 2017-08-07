package io.biologeek.expenses.domain.beans.operations;

public enum OperationType {
    EXPENSE(-1), INCOME(1), LOAN(1), DEBT(-1), REGULAR_EXPENSE(-1), REGULAR_INCOME(1), REGULAR_LOAN(1), REGULAR_DEBT(-1);
    
    private int sign;

	OperationType(int sign) {
		this.sign = sign;
	}

	public int getSign() {
		return sign;
	}

	public void setSign(int sign) {
		this.sign = sign;
	}
}
