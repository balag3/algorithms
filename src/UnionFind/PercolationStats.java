package UnionFind;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private int n;
    private int trials;
    private Percolation p;
    private double[] results;

    public PercolationStats(int n, int trials) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        if (trials <= 0) {
            throw new IllegalArgumentException();
        }
        this.n = n;
        this.trials = trials;
        this.results = new double[trials];
        for (int i = 0; i < trials; i++) {
            p = new Percolation(this.n);
            double counter = 0;
            while (!p.percolates()) {
                int row = StdRandom.uniform(n) + 1;
                int col = StdRandom.uniform(n) + 1;
                if (!p.isOpen(row , col)) {
                    p.open(row, col);
                    counter++;
                }
            }
            results[i] = counter / (n * n);
        }
    }

    public double mean() {
        return StdStats.mean(results);
    }

    public double stddev() {
        return StdStats.stddev(results);
    }

    public double confidenceLo() {
        return mean() - 1.95 * stddev() / Math.sqrt(trials);
    }

    public double confidenceHi() {
        return mean() + 1.95 * stddev() / Math.sqrt(trials);
    }

    public static void main(String[] args) {
        if (args.length != 2) {
            return;
        }

        try {
            int n = Integer.parseInt(args[0]);
            int trials = Integer.parseInt(args[1]);
            PercolationStats ps = new PercolationStats(n, trials);
            System.out.println("mean = " + ps.mean());
            System.out.println("stddev = " + ps.stddev());
            System.out.println("95% confidence interval = " + ps.confidenceHi() + ", " +ps.confidenceLo());
        } catch (NumberFormatException e) {
            System.out.println("Arguments must be integers!");
        }
    }

}
