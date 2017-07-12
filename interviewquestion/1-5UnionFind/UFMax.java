import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.introcs.StdRandom;

/**
 * Use a array to record the max element of a connected component. When union
 * two component, compare the max and record.
 * 
 * @author ºúÁÁ
 *
 */
public class UFMax {

	// basic data type
	private int[] id;
	// the element count
	private int[] size;
	// max arrry
	private int[] max;
	private int n;

	public UFMax(int n) {
		this.n = n;
		size = new int[n];
		id = new int[n];
		max = new int[n];

		for (int i = 0; i < n; i++) {
			id[i] = i;
			max[i] = i;
			size[i] = 1;
		}
	}

	// find root id
	public int find(int x) {

		int y = x;
		while (id[x] != x) {
			x = id[x];
		}

		// path compression
		while (id[y] != x) {
			int tem = id[y];
			id[y] = x;
			y = tem;
		}

		return x;
	}

	// union and check
	public void Union(int p, int q) {

		int rootp = find(p);
		int rootq = find(q);

		if (rootp == rootq)
			return;

		if (size[rootp] < size[rootq]) {
			id[rootp] = rootq;
			size[rootq] += size[rootp];

			if (max[rootq] < max[rootp])
				max[rootq] = max[rootp];

		} else {
			id[rootq] = rootp;
			size[rootp] += size[rootq];

			if (max[rootp] < max[rootq])
				max[rootp] = max[rootq];
		}
	}

	public int findMax(int i) {
		int root = find(i);
		return max[root];
	}

	private void printUion(int p) {
		// ´òÓ¡¼¯ºÏ
		int root = find(p);

		for (int i = 0; i < n; i++) {
			if (find(i) == root) {
				// StdOut.print("[");
				StdOut.print(i + " ");
				// StdOut.print("]");
			}
		}
	}

	public static void main(String[] args) {

		UFMax uf = new UFMax(10);
		int i = 0;
		while (i++ < 10) {
			int p = StdRandom.uniform(10);
			int q = StdRandom.uniform(10);

			uf.Union(p, q);
			int max = uf.findMax(p);

			StdOut.printf("p = %d q = %d\t ", p, q);
			uf.printUion(p);
			StdOut.println("\tmax = " + max);
		}
	}
}
