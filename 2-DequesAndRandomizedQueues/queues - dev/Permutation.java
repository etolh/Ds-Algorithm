

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import edu.princeton.cs.introcs.StdIn;
import edu.princeton.cs.introcs.StdOut;

public class Permutation {
	
	public static void main(String[] args) throws FileNotFoundException{
		
		
		RandomizedQueue<String> rq = new RandomizedQueue<String>();
		int k = Integer.parseInt(args[0]);
		
		System.setIn(new FileInputStream(new File("queues/duplicates.txt")));
		
		for(int i = 0 ; i < k; i++){
			String s = StdIn.readString();
			rq.enqueue(s);
		}
		
		for(String s : rq){
			StdOut.println(s);
		}
	}
}
