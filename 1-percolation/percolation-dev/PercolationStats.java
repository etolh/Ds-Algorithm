package com.exp.a_UFexercise;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdOut;

public class PercolationStats {

	// record every experiments' number
	private double[] x;
	private int trials;

	// perform trials independent experiments on an n-by-n grid
	public PercolationStats(int n, int trials) {

		if (n <= 0 || trials <= 0)
			throw new IllegalArgumentException();
		// 初始化
		x = new double[trials];
		this.trials = trials;

		Percolation p = new Percolation(n);

		// 进行trials实验，收集数据
		for (int i = 0; i < trials; i++) {

			while (true) {

				// 读取随机数 1~n
				int rrow = StdRandom.uniform(1, n + 1);
				int rcol = StdRandom.uniform(1, n + 1);
				// StdOut.println(rrow+" : "+rcol);

				if (!p.isOpen(rrow, rcol))
					p.open(rrow, rcol);

				if (p.percolates())
					break;

			}
			int opensites = p.numberOfOpenSites();
			x[i] = (double) opensites / (n * n);
		}
	}

	// sample mean of percolation threshold
	public double mean() {
		return StdStats.mean(x);
	}

	// sample standard deviation of percolation threshold
	public double stddev() {
		return StdStats.stddev(x);
	}

	// low endpoint of 95% confidence interval
	public double confidenceLo() {
		double avgx = mean();
		double s = stddev();
		return (avgx - 1.96 * s / Math.sqrt(trials));
	}

	// high endpoint of 95% confidence interval
	public double confidenceHi() {
		double avgx = mean();
		double s = stddev();
		return avgx + 1.96 * s / Math.sqrt(trials);
	}

	// test client (described below)
	public static void main(String[] args) {

		int n = Integer.parseInt(args[0]);
		int trials = Integer.parseInt(args[1]);

		PercolationStats ps = new PercolationStats(n, trials);
		StdOut.printf("mean                    = %f\n", ps.mean());
		StdOut.printf("stddev                  = %f\n", ps.stddev());
		StdOut.printf("95%% confidence interval = [%f, %f]", ps.confidenceLo(),
				ps.confidenceHi());
	}
}
