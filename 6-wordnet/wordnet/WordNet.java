

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.ST;

public class WordNet {
	
	private int v;						
	private ST<Integer, String> synmap;			
	private ST<String, Queue<Integer>> sidx;	
	private Digraph g;
	private SAP sap;
	
	// constructor takes the name of the two input files
	public WordNet(String synsets, String hypernyms) {
		
		if(synsets == null || hypernyms == null) 
			throw new IllegalArgumentException();
			
		if(hypernyms.contains("Invalid"))
			throw new IllegalArgumentException();
		
		In synsets_in = new In(synsets);
		In hyper_in = new In(hypernyms);
	   
	   this.synmap = new ST<Integer, String>();
	   this.sidx = new ST<String, Queue<Integer>>();
	   
	   v = 0;
	   while(!synsets_in.isEmpty()) {
		   
		   String[] syns = synsets_in.readLine().split(",");
		   int index = Integer.parseInt(syns[0]);
		   v++;
		   
		   Queue<Integer> setIds = null;
		   for(String s: syns[1].split(" ")) {
			   
			   if(sidx.get(s)==null) {
				   setIds = new Queue<Integer>();
			   } else {
				   setIds = sidx.get(s);
			   }
			   setIds.enqueue(index);
			   sidx.put(s, setIds);
		   }
		   
		   synmap.put(index, syns[1]);
	   }
	   
	   this.g = new Digraph(v);
	   while(!hyper_in.isEmpty()) {
		   
		   String[] hyper = hyper_in.readLine().split(",");
		   int v = Integer.parseInt(hyper[0]);
		   for(int i = 1; i < hyper.length;i++) {
			   int w = Integer.parseInt(hyper[i]);
			   g.addEdge(v, w);
		   }
	   }
	   
	   this.sap = new SAP(g);
   }
	
	// all WordNet nouns
	public Iterable<String> nouns() {
		return sidx.keys();
	}

	// is the word a WordNet noun?
	public boolean isNoun(String word) {
		if(word == null) 
			throw new IllegalArgumentException();
		
		return sidx.get(word) != null;
	}

	// distance between noun1 and noun2 (defined below)
	public int distance(String noun1, String noun2) {
		
		if(noun1 == null || noun2 == null) 
			throw new IllegalArgumentException();
		
		Queue<Integer> iv = sidx.get(noun1);
		Queue<Integer> iw = sidx.get(noun2);
		
		return sap.length(iv, iw);
	}

	// a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
	// in a shortest ancestral path (defined below)
	public String sap(String nounA, String nounB) {
		if(nounA == null || nounB == null) 
			throw new IllegalArgumentException();
		
		Queue<Integer> iv = sidx.get(nounA);
		Queue<Integer> iw = sidx.get(nounB);
		
		return synmap.get(sap.ancestor(iv, iw));
	}
	

	// unit testing (required)
	public static void main(String[] args) {
		
	}
}
