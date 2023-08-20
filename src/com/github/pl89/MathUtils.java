package com.github.pl89;

import java.util.Arrays;
import java.util.Random;

public class MathUtils {

	private static Random random = new Random();

	public static double calculateMean(double[] arr) {
		double sum = 0.0;
		for (double value : arr) {
			sum += value;
		}
		return sum / arr.length;
	}

	public static double calculateStandardDeviation(double[] data, double bootstrapMean) {
		double variance = 0;
		for (int i = 0; i < data.length; i++) {
			variance += Math.pow(data[i] - bootstrapMean, 2);
		}
		variance /= data.length;
		return Math.sqrt(variance);
	}

	public static double calculateNormalDistribution(double x, double mean, double stdDev) {
		double exponent = -Math.pow(x - mean, 2) / (2 * Math.pow(stdDev, 2));
		double coefficient = 1 / (stdDev * Math.sqrt(2 * Math.PI));
		return coefficient * Math.exp(exponent);
	}

	public static double[] calculatePercentiles(double[] values, double lowerPercentile, double upperPercentile) {
		double[] sortedValues = values.clone();
		Arrays.sort(sortedValues);
		int lowerIndex = (int) Math.floor(lowerPercentile * values.length / 100);
		int upperIndex = (int) Math.floor(upperPercentile * values.length / 100);
		double lowerValue = sortedValues[lowerIndex];
		double upperValue = sortedValues[upperIndex];
		return new double[] { lowerValue, upperValue };
	}

	public static double generateValueWithRandomError(double value, double error) {
		double errorMin = Math.abs(error) * -1;
		double errorMax = Math.abs(error);
		double randomError = errorMin + (errorMax - errorMin) * random.nextDouble();
		return (value + randomError);
	}
}
