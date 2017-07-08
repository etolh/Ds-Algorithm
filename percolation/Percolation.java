import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {    
    private boolean[][] fopen;
    private int opencount, up;
    private static WeightedQuickUnionUF uf;
    public Percolation(int n) {       
        if(n<=0)
            throw new IllegalArgumentException();        
        int count = n * n;
        opencount = 0;
        fopen = new boolean[n][n];
        uf = new WeightedQuickUnionUF(count);
        up = 0;
        for(int i = 0; i < n; i++){
            uf.union(up, i);
        }        
        for(int i = 0; i < n; i++)
            for(int j = 0; j < n; j++)
            fopen[i][j] = false;
    }

    public void open(int row, int col) {
        int n = fopen[0].length;
        if(row < 1  || row > n || col < 1 || col > n)
            throw new IllegalArgumentException();
        
        if(isOpen(row, col))
            return;
        
        opencount++;
        fopen[row-1][col-1] = true;
    }
    public boolean isOpen(int row, int col) {
        int n = fopen[0].length;
        if(row < 1  || row > n || col < 1 || col > n)
            throw new IllegalArgumentException();
        return fopen[row-1][col-1];
    }
    public boolean isFull(int row, int col) {
        int n = fopen[0].length;
        if(row < 1  || row > n || col < 1 || col > n)
            throw new IllegalArgumentException();       
        if(!isOpen(row, col))
            return false;
        int n = fopen[0].length;
        int nowc = (row-1) * n + col - 1;
        //上
        if(row > 1){
            int upc = nowc - n;
            if(isOpen(row-1, col)){
                uf.union(nowc, upc);
            }
        }
        if(row < n){
            int downc = nowc + n;
            if(isOpen(row+1, col)){
                uf.union(nowc, downc);
            }
        }        
        if(col > 1){
            int leftc = nowc - 1;
            if(isOpen(row, col-1)){
                uf.union(nowc, leftc);
            }
        }       
        if(col < n){
            int rightc = nowc + 1;
            if(isOpen(row, col+1)){
                uf.union(nowc, rightc);
            }
        }
        return uf.connected(nowc, up);
    }
    public int numberOfOpenSites() {
        return opencount;
    }
    public boolean percolates() {
        int n = fopen[0].length;       
        for(int i = n * n - 1; i >= n*n - n; i--)
            if(uf.connected(i, up))
                return true;     
        return false;
    }   
}
