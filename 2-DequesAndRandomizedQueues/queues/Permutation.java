import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
	
	public static void main(String[] args) {

		RandomizedQueue<String> rq = new RandomizedQueue<String>();
		int k = Integer.parseInt(args[0]);

		while (!StdIn.isEmpty()) {
			String s = StdIn.readString();
			rq.enqueue(s);
		}

		int i = 0;
		for (String s : rq) {
			if (i++ < k) {
				StdOut.println(s);
			}
		}
	}
}
