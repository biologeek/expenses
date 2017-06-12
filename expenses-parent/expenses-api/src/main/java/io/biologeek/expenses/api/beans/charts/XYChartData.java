package io.biologeek.expenses.api.beans.charts;

import java.util.List;

/**
 * Represents a time-based chart as a list of points determined by their (x, y)
 * position and eventually a label.
 * 
 * @author xavier
 *
 */
public class XYChartData extends AbstractChartDataset {
	private List<XYChartPoint> data;

	public List<XYChartPoint> getData() {
		return data;
	}

	public void setData(List<XYChartPoint> data) {
		this.data = data;
	}

	public XYChartData data(List<XYChartPoint> data) {
		this.data = data;
		return this;
	}

	public static class XYChartPoint {
		private double x;
		private double y;
		private String label;

		public double getX() {
			return x;
		}

		public void setX(double x) {
			this.x = x;
		}

		public double getY() {
			return y;
		}

		public void setY(double y) {
			this.y = y;
		}

		public String getLabel() {
			return label;
		}

		public void setLabel(String label) {
			this.label = label;
		}
	}
}
