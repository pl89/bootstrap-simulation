package com.github.pl89;

import java.awt.Color;
import java.lang.reflect.Field;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class Settings {

	private int numIterations = 10000;
	private String graphTitle = "Graph Title";
	private String graphXAxisLabel = "X Axis";
	private String graphYAxisLabel = "Y Axis";
	private int graphWidth = 800;
	private int graphHeight = 600;
	private Color graphColorDataset1 = Color.RED;
	private Color graphColorDataset2 = Color.GREEN;

	private double[] dataset1;
	private double[] dataset2;
	private double[] errorDataset1;
	private double[] errorDataset2;

	public Settings(String[] args) throws ParseException {

		Options options = new Options();

		options.addOption(null, "dataset1", true,
				"First dataset, comma-separated. Mandatory parameter. Usage example: --dataset1 1.0,2.0,3.0,-1.0");
		options.addOption(null, "dataset2", true,
				"Second dataset, comma-separated. Optional parameter. Usage example: --dataset2 1.0,2.0,3.0,-1.0");
		options.addOption(null, "error1", true,
				"Error values for dataset1, comma-separated. Optional parameter.\nMust have the same number of values as --dataset1.\nUsage example: --error1 0.1,0.2,0.3,0.4");
		options.addOption(null, "error2", true,
				"Error values for dataset2, comma-separated. Optional parameter.\nMust have the same number of values as --dataset2.\nUsage example: --error2 0.1,0.2,0.3,0.4");
		String acceptedColorValues = "BLACK, BLUE, CYAN, DARK_GRAY, GRAY, GREEN, LIGHT_GRAY, MAGENTA, ORANGE, PINK, RED, WHITE, YELLOW";
		options.addOption(null, "graphColorDataset1", true,
				"Sets the color of the first dataset on the graph. Default: RED\nAccepted values: "
						+ acceptedColorValues);
		options.addOption(null, "graphColorDataset2", true,
				"Sets the color of the second dataset on the graph. Default: GREEN\nAccepted values: "
						+ acceptedColorValues);
		options.addOption(null, "graphHeight", true, "Sets the height of the graph. Default: " + graphHeight);
		options.addOption(null, "graphTitle", true, "Sets the title of the graph.");
		options.addOption(null, "graphWidth", true, "Sets the width of the graph. Default: " + graphWidth);
		options.addOption(null, "graphXAxisLabel", true, "Sets the X Axis Label of the graph.");
		options.addOption(null, "graphYAxisLabel", true, "Sets the Y Axis Label of the graph.");
		options.addOption(null, "numIterations", true,
				"Number of simulation iterations to run.\nHigher values will provide more precise results but will take longer to run.\nDefault: "
						+ numIterations);
		options.addOption("h", "help", false, "Displays the help.");

		CommandLineParser parser = new DefaultParser();
		CommandLine cmd = parser.parse(options, args);

		if (cmd.hasOption("h")) {
			HelpFormatter formatter = new HelpFormatter();
			formatter.setWidth(200);
			formatter.printHelp("Bootstrap simulation", options);
			System.exit(0);
		}

		if (!cmd.hasOption("dataset1")) {
			System.out.println("Must provide mandatory --dataset1 argument.");
			System.exit(1);
		} else {
			String[] datasetStringArr = cmd.getOptionValue("dataset1").split(",");
			dataset1 = new double[datasetStringArr.length];
			for (int i = 0; i < datasetStringArr.length; ++i) {
				dataset1[i] = Double.parseDouble(datasetStringArr[i]);
			}
		}

		if (!cmd.hasOption("dataset2")) {
			dataset2 = null;
		} else {
			String[] datasetStringArr = cmd.getOptionValue("dataset2").split(",");
			dataset2 = new double[datasetStringArr.length];
			for (int i = 0; i < datasetStringArr.length; ++i) {
				dataset2[i] = Double.parseDouble(datasetStringArr[i]);
			}
		}

		if (!cmd.hasOption("error1")) {
			errorDataset1 = new double[dataset1.length];
		} else {
			String[] errorStringArr = cmd.getOptionValue("error1").split(",");
			errorDataset1 = new double[errorStringArr.length];
			for (int i = 0; i < errorStringArr.length; ++i) {
				errorDataset1[i] = Double.parseDouble(errorStringArr[i]);
			}
		}

		if (dataset2 == null) {
			errorDataset2 = null;
		} else if (!cmd.hasOption("error2")) {
			errorDataset2 = new double[dataset2.length];
		} else {
			String[] errorStringArr = cmd.getOptionValue("error2").split(",");
			errorDataset2 = new double[errorStringArr.length];
			for (int i = 0; i < errorStringArr.length; ++i) {
				errorDataset2[i] = Double.parseDouble(errorStringArr[i]);
			}
		}

		if (dataset1.length != errorDataset1.length) {
			System.out.printf(
					"--dataset1 and --error1 must contain the same number of values.%n--dataset1 contains %d values while --error1 contains %d.%n",
					dataset1.length, errorDataset1.length);
			System.exit(1);
		}
		if (dataset2 != null && dataset2.length != errorDataset2.length) {
			System.out.printf(
					"--dataset2 and --error2 must contain the same number of values.%n--dataset2 contains %d values while --error2 contains %d.%n",
					dataset2.length, errorDataset2.length);
			System.exit(1);
		}

		if (cmd.hasOption("graphColorDataset1")) {
			try {
				Field field = Class.forName("java.awt.Color").getField(cmd.getOptionValue("graphColorDataset1"));
				graphColorDataset1 = (Color) field.get(null);
			} catch (Exception e) {
			}
		}
		if (cmd.hasOption("graphColorDataset2")) {
			try {
				Field field = Class.forName("java.awt.Color").getField(cmd.getOptionValue("graphColorDataset2"));
				graphColorDataset2 = (Color) field.get(null);
			} catch (Exception e) {
			}
		}
		if (cmd.hasOption("graphHeight")) {
			graphHeight = Integer.parseInt(cmd.getOptionValue("graphHeight"));
		}
		if (cmd.hasOption("graphTitle")) {
			graphTitle = cmd.getOptionValue("graphTitle");
		}
		if (cmd.hasOption("graphWidth")) {
			graphWidth = Integer.parseInt(cmd.getOptionValue("graphWidth"));
		}
		if (cmd.hasOption("graphXAxisLabel")) {
			graphXAxisLabel = cmd.getOptionValue("graphXAxisLabel");
		}
		if (cmd.hasOption("graphYAxisLabel")) {
			graphYAxisLabel = cmd.getOptionValue("graphYAxisLabel");
		}
		if (cmd.hasOption("numIterations")) {
			numIterations = Integer.parseInt(cmd.getOptionValue("numIterations"));
		}
	}

	public int getNumIterations() {
		return numIterations;
	}

	public String getGraphTitle() {
		return graphTitle;
	}

	public String getGraphXAxisLabel() {
		return graphXAxisLabel;
	}

	public String getGraphYAxisLabel() {
		return graphYAxisLabel;
	}

	public int getGraphWidth() {
		return graphWidth;
	}

	public int getGraphHeight() {
		return graphHeight;
	}

	public Color getGraphColorDataset1() {
		return graphColorDataset1;
	}

	public Color getGraphColorDataset2() {
		return graphColorDataset2;
	}

	public double[] getDataset1() {
		return dataset1;
	}

	public double[] getDataset2() {
		return dataset2;
	}

	public double[] getErrorDataset1() {
		return errorDataset1;
	}

	public double[] getErrorDataset2() {
		return errorDataset2;
	}
}
