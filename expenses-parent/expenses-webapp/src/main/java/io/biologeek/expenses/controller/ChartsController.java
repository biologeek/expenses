package io.biologeek.expenses.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.biologeek.expenses.api.beans.OperationType;
import io.biologeek.expenses.api.beans.charts.XYChartData;
import io.biologeek.expenses.converter.OperationToApiConverter;
import io.biologeek.expenses.services.OperationService;

@Controller
@RequestMapping("/charts")
public class ChartsController {

	@Autowired
	OperationService operationService;
	

	@RequestMapping("/daily/account/{account}")
	public ResponseEntity<XYChartData> getDailyChart(//
			@PathVariable("account") Long account,//
			@RequestParam("types") List<OperationType> types, //
			@RequestParam("begin") Date begin,//
			@RequestParam("end") Date end) {
		
		// Default is all types
		if (types == null || types.size() == 0)
			types = Arrays.asList(OperationType.values());
		
		List<io.biologeek.expenses.domain.beans.operations.OperationType> operationTypes = types.stream()//
				.map(t->io.biologeek.expenses.domain.beans.operations.OperationType.valueOf(t.name()))//
				.collect(Collectors.toList());
		
		return OperationToApiConverter.convertToXYChartData(operationService.getDailyBalanceForPeriod(account, begin, end, operationTypes),//
				"charts.daily.title", "charts.x.label", "chart.y.label");
	}
	
	
	@RequestMapping("/category/account/{account}")
	public ResponseEntity<XYChartData> getCategoryChart(//
			@PathVariable("account") Long account,//
			@RequestParam("types") List<OperationType> types, //
			@RequestParam("begin") Date begin,//
			@RequestParam("end") Date end) {
		
		// Default is all types
		if (types == null || types.size() == 0)
			types = Arrays.asList(OperationType.values());
		
		List<io.biologeek.expenses.domain.beans.operations.OperationType> operationTypes = types.stream()//
				.map(t->io.biologeek.expenses.domain.beans.operations.OperationType.valueOf(t.name()))//
				.collect(Collectors.toList());
		
		return OperationToApiConverter.convertToXYChartData(operationService.getCategoryBalanceForAccount(account, begin, end, operationTypes),//
				"charts.daily.title", "charts.x.label", "chart.y.label");
	}
}
