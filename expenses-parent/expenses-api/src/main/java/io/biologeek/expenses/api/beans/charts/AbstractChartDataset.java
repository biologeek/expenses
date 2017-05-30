package io.biologeek.expenses.api.beans.charts;

/**
 * All charts have at least a title and 2 "axis". For a line chart, x and y are
 * the classic axis. <br>
 * <br>
 * For a pie chart for example, x labels categories and y what is represented.
 * 
 *
 */
public abstract class AbstractChartDataset {
	String title;

	String xLabel;
	String yLabel;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getxLabel() {
		return xLabel;
	}

	public void setxLabel(String xLabel) {
		this.xLabel = xLabel;
	}

	public String getyLabel() {
		return yLabel;
	}

	public void setyLabel(String yLabel) {
		this.yLabel = yLabel;
	}
	
	public <T extends AbstractChartDataset> T title(String title) {
		this.title = title;
		return (T) this;
	}

	public <T extends AbstractChartDataset> T xLabel(String xLabel) {
		this.xLabel = xLabel;
		return (T) this;
	}

	public <T extends AbstractChartDataset> T yLabel(String yLabel) {
		this.yLabel = yLabel;
		return (T) this;
	}


}
