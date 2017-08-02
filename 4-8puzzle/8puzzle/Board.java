
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Board {

	private int[][] blocks;
	private int n;

	/**
	 * construct a board from an n-by-n array of blocks (where blocks[i][j] =
	 * block in row i, column j)
	 * 
	 * @param blocks
	 */
	public Board(int[][] blocks) {
		this.n = blocks[0].length;
		this.blocks = new int[n][n];

		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++)
				this.blocks[i][j] = blocks[i][j];
	}

	/**
	 * board dimension n
	 * 
	 * @return
	 */
	public int dimension() {
		return n;
	}

	/**
	 * number of blocks out of place num
	 * 
	 * @return
	 */
	public int hamming() {
		int ham = 0;

		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++) {
				int num = i * n + j + 1;
				if (num == n * n)
					break;
				if (blocks[i][j] != num)
					ham++;
			}

		return ham;
	}

	/**
	 * sum of Manhattan distances between blocks and goal
	 * 
	 * @return
	 */
	public int manhattan() {
		int man = 0;

		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++) {

				int gnum = blocks[i][j];

				if (gnum != 0) {
					int gi = (gnum - 1) / n;
					int gj = (gnum - 1) % n;
					int disi = (gi - i > 0) ? (gi - i) : -(gi - i);
					int disj = (gj - j > 0) ? (gj - j) : -(gj - j);
					man += (disi + disj);
				}
			}
		return man;
	}

	/**
	 * is this board the goal board?
	 * 
	 * @return
	 */
	public boolean isGoal() {

		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++) {
				int num = i * n + j + 1;
				if (num != n * n) {
					if (num != blocks[i][j])
						return false;
				}
			}

		return true;
	}

	/**
	 * a board that is obtained by exchanging any pair of blocks
	 * 
	 * @return
	 */
	public Board twin() {

		int[][] twinBlocks = newClone();

		int i = 0, j = 0;

		while (true) {

			if (j < n - 1) {

				if (twinBlocks[i][j] != 0 && twinBlocks[i][j + 1] != 0) {
					exch(twinBlocks, i, j, i, j + 1);
					break;
				}

			} else if (j == n - 1) {
				i++;
				j = 0;
				continue;
			}

			j++;
		}

		return new Board(twinBlocks);
	}

	/**
	 * does this board equal y?
	 * 
	 */
	public boolean equals(Object y) {

		if (y == this)
			return true;
		if (y == null)
			return false;
		if (y.getClass() != this.getClass())
			return false;

		Board yb = (Board) y;
		int[][] yblocks = yb.blocks;
		int yn = yb.dimension();

		if (yn != n)
			return false;

		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++) {
				if (blocks[i][j] != yblocks[i][j])
					return false;
			}

		return true;
	}

	/**
	 * all neighboring boards
	 * 
	 * @return
	 */
	public Iterable<Board> neighbors() {
		Stack<Board> sb = new Stack<Board>();

		int bi = 0, bj = 0;
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++) {
				if (blocks[i][j] == 0) {
					bi = i;
					bj = j;
				}
			}

		// up
		if (bi > 0) {
			int[][] newblocks = newClone();
			exch(newblocks, bi, bj, bi - 1, bj);
			sb.push(new Board(newblocks));
		}

		// down
		if (bi < n - 1) {
			int[][] newblocks = newClone();
			exch(newblocks, bi, bj, bi + 1, bj);
			sb.push(new Board(newblocks));
		}

		// left
		if (bj > 0) {
			int[][] newblocks = newClone();
			exch(newblocks, bi, bj, bi, bj - 1);
			sb.push(new Board(newblocks));
		}

		// right
		if (bj < n - 1) {
			int[][] newblocks = newClone();
			exch(newblocks, bi, bj, bi, bj + 1);
			sb.push(new Board(newblocks));
		}

		return sb;
	}

	private void exch(int[][] exblocks, int si, int sj, int ti, int tj) {
		int temp = exblocks[si][sj];
		exblocks[si][sj] = exblocks[ti][tj];
		exblocks[ti][tj] = temp;
	}

	private int[][] newClone() {
		int[][] newb = new int[n][n];
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++)
				newb[i][j] = blocks[i][j];

		return newb;
	}

	/**
	 * string representation of this board (in the output format specified
	 * below)
	 * 
	 * @return
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(n + "\n");

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++)
				sb.append(String.format(" %2d", blocks[i][j]));
			sb.append("\n");
		}

		return sb.toString();
	}
}
