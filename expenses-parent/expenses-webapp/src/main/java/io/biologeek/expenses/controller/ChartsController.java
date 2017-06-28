package io.biologeek.expenses.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import io.biologeek.expenses.api.beans.OperationType;
import io.biologeek.expenses.api.beans.charts.EmptyXYChartData;
import io.biologeek.expenses.api.beans.charts.PieChartData;
import io.biologeek.expenses.api.beans.charts.XYChartData;
import io.biologeek.expenses.converter.OperationToApiConverter;
import io.biologeek.expenses.domain.beans.balances.CategoryBalance;
import io.biologeek.expenses.domain.beans.balances.DailyBalances;
import io.biologeek.expenses.domain.beans.balances.FullPeriodicBalance;
import io.biologeek.expenses.services.OperationService;


@Controller
@RequestMapping("/charts/{account}")
public class ChartsController {

	private OperationService operationService;

	/**
	 * Method used to retrieve data for time-based chart (e.g. for a line chart
	 * that shows expenses and incomes evolutions over a month)
	 * 
	 * @param account the account from which 
	 * @param types
	 * @param begin
	 * @param end
	 * @return
	 */
	@RequestMapping("/amounts/{unitConstant}/")
	public ResponseEntity<? extends XYChartData> getOperationsForPeriod(@PathParam("account") long account
			, @PathParam("unitConstant") String unitConstant
			, @QueryParam("begin") Date begin, @QueryParam("end") Date end
			, @QueryParam("types") OperationType... types) {
		
		DailyBalances operations = operationService.getDailyBalancesForPeriod(account, unitConstant, begin,
				end, convertOperationType(types));

		if (operations.isEmpty())
			return new ResponseEntity<XYChartData>(HttpStatus.NO_CONTENT);
		
		return ResponseEntity.ok(
				OperationToApiConverter.convertToXYChartData(operations, "chart.xy.time.title", "chart.xy.time.x.label",
				"chart.xy.time.y.label"));
	}
	
	public ResponseEntity<PieChartData> getOperationsByCategoryForPeriod(@PathParam("account") long account
			, @PathParam("unitConstant") String unitConstant
			, @QueryParam("begin") Date begin, @QueryParam("end") Date end
			, @QueryParam("types") OperationType... types){
		

		List<CategoryBalance> balance = operationService.ca(account, unitConstant, begin,
				end, convertOperationType(types));

		if (operations.isEmpty())
			return new ResponseEntity<XYChartData>(HttpStatus.NO_CONTENT);
		
		return ResponseEntity.ok(
				OperationToApiConverter.convertToXYChartData(operations, "chart.xy.time.title", "chart.xy.time.x.label",
				"chart.xy.time.y.label"));
		
	}
	
	
	/**
	 * 
	 * @param types
	 * @return
	 */
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
