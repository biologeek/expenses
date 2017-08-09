package io.biologeek.expenses.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;

import io.biologeek.expenses.api.beans.Operation;
import io.biologeek.expenses.api.beans.PaginatedOperationsList;
import io.biologeek.expenses.api.beans.charts.XYChartData;
import io.biologeek.expenses.api.beans.charts.XYChartData.XYChartPoint;
import io.biologeek.expenses.beans.OperationList;
import io.biologeek.expenses.domain.beans.balances.DailyBalance;
import io.biologeek.expenses.domain.beans.balances.FullPeriodicBalance;

public class OperationToApiConverter {
	public static List<Operation> convert(List<io.biologeek.expenses.domain.beans.operations.Operation> toConvert) {
		List<Operation> result = new ArrayList<>();
		for (io.biologeek.expenses.domain.beans.operations.Operation op : toConvert) {
			Operation ope = OperationToApiConverter.convert(op);
			result.add(ope);
		}
		return result;
	}
	public static PaginatedOperationsList convert(OperationList toConvert) {
		PaginatedOperationsList result = new PaginatedOperationsList();
		List<Operation> partial = new ArrayList<>();
		for (io.biologeek.expenses.domain.beans.operations.Operation op : toConvert.getOperations()) {
			Operation ope = OperationToApiConverter.convert(op);
			partial.add(ope);
		}
		result.setOperations(partial);
		result.setCurrentPage(toConvert.getCurrentPage());
		result.setOperationPerPage(toConvert.getOperationPerPage());
		result.setTotalOperations(toConvert.getTotalOperations());
		result.setTotalPages(toConvert.getTotalPages());
		return result;
	}
	public static io.biologeek.expenses.api.beans.Operation convert(
			io.biologeek.expenses.domain.beans.operations.Operation toConvert) {		
				return convert(toConvert, new Operation());		
	}

	public static Operation convert(
			io.biologeek.expenses.domain.beans.operations.Operation toConvert, Operation res) {

		res.setAccount(toConvert.getAccount().getId());
		res.setAmount(toConvert.getAmount());
		res.setBeneficiary(UserConverter.convert(toConvert.getBeneficiary()));
		res.setEmitter(UserConverter.convert(toConvert.getEmitter()));
		res.setCategory(CategoryToApiConverter.convertToCategory(toConvert.getCategory()));
		res.setNomenclature(CategoryToApiConverter.convert(toConvert.getCategory()));
		res.setCreationDate(toConvert.getCreationDate());
		res.setUpdateDate(toConvert.getUpdateDate());
		res.setId(toConvert.getId());
		res.setVersion(toConvert.getVersion());

		return res;
	}
	
	public static ResponseEntity<XYChartData> convertToXYChartData(
			FullPeriodicBalance operations, String title, String xLabel, String yLabel) {
		XYChartData chart = ((XYChartData) new XYChartData()//
				.title(title)//
				.xLabel(xLabel)//
				.yLabel(yLabel))//
				.data(operations.getDailyBalances().stream().map(OperationToApiConverter::convertToXYChartPoint).collect(Collectors.toList()));
		
		
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
