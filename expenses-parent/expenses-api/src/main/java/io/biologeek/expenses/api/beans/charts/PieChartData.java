package io.biologeek.expenses.api.beans.charts;

import java.util.Map;

public class PieChartData extends AbstractChartDataset {

	Map<String, Double> values;

	public Map<String, Double> getValues() {
		return values;
	}

	public void setValues(Map<String, Double> values) {
		this.values = values;
	}
}
