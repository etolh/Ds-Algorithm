
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Outcast {

	private WordNet wordnet;

	// constructor takes a WordNet object
	public Outcast(WordNet wordnet) {
		this.wordnet = wordnet;
	}

	// given an array of WordNet nouns, return an outcast
	public String outcast(String[] nouns) {

		int maxdist = -1;
		String opti = null;

		for (int i = 0; i < nouns.length; i++) {

			int dist = 0;
			for (String s : nouns) {
				dist += wordnet.distance(nouns[i], s);
			}

			if (dist > maxdist) {
				maxdist = dist;
				opti = nouns[i];
			}
		}

		return opti;
	}

	// see test client below
	public static void main(String[] args) {

		WordNet wordnet = new WordNet(args[0], args[1]);
		Outcast outcast = new Outcast(wordnet);

		for (int t = 2; t < args.length; t++) {
			In in = new In(args[t]);
			String[] nouns = in.readAllStrings();
			StdOut.println(args[t] + ": " + outcast.outcast(nouns));
		}
	}
}
