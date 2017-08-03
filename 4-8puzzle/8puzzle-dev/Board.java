

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

/**
 * 包含n*n个格子的网板
 * 
 * @author 胡亮
 *
 */

public class Board {

	private char[] titles; // blocks存储每个块目标位置
	private int n; // 维度
	private int ham, man;
	private int zero; // 空白点所在位置（一维数组）

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
				if (titles[num] == 0)
					zero = num;
			}
		this.ham = callHam(titles);
		this.man = callMan(titles);
	}

	private Board(char[] titles, int zero, int ham, int man) {
		this.n = (int) Math.sqrt(titles.length);
		this.titles = titles;
		this.zero = zero;
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
	 * number of blocks out of place num: 当前所在的位置 blocks[i][j]:目标位置
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

		return new Board(twinBlocks, zero, callHam(twinBlocks),
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
	 * 返回与空白位置zero交换位置为pos处的邻接结点
	 * 
	 * @param i
	 * @param j
	 * @return
	 */
	private Board neignbor(int pos) {

		char[] newblocks = titles.clone();
		exch(newblocks, zero, pos);

		// 交换后pos位置为空白
		return new Board(newblocks, pos, callHam(newblocks), man
				+ callSinMan(newblocks[zero], zero)
				- callSinMan(titles[pos], pos));
	}

	/**
	 * all neighboring boards
	 * 
	 * @return
	 */
	public Iterable<Board> neighbors() {
		Stack<Board> sb = new Stack<Board>();

		int bi = (zero - 1) / n;
		int bj = (zero - 1) % n;

		int num = zero;
		// up
		if (bi > 0) {
			// 互换
			sb.push(neignbor(num - n));
		}

		// down
		if (bi < n - 1) {
			sb.push(neignbor(num + n));
		}

		// left
		if (bj > 0) {
			sb.push(neignbor(num - 1));
		}

		// right
		if (bj < n - 1) {
			sb.push(neignbor(num + 1));
		}

		return sb;
	}

	// 交换数组格子
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
	
	/**
	 * 求一维数组当前位置pos(i, j)的曼哈顿距离
	 * 
	 * @param gnum
	 * @param i
	 * @param j
	 * @return
	 */
	private int callSinMan(int gnum, int pos) {
		
		int gi = (gnum - 1) / n;
		int gj = (gnum - 1) % n;
		int i = (pos - 1) / n;
		int j = (pos - 1) % n;
		int disi = (gi - i > 0) ? (gi - i) : -(gi - i);
		int disj = (gj - j > 0) ? (gj - j) : -(gj - j);
		
		return (disi + disj);
	}

	/**
	 * 求整个数组的曼哈顿距离
	 * 
	 * @param titles
	 * @return
	 */
	private int callMan(char[] titles) {

		int man = 0;

		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++) {

				int num = i * n + j + 1;
				int gnum = titles[num];

				if (gnum != 0) {
					man += callSinMan(gnum, num);
				}
			}
		return man;
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
				sb.append(String.format(" %2d", (int) titles[i * n + j + 1]));
			sb.append("\n");
		}

		return sb.toString();
	}

	public static void main(String[] args) {
		// unit tests (not graded)

		In in = new In(args[0]);
		int n = in.readInt();
		int[][] blocks = new int[n][n];
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++)
				blocks[i][j] = in.readInt();
		Board board = new Board(blocks);

		Board twinb = board.twin();

		StdOut.println(board);
		StdOut.println("ham:" + board.hamming());
		StdOut.println("man:" + board.manhattan());
		StdOut.println(board.isGoal());

		StdOut.println("twin:");
		StdOut.println(twinb);

		StdOut.println("neigh:");
		for (Board b : board.neighbors()) {
			StdOut.println(b);
			StdOut.println(b.isGoal());
		}
	}

}
