package io.biologeek.expenses.converter;

import io.biologeek.expenses.api.beans.Income;

public class IncomeToApiConverter {

	public static Income convert(io.biologeek.expenses.domain.beans.operations.Income toConvert) {
		Income res = new Income();
		OperationToApiConverter.convert(toConvert, res);
		
		res.setCurrency(toConvert.getCurrency().getCurrencyCode());

		return res;
	}

}
