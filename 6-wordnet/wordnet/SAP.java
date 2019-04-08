import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class SAP {

	private int ancestor;
	private Digraph G;
	private BreadthFirstDirectedPaths bfsv;
	private BreadthFirstDirectedPaths bfsw;

	// constructor takes a digraph (not necessarily a DAG)
	public SAP(Digraph G) {
		this.G = new Digraph(G);;
		this.ancestor = -1;
	}

	// length of shortest ancestral path between v and w; -1 if no such path
	public int length(int v, int w) {

		bfsv = new BreadthFirstDirectedPaths(G, v);
		bfsw = new BreadthFirstDirectedPaths(G, w);

		int mindist = Integer.MAX_VALUE;
		for (int i = 0; i < G.V(); i++) {
			if (bfsv.hasPathTo(i) && bfsw.hasPathTo(i)) {
				int dist = bfsv.distTo(i) + bfsw.distTo(i);
				if (dist < mindist) {
					mindist = dist;
					ancestor = i;
				}
			}
		}
		if(mindist == Integer.MAX_VALUE) {	
    		ancestor = -1;
    		return -1;
    	}
		return mindist;
	}

	// a common ancestor of v and w that participates in a shortest ancestral path;
	// -1 if no such path
	public int ancestor(int v, int w) {
		length(v, w);
		return ancestor;
	}

	// length of shortest ancestral path between any vertex in v and any vertex in
	// w; -1 if no such path
	public int length(Iterable<Integer> v, Iterable<Integer> w) {
		
		if(v == null || w == null)
			throw new IllegalArgumentException();
		
		bfsv = new BreadthFirstDirectedPaths(G, v);
		bfsw = new BreadthFirstDirectedPaths(G, w);

		int mindist = Integer.MAX_VALUE;
		for (int i = 0; i < G.V(); i++) {
			if (bfsv.hasPathTo(i) && bfsw.hasPathTo(i)) {
				int dist = bfsv.distTo(i) + bfsw.distTo(i);
				if (dist < mindist) {
					mindist = dist;
					ancestor = i;
				}
			}
		}
		if (mindist == Integer.MAX_VALUE)
			return -1;
		return mindist;
	}

	// a common ancestor that participates in shortest ancestral path; -1 if no such
	// path
	public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
		length(v, w);
		return ancestor;
	}

	// do unit testing of this class
	public static void main(String[] args) {

		In in = new In(args[0]);
		Digraph G = new Digraph(in);
		SAP sap = new SAP(G);
		while (!StdIn.isEmpty()) {
			int v = StdIn.readInt();
			int w = StdIn.readInt();
			int length = sap.length(v, w);
			int ancestor = sap.ancestor(v, w);
			StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
		}
	}
}
