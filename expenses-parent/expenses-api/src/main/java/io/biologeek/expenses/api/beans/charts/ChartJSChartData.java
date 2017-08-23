package io.biologeek.expenses.api.beans.charts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;

/**
 * ChartJS specific representation of data to display in chart
 * 
 */
public class ChartJSChartData extends AbstractChartDataset {

	Map<String, List<Double>> data;
	/**
	 * If your label is a date, transform it previously to a timestamp
	 */
	List<Double> labels;
	
	
	public ChartJSChartData() {
		data = new HashMap<>();
		labels = new ArrayList<>();
	}
	public Map<String, List<Double>> getData() {
		return data;
	}
	public void setData(Map<String, List<Double>> data) {
		this.data = data;
	}
	public List<Double> getLabels() {
		return labels;
	}
	public void setLabels(List<Double> labels) {
		this.labels = labels;
	}
}
