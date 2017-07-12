import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdOut;

public class PercolationStats {

    // record every experiments' number
    private final double[] x;
    private final int trials;
    private final double avgx;
    private final double s;

    // perform trials independent experiments on an n-by-n grid
    public PercolationStats(int n, int trials) {

        if (n <= 0 || trials <= 0)
            throw new IllegalArgumentException();
        // 初始化
        x = new double[trials];
        this.trials = trials;

        // 进行trials实验，收集数据
        for (int i = 0; i < trials; i++) {

            Percolation p = new Percolation(n);

            while (true) {

                // 读取随机数 1~n
                int rrow = StdRandom.uniform(1, n + 1);
                int rcol = StdRandom.uniform(1, n + 1);

                if (!p.isOpen(rrow, rcol))
                    p.open(rrow, rcol);

                if (p.percolates())
                    break;

            }
            int opensites = p.numberOfOpenSites();
            x[i] = (double) opensites / (n * n);
        }

        // 初始化，只调用一次
        this.avgx = StdStats.mean(x);
        this.s = StdStats.stddev(x);
    }

    // sample mean of percolation threshold
    public double mean() {
        return this.avgx;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return this.s;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return avgx - 1.96 * s / Math.sqrt(trials);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
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
