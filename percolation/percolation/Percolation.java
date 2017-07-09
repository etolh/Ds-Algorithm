import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private byte[] fopen;
    private int opencount;
    private WeightedQuickUnionUF uf;
    private int n;

    public Percolation(int n) {
        if (n <= 0)
            throw new IllegalArgumentException();
        this.n = n; // 对n初始化
        int count = n * n;
        this.opencount = 0;
        fopen = new byte[count];
        uf = new WeightedQuickUnionUF(count);
    }

    private void validate(int r, int c) {
        if (r < 1 || r > n || c < 1 || c > n)
            throw new IllegalArgumentException();
    }
    
    private void union(int p, int q){
        uf.union(p, q);
    }
    
    public void open(int row, int col) {
        validate(row, col);
        if (isOpen(row, col))
            return;

        int nowc = toNum(row, col);
        opencount++;

        if (row == 1)
            fopen[nowc] = 2;
        else
            fopen[nowc] = 1;

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
                int nowroot = uf.find(nowc); // 自身格子集合root，不断变化，每连接一次变化一次。
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
                int nowroot = uf.find(nowc); // 自身格子集合root，不断变化，每连接一次变化一次。
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
                int nowroot = uf.find(nowc); // 自身格子集合root，不断变化，每连接一次变化一次。
                union(nowc,rightc);
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
        int root = uf.find(toNum(row, col));
        return fopen[root] == 2;
    }
    
    public int numberOfOpenSites() {
        return opencount;
    }

    // does the system percolate?
    public boolean percolates() {
        if (n == 1)
            return isOpen(1, 1);
        for (int i = 1; i <= n; i++)
            if (isFull(n, i))
                return true;
        return false;
    }

    // (r,c)-->int
    private int toNum(int r, int c) {
        return (r - 1) * n + c - 1;
    }
}
