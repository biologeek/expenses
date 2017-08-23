package io.biologeek.expenses.api.beans.charts;

import java.util.Collections;

public class EmptyXYChartData extends XYChartData {

	EmptyXYChartData(String title) {
		super();
		this.setTitle(title);
		this.setData(Collections.<XYChartPoint> emptyList());
	}
}
