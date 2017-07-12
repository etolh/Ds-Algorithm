public class SocialNet {
	/*
	 * * Use Weighted Quick-Union Model: set setCount as a count of set, when
	 * merging two member from different set, setCount--; if setCount==1, then
	 * we can think all members are connected.
	 */

	// basic data type
	private int[] id;
	// the element count
	private int[] size;
	// set number
	int setCount;

	public SocialNet(int n) {
		size = new int[n];
		id = new int[n];

		for (int i = 0; i < n; i++) {
			id[i] = i;
			size[i] = 1;
		}

		setCount = n;
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

		setCount--;

		if (size[rootp] < size[rootq]) {
			id[rootp] = rootq;
			size[rootq] += size[rootp];
		} else {
			id[rootq] = rootp;
			size[rootp] += size[rootq];
		}
	}

}
