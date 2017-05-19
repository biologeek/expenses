package io.biologeek.expenses.converter;

import java.util.List;
import java.util.stream.Collectors;

import io.biologeek.expenses.api.beans.Expense;

public class ExpenseToApiConverter {

	public static List<Expense> convert(List<io.biologeek.expenses.domain.beans.operations.Expense> toConvert) {
		return toConvert.stream()//
				.map(ExpenseToApiConverter::convert)//
				.collect(Collectors.toList());
	}

	public static Expense convert(io.biologeek.expenses.domain.beans.operations.Expense toConvert) {
		Expense res = new Expense();
		OperationToApiConverter.convert(toConvert, res);
		res.setCurrency(toConvert.getCurrency().getCurrencyCode());
		return res;
	}

}
