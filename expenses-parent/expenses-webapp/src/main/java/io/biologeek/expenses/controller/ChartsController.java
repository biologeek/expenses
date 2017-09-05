package io.biologeek.expenses.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.biologeek.expenses.api.beans.OperationType;
import io.biologeek.expenses.api.beans.charts.AbstractChartDataset;
import io.biologeek.expenses.api.beans.charts.ChartJSChartData;
import io.biologeek.expenses.api.beans.charts.PieChartData;
import io.biologeek.expenses.api.beans.charts.XYChartData;
import io.biologeek.expenses.converter.MultiPatternDateConverter;
import io.biologeek.expenses.converter.OperationToApiConverter;
import io.biologeek.expenses.domain.beans.balances.FullPeriodicBalance;
import io.biologeek.expenses.services.OperationService;
import io.biologeek.expenses.utils.DateTimeUnit;

@Controller
@RequestMapping("/charts")
public class ChartsController {

	@Autowired
	OperationService operationService;
	

	@RequestMapping("/daily/account/{account}/interval/{interval}")
	public ResponseEntity<List<? extends AbstractChartDataset>> getTimeChartForIntervalAndTypes(//
			@PathVariable("account") Long account,//
			@PathVariable("interval") DateTimeUnit interval,//
			@RequestParam("types") List<OperationType> types, //
			@RequestParam("begin") String beginStr,//
			@RequestParam("end") String endStr) {
		return mockDefaultAndReturn();
		// Default is all types
//		if (types == null || types.size() == 0)
//			types = Arrays.asList(OperationType.values());
//		if (interval == null)
//			interval = DateTimeUnit.DAYS;
//		
//		Date begin, end;
//
//		begin = MultiPatternDateConverter.convert(beginStr);
//		end = MultiPatternDateConverter.convert(beginStr);
//		List<io.biologeek.expenses.domain.beans.operations.OperationType> operationTypes = types.stream()//
//				.map(t->io.biologeek.expenses.domain.beans.operations.OperationType.valueOf(t.name()))//
//				.collect(Collectors.toList());
//		List<FullPeriodicBalance> result = operationService.getPeriodicBalanceForPeriodWithoutCategoriesNorSeparatingTypes(account, interval, begin, end, operationTypes);
//		return new ResponseEntity<>(OperationToApiConverter.convertToXYChartData(result,//
//				"charts.daily.title", "charts.x.label", "chart.y.label"), HttpStatus.OK);
	}
	
	private ResponseEntity<List<? extends AbstractChartDataset>> mockDefaultAndReturn() {
		List<ChartJSChartData> data = new ArrayList<>();
		
		ChartJSChartData one = new ChartJSChartData();
		one.setTitle("Title 1");
		one.setxLabel("a xLabel");
		one.setyLabel("a yLabel");
		one.setData(data());
		one.setLabels(Arrays.asList(1D, 2D, 3D, 5D));
		data.add(one);
		
		return new ResponseEntity<>(data, HttpStatus.OK);		
	}

	private Map<String, List<Double>> data() {
		Map<String, List<Double>> map = new HashMap<>();

		map.put("Serie 1", Arrays.asList(20.5D, 3D, -1.4D, 1D));
		map.put("Serie 2", Arrays.asList(-20.5D, -3D, 1.4D, -1D));
		return map;
	}

	/**
	 * Endpoint that builds data necessary for displaying a Pie chart
	 * @param account
	 * @param types
	 * @param begin
	 * @param end
	 * @return
	 */
	@RequestMapping("/category/account/{account}")
	public ResponseEntity<PieChartData> getCategoryChart(//
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
		
		return new ResponseEntity<>(OperationToApiConverter.convertToPieChartData(operationService.getCategoryBalanceForAccount(account, begin, end, operationTypes),//
				"charts.daily.title", "charts.x.label", "chart.y.label"), HttpStatus.OK);
	}
}
