package io.biologeek.expenses.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import io.biologeek.expenses.api.beans.OperationType;
import io.biologeek.expenses.api.beans.charts.PieChartData;
import io.biologeek.expenses.api.beans.charts.XYChartData;
import io.biologeek.expenses.converter.OperationToApiConverter;
import io.biologeek.expenses.domain.beans.balances.FullPeriodicBalance;
import io.biologeek.expenses.domain.beans.balances.FullPeriodicBalance;
import io.biologeek.expenses.services.OperationService;


@Controller
@RequestMapping("/charts")
public class ChartsController {

	private OperationService operationService;

	/**
	 * Method used to retrieve data for time-based chart (e.g. for a line chart
	 * that shows expenses and incomes evolutions over a month)
	 * 
	 * @param account
	 * @param types
	 * @param begin
	 * @param end
	 * @return
	 */
	public ResponseEntity<XYChartData> getOperationsForPeriod(long account, Date begin, Date end,
			OperationType... types) {
		FullPeriodicBalance operations = operationService.getFullBalanceForPeriod(account, begin, end,
				convertOperationType(types));

		return OperationToApiConverter.convertToXYChartData(operations, "chart.xy.time.title", "chart.xy.time.x.label",
				"chart.xy.time.y.label");
	}

	private List<io.biologeek.expenses.domain.beans.operations.OperationType> convertOperationType(
			OperationType... types) {
		return Arrays.asList(types).stream()
				.map(t -> io.biologeek.expenses.domain.beans.operations.OperationType.valueOf(t.name()))
				.collect(Collectors.toList());
	}

	public ResponseEntity<PieChartData> getExpensesForEveryCategory(long account, Date begin, Date end) {
		return null;
	}

}
