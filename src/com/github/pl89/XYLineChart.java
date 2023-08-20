package com.github.pl89;

import java.awt.Dimension;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.ui.ApplicationFrame;

public class XYLineChart extends ApplicationFrame {

	private static final long serialVersionUID = 767456632355247318L;

	public XYLineChart(String applicationTitle, String chartTitle, JFreeChart xylineChart, Dimension graphDimension,
			XYLineAndShapeRenderer renderer) {
		super(applicationTitle);
		ChartPanel chartPanel = new ChartPanel(xylineChart);
		chartPanel.setPreferredSize(graphDimension);
		final XYPlot plot = xylineChart.getXYPlot();
		plot.setRenderer(renderer);
		setContentPane(chartPanel);
	}
}
