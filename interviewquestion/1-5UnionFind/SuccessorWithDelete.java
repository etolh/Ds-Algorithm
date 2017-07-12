import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.introcs.StdRandom;

/**
 * It's not trivial to find the smallest y in S that y >= x when x is removed
 * from S£¬ because y isn't simple x + 1 when remove x, y maybe x + 1; next time
 * remove x - 1, y isn't x rather than x + 1 We can use Question2:UFMax£¬we set a
 * array rm[n] to stare weather x is deleted, if x is deleted, we figure out x +
 * 1, x - 1 is deleted too? if it is, we will union x and x + 1, x - 1 find y:
 * if x isn't removed,y = x, else we find the max element in the component
 * containing x, y = findMax(x) + 1
 * 
 * @author ºúÁÁ
 *
 */

public class SuccessorWithDelete {

	// basic data type
	private int[] id;
	// the element count
	private int[] size;
	// max arrry
	private int[] max;
	// record a[i] is removed
	private boolean[] rm;
	private int n;

	public SuccessorWithDelete(int n) {
		this.n = n;
		size = new int[n];
		id = new int[n];
		max = new int[n];
		rm = new boolean[n];

		for (int i = 0; i < n; i++) {
			id[i] = i;
			max[i] = i;
			size[i] = 1;
			rm[i] = false;
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
	public void union(int p, int q) {

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

	public void delete(int x) {

		if (x < 0 | x >= n)
			throw new IllegalArgumentException();

		if (rm[x])
			return;

		rm[x] = true;

		// Ç°
		if (x + 1 < n && rm[x + 1])
			union(x, x + 1);

		// ºó
		if (x - 1 > 0 && rm[x - 1])
			union(x, x - 1);
	}

	public int findSuc(int x) {
		if (!rm[x])
			return x;
		else
			return findMax(x) + 1;
	}

	private void printArr() {

		for (int i = 0; i < n; i++)
			if (!rm[i])
				StdOut.print(i + " ");

		StdOut.println();
	}

	public static void main(String[] args) {

		SuccessorWithDelete uf = new SuccessorWithDelete(10);
		int i = 0;

		while (i++ < 10) {

			int p = StdRandom.uniform(10);

			StdOut.printf("Before Remove %d\n", p);
			uf.printArr();

			uf.delete(p);
			int suc = uf.findSuc(p);

			StdOut.printf("After Remove %d\n", p);
			uf.printArr();
			StdOut.printf("suc = %d\n\n", suc);
		}
	}
}
