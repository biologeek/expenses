package io.biologeek.expenses.converter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;

import io.biologeek.expenses.api.beans.Operation;
import io.biologeek.expenses.api.beans.charts.PieChartData;
import io.biologeek.expenses.api.beans.charts.XYChartData;
import io.biologeek.expenses.api.beans.charts.XYChartData.XYChartPoint;
import io.biologeek.expenses.domain.beans.Category;
import io.biologeek.expenses.domain.beans.balances.CategoryBalance;
import io.biologeek.expenses.domain.beans.balances.DailyBalances;
import io.biologeek.expenses.domain.beans.balances.DailyBalances.DailyBalance;
import io.biologeek.expenses.domain.beans.balances.FullPeriodicBalance;
import io.biologeek.expenses.domain.beans.operations.OperationType;

public class OperationToApiConverter {
	public static List<Operation> convert(List<io.biologeek.expenses.domain.beans.operations.Operation> toConvert) {
		List<Operation> result = new ArrayList<>();
		for (io.biologeek.expenses.domain.beans.operations.Operation op : toConvert) {
			result.add(OperationToApiConverter.convert((io.biologeek.expenses.domain.beans.operations.Operation) op));
		}
		return result;
	}

	public static Operation convert(io.biologeek.expenses.domain.beans.operations.Operation toConvert) {
		return new Operation()//
				.account(toConvert.getAccount().getId())//
				.amount(toConvert.getAmount())//
				.beneficiary(UserConverter.convert(toConvert.getBeneficiary()))//
				.emitter(UserConverter.convert(toConvert.getEmitter()))//
				.category(CategoryToApiConverter.convert(toConvert.getCategory()))//
				.creationDate(toConvert.getCreationDate())//
				.updateDate(toConvert.getUpdateDate())//
				.id(toConvert.getId())//
				.version(toConvert.getVersion());
	}

	public static io.biologeek.expenses.api.beans.Operation convert(
			io.biologeek.expenses.domain.beans.operations.Operation toConvert, Operation res) {

		res.setAccount(toConvert.getAccount().getId());
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

	public static XYChartData convertToXYChartData(FullPeriodicBalance operations, String title, String xLabel,
			String yLabel) {
		return convertToXYChartData(operations.getDailyBalances(), title, xLabel, yLabel);

	}

	public static XYChartData convertToXYChartData(DailyBalances operations, String title, String xLabel,
			String yLabel) {

		XYChartData chart = ((XYChartData) new XYChartData()//
				.title(title)//
				.xLabel(xLabel)//
				.yLabel(yLabel))//
						.data(operations.stream().map(OperationToApiConverter::convertToXYChartPoint)
								.collect(Collectors.toList()));

		return chart;
	}

	/**
	 * Converts a {@link DailyBalance}
	 * 
	 * @param operation
	 * @return
	 */
	public static XYChartPoint convertToXYChartPoint(DailyBalance operation) {
		XYChartPoint point = new XYChartData.XYChartPoint();
		point.setLabel(null);
		point.setX(operation.getBalanceDate().getTime());
		point.setY(operation.getBalanceValue().doubleValue());
		return null;
	}

	/**
	 * Converts a balance by category to a Pie chart object
	 * 
	 * @param balance
	 * @param title
	 * @param xLabel
	 * @param yLabel
	 * @return
	 */
	public static PieChartData convertToPieChartData(CategoryBalance balance, String title, String xLabel,
			String yLabel) {
		PieChartData result = new PieChartData();

		result.setTitle(title);
		result.setxLabel(xLabel);
		result.setyLabel(yLabel);

		for (Entry<Category, BigDecimal> entry : balance.getCategories().entrySet()) {
			result.getValues().put(entry.getKey().getName(), entry.getValue().doubleValue());
		}
		return result;
	}

	public static class OperationTypeConverter {

		public static List<io.biologeek.expenses.api.beans.OperationType> convert(List<OperationType> asList) {
			return asList.stream()//
					.map(t -> OperationTypeConverter.convert(t))//
					.collect(Collectors.toList());
		}

		public static io.biologeek.expenses.api.beans.OperationType convert(OperationType asList) {
			return new io.biologeek.expenses.api.beans.OperationType(asList.name(), //
					asList.getSign());
		}
	}

}
