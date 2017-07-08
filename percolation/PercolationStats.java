import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdOut;

public class PercolationStats {

    private final double[] x;

    public PercolationStats(int n, int trials) {

        if (n <= 0 || trials <= 0)
            throw new IllegalArgumentException();
        x = new double[trials];

        for (int i = 0; i < trials; i++) {

            Percolation p = new Percolation(n);

            while (true) {

                int rrow = StdRandom.uniform(1, n + 1);
                int rcol = StdRandom.uniform(1, n + 1);

                if (!p.isOpen(rrow, rcol))
                    p.open(rrow, rcol);

                if (p.isFull(rrow, rcol)) {
                    if (p.percolates()) {
                        break;
                    }
                }
            }
            int opensites = p.numberOfOpenSites();
            x[i] = (double) opensites / (n * n);
        }
    }

    public double mean() {
        return StdStats.mean(x);
    }

    public double stddev() {
        return StdStats.stddev(x);
    }

    public double confidenceLo() {
        double avgx = mean();
        double s = stddev();
        return (avgx - 1.96 * s / Math.sqrt(x.length));
    }

    public double confidenceHi() {
        double avgx = mean();
        double s = stddev();
        return avgx + 1.96 * s / Math.sqrt(x.length);
    }

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
