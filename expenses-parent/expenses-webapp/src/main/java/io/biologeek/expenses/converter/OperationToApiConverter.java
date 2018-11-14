package io.biologeek.expenses.converter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.biologeek.expenses.api.beans.Operation;
import io.biologeek.expenses.api.beans.OperationType;
import io.biologeek.expenses.api.beans.PaginatedOperationsList;
import io.biologeek.expenses.api.beans.charts.ChartJSChartData;
import io.biologeek.expenses.api.beans.charts.PieChartData;
import io.biologeek.expenses.api.beans.charts.XYChartData;
import io.biologeek.expenses.api.beans.charts.XYChartData.XYChartPoint;
import io.biologeek.expenses.beans.OperationList;
import io.biologeek.expenses.domain.beans.Category;
import io.biologeek.expenses.domain.beans.balances.BalanceUnit;
import io.biologeek.expenses.domain.beans.balances.CategoryBalance;
import io.biologeek.expenses.domain.beans.balances.FullPeriodicBalance;

@Component
public class OperationToApiConverter {

	@Autowired
	private UserConverter userConverter;

	public List<Operation> convert(List<io.biologeek.expenses.domain.beans.operations.Operation> toConvert) {
		List<Operation> result = new ArrayList<>();
		for (io.biologeek.expenses.domain.beans.operations.Operation op : toConvert) {
			Operation ope = this.convert(op);
			result.add(ope);
		}
		return result;
	}

	public PaginatedOperationsList convert(OperationList toConvert) {
		PaginatedOperationsList result = new PaginatedOperationsList();
		List<Operation> partial = new ArrayList<>();
		for (io.biologeek.expenses.domain.beans.operations.Operation op : toConvert.getOperations()) {
			Operation ope = this.convert(op);
			partial.add(ope);
		}
		result.setOperations(partial);
		result.setCurrentPage(toConvert.getCurrentPage());
		result.setOperationPerPage(toConvert.getOperationPerPage());
		result.setTotalOperations(toConvert.getTotalOperations());
		result.setTotalPages(toConvert.getTotalPages());
		return result;
	}

	public io.biologeek.expenses.api.beans.Operation convert(
			io.biologeek.expenses.domain.beans.operations.Operation toConvert) {
		return convert(toConvert, new Operation());
	}

	public Operation convert(io.biologeek.expenses.domain.beans.operations.Operation toConvert, Operation res) {

		res.setAccount(toConvert.getAccount().getId());
		res.setAmount(new BigDecimal(toConvert.getAmount()));
		res.setBeneficiary(userConverter.convert(toConvert.getBeneficiary()));
		res.setEmitter(userConverter.convert(toConvert.getEmitter()));
		res.setCategory(CategoryToApiConverter.convertToCategory(toConvert.getCategory()));
		res.setNomenclature(CategoryToApiConverter.convert(toConvert.getCategory()));
		res.setCreationDate(toConvert.getCreationDate());
		res.setUpdateDate(toConvert.getUpdateDate());
		res.setEffectiveDate(toConvert.getEffectiveDate());
		res.setType(OperationType.valueOf(toConvert.getOperationType().name()));
		res.setId(toConvert.getId());
		res.setVersion(toConvert.getVersion());
		res.setCurrency(toConvert.getCurrency().getCurrencyCode());
		res.setDescription(toConvert.getDescription());
		res.setModifiable(toConvert.isModifiable());
		return res;
	}

	public List<XYChartData> convertToXYChartData(List<FullPeriodicBalance> operations, String title, String xLabel,
			String yLabel) {
		List<XYChartData> result = new ArrayList<>();

		for (FullPeriodicBalance e : operations) {
			result.add(convertToXYChartData(e, title, xLabel, yLabel));
		}
		return result;
	}

	public XYChartData convertToXYChartData(FullPeriodicBalance operations, String title, String xLabel,
			String yLabel) {
		XYChartData chart = ((XYChartData) new XYChartData()//
				.title(title)//
				.xLabel(xLabel)//
				.yLabel(yLabel))//
						.data(operations.getDailyBalances().stream().map(this::convertToXYChartPoint)
								.collect(Collectors.toList()));
		return chart;
	}

	/**
	 * Converts a {@link BalanceUnit} to a point of an XY chart
	 * 
	 * @param operation
	 *            the balance of the day with date and value set
	 * @return a point with x an y positions
	 */
	public XYChartPoint convertToXYChartPoint(BalanceUnit operation) {
		XYChartPoint point = new XYChartData.XYChartPoint();
		point.setLabel(null);
		point.setX(operation.getBalanceDate().getTime());
		point.setY(operation.getBalanceValue().doubleValue());
		return null;
	}

	/**
	 * Converts a Category Balance in a Pie chart data object.
	 * 
	 * @param categoryBalanceForAccount
	 * @param title
	 * @param xLabel
	 * @param yLabel
	 * @return
	 */
	public PieChartData convertToPieChartData(CategoryBalance categoryBalanceForAccount, String title, String xLabel,
			String yLabel) {

		PieChartData data = new PieChartData();

		data.setTitle(title);
		data.setxLabel(xLabel);
		data.setyLabel(yLabel);
		for (Entry<Category, BigDecimal> elt : categoryBalanceForAccount.getCategories().entrySet()) {
			data.getValues().put(elt.getKey().getName(), elt.getValue().doubleValue());
		}
		return data;
	}

	public ChartJSChartData convertToChartJS(FullPeriodicBalance balance, String title, String xLabel, String yLabel) {
		ChartJSChartData result = new ChartJSChartData();

		result.getData().put(title, new ArrayList<>());
		result.setxLabel(xLabel);
		result.setyLabel(yLabel);

		for (BalanceUnit bl : balance.getDailyBalances()) {
			result.getData().get(title).add(bl.getBalanceValue().doubleValue());
			result.getLabels().add(new Double(bl.getBalanceDate().getTime()));
		}

		return result;
	}

	public List<ChartJSChartData> convertToChartJS(List<FullPeriodicBalance> balance, String title, String xLabel,
			String yLabel) {
		List<ChartJSChartData> res = new ArrayList<>();
		for (FullPeriodicBalance bl : balance) {
			res.add(convertToChartJS(bl, title, xLabel, yLabel));
		}
		return res;
	}
}
