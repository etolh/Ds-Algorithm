import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
	
	public static void main(String[] args) {
		
		RandomizedQueue<String> rq = new RandomizedQueue<String>();
		int k = Integer.parseInt(args[0]);
		
		for(int i = 0 ; i < k; i++){
			String s = StdIn.readString();
			rq.enqueue(s);
		}
		
		for(String s : rq){
			StdOut.println(s);
		}
	}
}
