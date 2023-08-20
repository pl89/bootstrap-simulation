# bootstrap-simulation

Given a set of data, this tool will generate a mean using a bootstrap algorithm as well as graph the normal distribution of the results.

Example usage:
```
java -cp * com.github.pl89.Main --dataset1 "75.3,72.5,65.6,42.0,92.5,84.0,62.5,75.7,88.7,81.2,77.3,111.1" --graphTitle "Salt sampling" --graphXAxisLabel "salt ppm" --graphYAxisLabel "normal distribution"
```

This will output something that looks as follows:
```
Starting simulation for dataset 1.
Simulation for dataset 1 is complete (10000 of 10000).
Bootstrap mean for simulation 1: 77.39 (95% Confidence Interval: 68.04 - 86.46)
Standard deviation for simulation 1 means: 4.69
Simulation(s) ran in 15 ms.
Generating graph ...
Graph generated in 385 ms.
Successfully wrote file '1692546165761-mean-dataset1.txt'.
```
Along with a graph that looks like:
![graph1](https://github.com/[pl89]/[bootstrap-simulation]/img/graph-example1.png?raw=true)