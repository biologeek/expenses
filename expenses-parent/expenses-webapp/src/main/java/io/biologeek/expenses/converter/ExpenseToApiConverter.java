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

		res.setAccount(AccountToApiConverter.convert(toConvert));
		res.setAmount(toConvert.getAmount());
		res.setBeneficiary(UserConverter.convert(toConvert.getBeneficiary()));
		res.setEmitter(UserConverter.convert(toConvert.getEmitter()));
		res.setCategory(CategoryToApiConverter.convert(toConvert.getCategory()));
		res.setCreationDate(toConvert.getCreationDate());
		res.setUpdateDate(toConvert.getUpdateDate());
		res.setCurrency(toConvert.getCurrency().getCurrencyCode());
		res.setId(toConvert.getId());
		res.setVersion(toConvert.getVersion());

		return res;
	}

}
