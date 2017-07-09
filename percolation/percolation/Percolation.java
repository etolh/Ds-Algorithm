import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private byte[] fopen;
    private int opencount;
    private final WeightedQuickUnionUF uf;
    private final int n;

    public Percolation(int n) {
        if (n <= 0)
            throw new IllegalArgumentException();
        this.n = n;
        int count = n * n;
        this.opencount = 0;
        fopen = new byte[count];
        uf = new WeightedQuickUnionUF(count);
    }

    private void validate(int r, int c) {
        if (r < 1 || r > n || c < 1 || c > n)
            throw new IllegalArgumentException();
    }

    private void union(int p, int q) {
        uf.union(p, q);
    }

    public void open(int row, int col) {
        validate(row, col);
        if (isOpen(row, col))
            return;

        int nowc = toNum(row, col);
        opencount++;
        // fopen=0-block, 1-open, 2-与底部相连
        if (row == n)
            fopen[nowc] = 2; // 底层为2
        else
            fopen[nowc] = 1;

        // 建立虚拟top=0与所有打开的顶层格子相连。
        if (row == 1)
            uf.union(nowc, 0);

        if (row > 1) {
            int upc = nowc - n;
            if (isOpen(row - 1, col)) {
                int uproot = uf.find(upc); // 上方格子集合root
                int nowroot = uf.find(nowc); // 自身格子集合root，不断变化，每连接一次变化一次。
                union(upc, nowc);
                int root = uf.find(nowc); // 新集合root
                if (fopen[uproot] == 2 | fopen[nowroot] == 2)
                    fopen[root] = 2;
            }
        }
        if (row < n) {
            int downc = nowc + n;
            if (isOpen(row + 1, col)) {
                int downroot = uf.find(downc);
                int nowroot = uf.find(nowc);
                union(nowc, downc);
                int root = uf.find(nowc);
                if (fopen[downroot] == 2 | fopen[nowroot] == 2)
                    fopen[root] = 2;
            }
        }
        if (col > 1) {
            int leftc = nowc - 1;
            if (isOpen(row, col - 1)) {
                int lroot = uf.find(leftc);
                int nowroot = uf.find(nowc);
                union(leftc, nowc);
                int root = uf.find(nowc);
                if (fopen[lroot] == 2 | fopen[nowroot] == 2)
                    fopen[root] = 2;
            }
        }
        if (col < n) {
            int rightc = nowc + 1;
            if (isOpen(row, col + 1)) {
                int rroot = uf.find(rightc);
                int nowroot = uf.find(nowc);
                union(nowc, rightc);
                int root = uf.find(nowc);
                if (fopen[rroot] == 2 | fopen[nowroot] == 2)
                    fopen[root] = 2;
            }
        }
    }

    public boolean isOpen(int row, int col) {
        validate(row, col);
        return fopen[toNum(row, col)] != 0;
    }

    public boolean isFull(int row, int col) {
        validate(row, col);
        if (!isOpen(row, col))
            return false;
        int nowc = toNum(row, col);
        // nowc只有打开，且与顶层top相连即为full
        return fopen[nowc] > 0 && uf.connected(0, nowc);
    }

    public int numberOfOpenSites() {
        return opencount;
    }

    // does the system percolate?
    public boolean percolates() {
        if (n == 1)
            return isOpen(1, 1);
        // 判断top所属集合root的fopen[2]即表示连接到底层
        int r = uf.find(0);
        return fopen[r] == 2;
    }

    // (r,c)-->int
    private int toNum(int r, int c) {
        return (r - 1) * n + c - 1;
    }
}
