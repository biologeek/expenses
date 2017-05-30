package io.biologeek.expenses.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;

import io.biologeek.expenses.api.beans.Operation;
import io.biologeek.expenses.api.beans.charts.XYChartData;
import io.biologeek.expenses.api.beans.charts.XYChartData.XYChartPoint;
import io.biologeek.expenses.domain.beans.DailyBalance;
import io.biologeek.expenses.domain.beans.operations.RegularOperation;

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
	
	public static ResponseEntity<XYChartData> convertToXYChartData(
			List<DailyBalance> operations, String title, String xLabel, String yLabel) {
		XYChartData chart = new XYChartData()//
				.title(title)//
				.xLabel(xLabel)//
				.yLabel(yLabel)//
				.data(operations.stream().map(OperationToApiConverter::convertToXYChartPoint).collect(Collectors.toList()));
		
		
		return null;
	}
	
	
	/**
	 * Converts a {@link DailyBalance}
	 * @param operation
	 * @return
	 */
	public static XYChartPoint convertToXYChartPoint(DailyBalance operation){
		XYChartPoint point = new XYChartData.XYChartPoint();
		point.setLabel(null);
		point.setX(operation.getBalanceDate().getTime());
		point.setY(operation.getBalanceValue().doubleValue());
		return null;
	}
}
