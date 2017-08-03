

import java.util.Comparator;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

/**
 * 
 * @author 胡亮
 *
 */

public class Solver {

	private Stack<Board> rs;
	private int flag = 0;
	private Comparator<Node> comp = new ManPrio();

	/**
	 * 比较器
	 * 
	 * @author 胡亮
	 *
	 */
	private class ManPrio implements Comparator<Node> {

		@Override
		public int compare(Node o1, Node o2) {

			int p1 = o1.priority;
			int p2 = o2.priority;
			
			if(p1 < p2)
				return -1;
			else if(p1 == p2)
				return 0;
			else
				return 1;
			
		}
	}

	private class Node {

		Board board;
		Node pre;
		int moves;
		int priority;
		
		public Node(Board board, Node pre, int moves) {
			this.board = board;
			this.pre = pre;
			this.moves = moves;
			this.priority = board.manhattan() + moves;
		}
	}

	/**
	 * find a solution to the initial board (using the A* algorithm)
	 * 
	 * @param initial
	 */

	public Solver(Board initial) {

		if (initial == null)
			throw new IllegalArgumentException();

		MinPQ<Node> minpq = new MinPQ<Node>(comp);
		minpq.insert(new Node(initial, null, flag));

		MinPQ<Node> twinpq = new MinPQ<Node>(comp);
		twinpq.insert(new Node(initial.twin(), null, flag));

		while (!minpq.isEmpty()) {

			Node sn = minpq.delMin();
			Node tsn = twinpq.delMin();

			if (sn.board.isGoal()) {

				// 由goal结点向前遍历得到路径
				rs = new Stack<Board>();
				Node goal = sn;
				flag = sn.moves;

				while (goal.board != initial) {
					rs.push(goal.board);
					goal = goal.pre;
				}

				rs.push(initial);
				break;
			}

			if (tsn.board.isGoal()) {
				flag = -1;
				break;
			}

			for (Board b : sn.board.neighbors()) {
				// 结点已在close处理过的列表中不添加
				if (sn.pre != null && b.equals(sn.pre.board))
					continue;

				// 新结点pre设为sn
				minpq.insert(new Node(b, sn, sn.moves + 1));
			}

			// 兄弟结点
			for (Board b : tsn.board.neighbors()) {
				if (tsn.pre != null && tsn.pre.board.equals(b))
					continue;
				twinpq.insert(new Node(b, tsn, tsn.moves + 1));
			}
		}

	}

	// public Solver(Board initial) {
	//
	// if(initial == null)
	// throw new IllegalArgumentException();
	//
	// MinPQ<Node> minpq = new MinPQ<Node>(comp);
	// minpq.insert(new Node(initial, null, flag));
	//
	// MinPQ<Node> twinpq = new MinPQ<Node>(comp);
	// twinpq.insert(new Node(initial.twin(), null, flag));
	//
	// while (!minpq.min().board.isGoal() && !twinpq.min().board.isGoal()) {
	//
	// Node sn = minpq.delMin();
	// Node tsn = twinpq.delMin();
	//
	// for (Board b : sn.board.neighbors()) {
	// // 结点已在close处理过的列表中不添加
	// if (sn.pre != null && b.equals(sn.pre.board))
	// continue;
	// // 新结点pre设为sn
	// minpq.insert(new Node(b, sn, sn.moves + 1));
	// }
	//
	// // 兄弟结点
	// for (Board b : tsn.board.neighbors()) {
	// if (tsn.pre != null && tsn.pre.board.equals(b))
	// continue;
	// twinpq.insert(new Node(b, tsn, tsn.moves + 1));
	// }
	// }
	//
	// if(minpq.min().board.isGoal()){
	//
	// // 由goal结点向前遍历得到路径
	// rs = new Stack<Board>();
	// Node goal = minpq.min();
	// flag = goal.moves;
	//
	// while (goal.board != initial) {
	// rs.push(goal.board);
	// goal = goal.pre;
	// }
	//
	// rs.push(initial);
	// }else{
	// flag = -1;
	// }
	//
	// }

	/**
	 * is the initial board solvable?起始board是否可解
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
