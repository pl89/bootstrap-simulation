package com.github.pl89;

import java.util.Random;

public class Simulation {

	final private static Random random = new Random();
	final private int numIterations;
	private int numIterationsCompleted = 0;
	final private String simulationName;
	final private double[] data;
	final private double[] error;
	final private double[] bootstrapMeans;

	public Simulation(String simulationName, double[] data, double[] error, int numIterations) {
		this.simulationName = simulationName;
		this.numIterations = numIterations;
		this.data = data;
		this.error = error;
		this.bootstrapMeans = new double[numIterations];
	}

	public void run() {

		System.out.printf("Starting simulation for dataset %s.%n", simulationName);

		// Attempt to figure out roughly how many iterations we can squeeze in a second
		long startTime = System.currentTimeMillis();
		int numIterForBenchmark = 100000;
		for (; numIterationsCompleted < numIterations
				&& numIterationsCompleted <= numIterForBenchmark; numIterationsCompleted++) {
			bootstrapMeans[numIterationsCompleted] = generateBootstrappedMean();
		}
		long endTime = System.currentTimeMillis();

		int numIterationsPerSecond = numIterForBenchmark / ((int) (endTime - startTime)) * 1000;
		// System.out.printf("Benchmark for dataset %s completed.
		// numIterationsPerSecond=%d.%n", simulationName, numIterationsPerSecond);

		for (; numIterationsCompleted < numIterations; numIterationsCompleted++) {
			bootstrapMeans[numIterationsCompleted] = generateBootstrappedMean();
			if (numIterationsCompleted % numIterationsPerSecond == 0) {
				System.out.printf("Simulation %s in progress (%d of %d iterations completed).%n", this.simulationName,
						numIterationsCompleted, numIterations);
			}
		}

		System.out.printf("Simulation for dataset %s is complete (%d of %d).%n", simulationName, numIterationsCompleted,
				numIterations);
	}

	private double generateBootstrappedMean() {
		double[] bootstrapSample = new double[data.length];
		for (int j = 0; j < data.length; j++) {
			int randomIndex = random.nextInt(data.length);
			bootstrapSample[j] = MathUtils.generateValueWithRandomError(data[randomIndex], error[randomIndex]);
		}
		return MathUtils.calculateMean(bootstrapSample);
	}

	public String getSimulationName() {
		return this.simulationName;
	}

	/*
	 * Returns simulation results.
	 * 
	 * Throw RuntimeException is called before simulation is complete.
	 */
	public double[] getSimulationResults() {
		if (numIterationsCompleted != numIterations) {
			throw new RuntimeException("Simulation not complete.");
		}
		return bootstrapMeans;
	}

}
