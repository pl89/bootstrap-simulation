package com.github.pl89;

import java.awt.BasicStroke;
import java.awt.Dimension;

import org.apache.commons.cli.ParseException;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class Main {

	public static void main(String[] args) throws ParseException {

		Settings settings = new Settings(args);

		long startTime = System.currentTimeMillis();

		Simulation sim_1 = new Simulation("1", settings.getDataset1(), settings.getErrorDataset1(),
				settings.getNumIterations());
		sim_1.run();

		Simulation sim_2 = null;
		if (settings.getDataset2() != null) {
			sim_2 = new Simulation("2", settings.getDataset2(), settings.getErrorDataset2(),
					settings.getNumIterations());
			sim_2.run();
		}

		double[] bootstrapMeans_1 = sim_1.getSimulationResults();
		double bootstrapMeansMean_1 = MathUtils.calculateMean(bootstrapMeans_1);
		double standardDeviation_1 = MathUtils.calculateStandardDeviation(bootstrapMeans_1, bootstrapMeansMean_1);
		double[] confidenceIntervalMeans = MathUtils.calculatePercentiles(bootstrapMeans_1, 2.5, 97.5);
		System.out.printf("Bootstrap mean for simulation 1: %.2f (95%% Confidence Interval: %.2f - %.2f)%n",
				bootstrapMeansMean_1, confidenceIntervalMeans[0], confidenceIntervalMeans[1]);
		System.out.printf("Standard deviation for simulation 1 means: %.2f%n", standardDeviation_1);

		double[] bootstrapMeans_2 = null;
		double bootstrapMeansMean_2 = 0;
		double standardDeviation_2 = 0;
		if (sim_2 != null) {
			bootstrapMeans_2 = sim_2.getSimulationResults();
			bootstrapMeansMean_2 = MathUtils.calculateMean(bootstrapMeans_2);
			standardDeviation_2 = MathUtils.calculateStandardDeviation(bootstrapMeans_2, bootstrapMeansMean_2);
			double[] confidenceIntervalMeans_2 = MathUtils.calculatePercentiles(bootstrapMeans_2, 2.5, 97.5);
			System.out.printf("Bootstrap mean for simulation 2: %.2f (95%% Confidence Interval: %.2f - %.2f)%n",
					bootstrapMeansMean_2, confidenceIntervalMeans_2[0], confidenceIntervalMeans_2[1]);
			System.out.printf("Standard deviation for simulation 2 means: %.2f%n", standardDeviation_2);
		}
		long endTime = System.currentTimeMillis();
		System.out.println("Simulation(s) ran in " + (endTime - startTime) + " ms.");

		System.out.println("Generating graph ...");
		long graphGenerationStartTime = System.currentTimeMillis();
		final XYSeriesCollection dataset = new XYSeriesCollection();
		final XYSeries xySeriesSim_1 = new XYSeries("Simulation #1");
		for (double mean : bootstrapMeans_1) {
			xySeriesSim_1.add(mean,
					MathUtils.calculateNormalDistribution(mean, bootstrapMeansMean_1, standardDeviation_1));
		}
		dataset.addSeries(xySeriesSim_1);

		if (sim_2 != null) {
			final XYSeries xySeriesSim_2 = new XYSeries("Simulation #2");
			for (double mean : bootstrapMeans_2) {
				xySeriesSim_2.add(mean,
						MathUtils.calculateNormalDistribution(mean, bootstrapMeansMean_2, standardDeviation_2));
			}
			dataset.addSeries(xySeriesSim_2);
		}

		JFreeChart xylineChart = ChartFactory.createXYLineChart(settings.getGraphTitle(), settings.getGraphXAxisLabel(),
				settings.getGraphYAxisLabel(), dataset, PlotOrientation.VERTICAL, true, true, false);
		XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
		renderer.setSeriesPaint(0, settings.getGraphColorDataset1());
		renderer.setSeriesPaint(1, settings.getGraphColorDataset2());
		renderer.setSeriesStroke(0, new BasicStroke(2.0f));
		renderer.setSeriesStroke(1, new BasicStroke(2.0f));

		XYLineChart chart = new XYLineChart(settings.getGraphTitle(), settings.getGraphTitle(), xylineChart,
				new Dimension(settings.getGraphWidth(), settings.getGraphHeight()), renderer);
		chart.pack();
		chart.setVisible(true);
		long graphGenerationEndTime = System.currentTimeMillis();
		System.out.println("Graph generated in " + (graphGenerationEndTime - graphGenerationStartTime) + " ms.");

		long fileNameTimestamp = System.currentTimeMillis();
		Utils.createDataFile(fileNameTimestamp + "-mean-dataset1.txt", bootstrapMeans_1);
		if (sim_2 != null) {
			Utils.createDataFile(fileNameTimestamp + "-mean-dataset2.txt", bootstrapMeans_2);
		}
		Utils.createGraphFile(fileNameTimestamp + "-graph.png",
				xylineChart.createBufferedImage(settings.getGraphWidth(), settings.getGraphHeight()));
	}

}