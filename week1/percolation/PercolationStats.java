/* *****************************************************************************
 *  Name: Namanh Tran
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private double[] mean;
    private int meanCount;
    private int samplesize;

    public PercolationStats(int n, int trials) {
        if (trials < 1) {
            throw new java.lang.IllegalArgumentException(
                    "trials cannot be less than or equal to 0");
        }

        mean = new double[trials];
        samplesize = trials;
        for (int i = 0; i < trials; i++) {
            Percolation perc = new Percolation(n);
            while (!perc.percolates()) {
                int rand1 = StdRandom.uniform(1, n + 1);
                int rand2 = StdRandom.uniform(1, n + 1);
                perc.open(rand1, rand2);
            }
            mean[meanCount++] = ((double) perc.numberOfOpenSites() / (n * n));
        }
    }

    public double mean() {                          // sample mean of percolation threshold
        return StdStats.mean(mean);
    }

    public double stddev() {                        // sample standard deviation of percolation threshold

        return StdStats.stddev(mean);
    }

    public double confidenceLo() {                  // low  endpoint of 95% confidence interval

        return mean() - ((1.96 * stddev()) / Math.sqrt(samplesize));
    }

    public double confidenceHi() {                  // high endpoint of 95% confidence interval

        return mean() + ((1.96 * stddev()) / Math.sqrt(samplesize));
    }

    public static void main(String[] args) {        // test client
        int size = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats stats = new PercolationStats(size, trials);
        System.out.printf("mean = %f\n", stats.mean());
        System.out.printf("stddev = %f\n", stats.stddev());
        System.out.printf("95%c confidence interval = [%f, %f]\n", '%', stats.confidenceLo(),
                          stats.confidenceHi());
    }
}
