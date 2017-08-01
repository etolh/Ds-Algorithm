
import java.util.Comparator;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

public class Solver {

	private int moves;
	private Queue<Board> rs;
	private int flag = 0;

	private class comp implements Comparator<Board> {

		public int compare(Board o1, Board o2) {
			return o1.manhattan() - o2.manhattan();
		}

	}

	/**
	 * find a solution to the initial board (using the A* algorithm)
	 * 
	 * @param initial
	 */
	public Solver(Board initial) {

		this.moves = 0;
		rs = new Queue<Board>();
		Board twin = initial.twin();

		MinPQ<Board> minpq = new MinPQ<Board>(new comp());
		Board pre = null;
		minpq.insert(initial);

		MinPQ<Board> twinpq = new MinPQ<Board>(new comp());
		Board tpre = null;
		twinpq.insert(twin);

		while (!minpq.isEmpty()) {
			
			Board searchnode = minpq.delMin();
			rs.enqueue(searchnode);

			Board twinsr = twinpq.delMin();

			
			if (searchnode.isGoal())
				break;

			
			if (twinsr.isGoal()) {
				flag = 1;
				break;
			}

			
			for (Board n : searchnode.neighbors()) {
				if (n != pre) {
					minpq.insert(n);
				}
			}

			for (Board n : twinsr.neighbors()) {
				if (n != tpre) {
					twinpq.insert(n);
				}
			}

			pre = searchnode;
			tpre = twinsr;
			moves++;
		}
	}

	/**
	 * is the initial board solvable
	 * 
	 * @return
	 */
	public boolean isSolvable() {
		return flag == 0;
	}

	/**
	 * min number of moves to solve initial board; -1 if unsolvable
	 * 
	 * @return
	 */
	public int moves() {
		return moves;
	}

	/**
	 * sequence of boards in a shortest solution; null if unsolvable
	 * 
	 * @return
	 */
	public Iterable<Board> solution() {
		return rs;
	}

	/**
	 * solve a slider puzzle (given below)
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// create initial board from file
		In in = new In(args[0]);
		int n = in.readInt();
		int[][] blocks = new int[n][n];
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++)
				blocks[i][j] = in.readInt();
		Board initial = new Board(blocks);

		// solve the puzzle
		Solver solver = new Solver(initial);

		// print solution to standard output
		if (!solver.isSolvable())
			StdOut.println("No solution possible");
		else {
			StdOut.println("Minimum number of moves = " + solver.moves());
			for (Board board : solver.solution())
				StdOut.println(board);
		}
	}
}
