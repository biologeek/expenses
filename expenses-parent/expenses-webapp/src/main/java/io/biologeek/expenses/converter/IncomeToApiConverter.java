package io.biologeek.expenses.converter;

import javax.persistence.criteria.CriteriaBuilder.In;

import io.biologeek.expenses.api.beans.Income;
import io.biologeek.expenses.api.beans.Interval;
import io.biologeek.expenses.api.beans.RegularIncome;

public class IncomeToApiConverter {

	public static Income convert(io.biologeek.expenses.domain.beans.operations.Income toConvert) {
		Income res = new Income();
		OperationToApiConverter.convert(toConvert, res);
		
		res.setCurrency(toConvert.getCurrency().getCurrencyCode());

		return res;
	}
	
	public static RegularIncome convert(io.biologeek.expenses.domain.beans.operations.RegularIncome income) {
		
		RegularIncome result = IncomeToApiConverter.convert(income);
		result.setInterval(IncomeToApiConverter.convert(income.getInterval()));
		return result;
		
	}

	private static Interval convert(io.biologeek.expenses.domain.beans.Interval interval) {
		Interval res = new Interval();
		res.setInterval(interval.getInterval());
		res.setUnit(interval.getUnit());
		return res;
	}

}
