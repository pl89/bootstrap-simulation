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
![graph1](https://github.com/pl89/bootstrap-simulation/blob/main/img/graph-example1.png?raw=true)

This application can also be used to compare two data sets while taking errors in consideration.

Example usage:
```
java -cp * com.github.pl89.Main --dataset1 "75.3,72.5,65.6,42.0,92.5,84.0,62.5,75.7,88.7,81.2,77.3,111.1" --error1 "1.1,1.4,1.9,0.9,1.5,1.9,0.8,1.2,1.3,2.4,2.9,1.9" --dataset2 "45.2,32.5,25.4,52.1,62.3,54.0,41.4,42.4,65.5,77.3,44.6,111.1" --error2 "0.9,2.4,1.2,1.1,1.4,1.2,1.2,1.5,0.8,1.7,1.5,2.3" --graphTitle "Salt sampling" --graphXAxisLabel "salt ppm" --graphYAxisLabel "normal distribution"
```

```
Starting simulation for dataset 1.
Simulation for dataset 1 is complete (10000 of 10000).
Starting simulation for dataset 2.
Simulation for dataset 2 is complete (10000 of 10000).
Bootstrap mean for simulation 1: 77.31 (95% Confidence Interval: 67.97 - 86.65)
Standard deviation for simulation 1 means: 4.70
Bootstrap mean for simulation 2: 54.36 (95% Confidence Interval: 43.02 - 67.84)
Standard deviation for simulation 2 means: 6.29
Simulation(s) ran in 21 ms.
Generating graph ...
Graph generated in 386 ms.
Successfully wrote file '1692546782250-mean-dataset1.txt'.
Successfully wrote file '1692546782250-mean-dataset2.txt'.
```

![graph2](https://github.com/pl89/bootstrap-simulation/blob/main/img/graph-example2.png?raw=true)
