
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;


public class Board {

	private char[] titles;
	private int n;
	private int ham, man;

	/**
	 * construct a board from an n-by-n array of blocks (where blocks[i][j] =
	 * block in row i, column j)
	 * 
	 * @param blocks
	 */
	public Board(int[][] blocks) {
		this.n = blocks[0].length;
		this.titles = new char[n * n + 1];

		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++) {
				int num = i * n + j + 1;
				this.titles[num] = (char) blocks[i][j];
			}
		this.ham = callHam(titles);
		this.man = callMan(titles);
	}

	private Board(char[] titles, int n, int ham, int man) {
		this.titles = titles;
		this.n = n;
		this.ham = ham;
		this.man = man;
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
		return ham;
	}

	/**
	 * sum of Manhattan distances between blocks and goal
	 * 
	 * @return
	 */
	public int manhattan() {
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
					if (num != (int) titles[num])
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

		char[] twinBlocks = titles.clone();
		int i = 0, j = 0;

		while (true) {

			int num = i * n + j + 1;
			if (j < n - 1) {
				if (twinBlocks[num] != 0 && twinBlocks[num + 1] != 0) {
					exch(twinBlocks, num, num + 1);
					break;
				}
			} else if (j == n - 1) {
				i++;
				j = 0;
				continue;
			}
			j++;
		}

		return new Board(twinBlocks, n, callHam(twinBlocks),
				callMan(twinBlocks));
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
		char[] yblocks = yb.titles;
		int yn = yb.dimension();

		if (yn != n)
			return false;

		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++) {
				int num = i * n + j + 1;
				if (titles[num] != yblocks[num])
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
				int bnum = i * n + j + 1;
				if ((int) titles[bnum] == 0) {
					bi = i;
					bj = j;
				}
			}
		
		int num = bi * n + bj + 1;
		// up
		if (bi > 0) {
			char[] newblocks = titles.clone();
			exch(newblocks, num, num - n);
			sb.push(new Board(newblocks, n, callHam(newblocks),
					man + callSinMan(newblocks[num], bi, bj) 
					- callSinMan(titles[num - n], bi - 1, bj)));
		}

		// down
		if (bi < n - 1) {
			char[] newblocks = titles.clone();
			exch(newblocks, num, num + n);
			sb.push(new Board(newblocks, n, callHam(newblocks),
					man + callSinMan(newblocks[num], bi, bj) 
					- callSinMan(titles[num + n], bi + 1, bj)));
		}

		// left
		if (bj > 0) {
			char[] newblocks = titles.clone();
			exch(newblocks, num, num - 1);
			sb.push(new Board(newblocks, n, callHam(newblocks),
					man + callSinMan(newblocks[num], bi, bj) 
					- callSinMan(titles[num - 1], bi, bj - 1)));
		}

		// right
		if (bj < n - 1) {
			char[] newblocks = titles.clone();
			exch(newblocks, num, num + 1);
			sb.push(new Board(newblocks, n, callHam(newblocks),
					man + callSinMan(newblocks[num], bi, bj) 
					- callSinMan(titles[num + 1], bi, bj + 1)));
		}

		return sb;
	}


	private void exch(char[] exblocks, int i, int j) {
		char temp = exblocks[i];
		exblocks[i] = exblocks[j];
		exblocks[j] = temp;
	}

	private int callHam(char[] titles) {

		int ham = 0;

		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++) {
				int num = i * n + j + 1;
				if (num == n * n)
					break;
				if ((int) titles[num] != num)
					ham++;
			}

		return ham;
	}
	
	private int callMan(char[] titles) {

		int man = 0;

		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++) {

				int num = i * n + j + 1;
				int gnum = titles[num];

				if (gnum != 0) {
					man += callSinMan(gnum, i, j);
				}
			}
		return man;
	}
	
	private int callSinMan(int gnum, int i, int j){
		
		int gi = (gnum - 1) / n;
		int gj = (gnum - 1) % n;
		int disi = (gi - i > 0) ? (gi - i) : -(gi - i);
		int disj = (gj - j > 0) ? (gj - j) : -(gj - j);
		
		return (disi + disj);
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
				sb.append(String.format(" %2d", (int)titles[i * n + j + 1]));
			sb.append("\n");
		}

		return sb.toString();
	}

}
