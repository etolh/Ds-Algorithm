
import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {

	private Item[] a;
	private int N;

	public RandomizedQueue() {
		// construct an empty randomized queue
		a = (Item[]) new Object[1];
		N = 0;
	}

	public boolean isEmpty() {
		// is the queue empty?
		return N == 0;
	}

	public int size() {
		// return the number of items on the queue
		return N;
	}

	private void resize(int max) {
		Item[] temp = (Item[]) new Object[max];

		for (int i = 0; i < N; i++)
			temp[i] = a[i];

		a = temp;
	}
	

	private void validateItem(Item item){
		if(item == null)
			throw new IllegalArgumentException();
	}
	

	private void validateQueue(){
		if(isEmpty())
			throw new NoSuchElementException();
	}
		
	public void enqueue(Item item) {
		// add the item
		validateItem(item);
		
		if (a.length == N)
			resize(a.length * 2);

		a[N++] = item;
	}

	public Item dequeue() {
		// remove and return a random item
		validateQueue();
		
		int i = StdRandom.uniform(N); 
		Item item = a[i];
		a[i] = null;

		a[i] = a[N - 1];
		a[N - 1] = null;

		N--;
		if (N > 0 && N == a.length / 4)
			resize(a.length / 2);

		return item;
	}

	public Item sample() {
		// return (but do not remove) a random item
		validateQueue();
		
		int i = StdRandom.uniform(N);
		return a[i];
	}

	public Iterator<Item> iterator() {
		// return an independent iterator over items in random order
		return new RandomArrayIterator();
	}
	
	private class RandomArrayIterator implements Iterator<Item>{
		
		private int pos = 0;
		private int[] index = new int[N];
		
		public RandomArrayIterator() {

			for(int i = 0; i < N; i++)
				index[i] = i;
			
			StdRandom.shuffle(index);
		}
		
		@Override
		public boolean hasNext() {
			return pos < N;
		}

		@Override
		public Item next() {
			
			if(pos == N)
				throw new NoSuchElementException();
				
			Item item = a[index[pos]];
			pos++;
			return item;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}
	
	public static void main(String[] args) {
		// unit testing (optional)
		RandomizedQueue<String> q = new RandomizedQueue<String>();
		while(!StdIn.isEmpty()){
			String s = StdIn.readString();
			q.enqueue(s);
		}
		
		StdOut.println("random:"+q.sample());
		for(String s : q)
			StdOut.print(s+" ");
	}
}
