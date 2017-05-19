package io.biologeek.expenses.converter;

import java.util.ArrayList;
import java.util.List;

import io.biologeek.expenses.api.beans.Operation;

public class OperationToApiConverter {
	public static List<Operation> convert(List<io.biologeek.expenses.domain.beans.operations.Operation> toConvert) {
		List<Operation> result = new ArrayList<>();
		for (io.biologeek.expenses.domain.beans.operations.Operation op : toConvert) {
			switch (op.getClass().getSimpleName()) {
			case "Expense":
				result.add(ExpenseToApiConverter.convert((io.biologeek.expenses.domain.beans.operations.Expense) op));
				break;
			case "Income":
				result.add(IncomeToApiConverter.convert((io.biologeek.expenses.domain.beans.operations.Income) op));
				break;

			}
		}
		return null;
	}
	public static io.biologeek.expenses.api.beans.Operation convert(
			io.biologeek.expenses.domain.beans.operations.Operation toConvert) {
				return null;
		
		
	}

	public static io.biologeek.expenses.api.beans.Operation convert(
			io.biologeek.expenses.domain.beans.operations.Operation toConvert, Operation res) {

		res.setAccount(AccountToApiConverter.convert(toConvert.getAccount()));
		res.setAmount(toConvert.getAmount());
		res.setBeneficiary(UserConverter.convert(toConvert.getBeneficiary()));
		res.setEmitter(UserConverter.convert(toConvert.getEmitter()));
		res.setCategory(CategoryToApiConverter.convert(toConvert.getCategory()));
		res.setCreationDate(toConvert.getCreationDate());
		res.setUpdateDate(toConvert.getUpdateDate());
		res.setId(toConvert.getId());
		res.setVersion(toConvert.getVersion());

		return res;
	}
}
