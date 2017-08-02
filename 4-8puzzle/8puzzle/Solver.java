
import java.util.Comparator;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {

	private Stack<Board> rs;
	private int flag = 0;
	private Comparator<Node> comp = new ManPrio();

	private class ManPrio implements Comparator<Node> {

		@Override
		public int compare(Node o1, Node o2) {

			int m1 = o1.board.manhattan();
			int m2 = o2.board.manhattan();
			
			int f1 = o1.moves + m1;
			int f2 = o2.moves + m2;
			if(f1 == f2)
				return m1 - m2;
			return f1 - f2;
		}
	}

	private class Node {

		Board board;
		Node pre;
		int moves;

		public Node(Board board, Node pre, int moves) {
			super();
			this.board = board;
			this.pre = pre;
			this.moves = moves;
		}
	}

	/**
	 * find a solution to the initial board (using the A* algorithm)
	 * 
	 * @param initial
	 */
public Solver(Board initial) {
		
		if(initial == null)
			throw new IllegalArgumentException();
		
		MinPQ<Node> minpq = new MinPQ<Node>(comp);
		minpq.insert(new Node(initial, null, flag));

		MinPQ<Node> twinpq = new MinPQ<Node>(comp);
		twinpq.insert(new Node(initial.twin(), null, flag));

		while (!minpq.min().board.isGoal() && !twinpq.min().board.isGoal()) {
			
			Node sn = minpq.delMin();
			Node tsn = twinpq.delMin();

			for (Board b : sn.board.neighbors()) {
				if (sn.pre != null && b.equals(sn.pre.board))
					continue;
				minpq.insert(new Node(b, sn, sn.moves + 1));
			}

			for (Board b : tsn.board.neighbors()) {
				if (tsn.pre != null && tsn.pre.board.equals(b))
					continue;
				twinpq.insert(new Node(b, tsn, tsn.moves + 1));
			}
		}
		
		if(minpq.min().board.isGoal()){
		
			rs = new Stack<Board>();
			Node goal = minpq.min();
			flag = goal.moves;
			
			while (goal.board != initial) {
				rs.push(goal.board);
				goal = goal.pre;
			}
			rs.push(initial);
		}else{
			flag = -1;
		}
		
	}

	/**
	 * is the initial board solvable?
	 * 
	 * @return
	 */
	public boolean isSolvable() {
		return flag != -1;
	}

	/**
	 * min number of moves to solve initial board; -1 if unsolvable
	 * 
	 * @return
	 */
	public int moves() {
		return flag;
	}

	/**
	 * sequence of boards in a shortest solution; null if unsolvable
	 * 
	 * @return
	 */
	public Iterable<Board> solution() {
		if (!isSolvable())
			return null;
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
