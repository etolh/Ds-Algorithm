import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
	
	public static void main(String[] args) {

		RandomizedQueue<String> rq = new RandomizedQueue<String>();
		int k = Integer.parseInt(args[0]);

		int i = 1;
		while (!StdIn.isEmpty()) {
			String s = StdIn.readString();
			if (k == 0)
				break;
			if (i <= k) {
				i++;
				rq.enqueue(s);
			} else {
				int p = StdRandom.uniform(i);
				if (p < k) {
					rq.dequeue();
					rq.enqueue(s);
				}
				i++;
			}
		}

		for (String s : rq)
			StdOut.println(s);
	}
}
