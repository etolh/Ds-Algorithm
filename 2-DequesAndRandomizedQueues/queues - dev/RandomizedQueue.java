

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.introcs.StdIn;
import edu.princeton.cs.introcs.StdOut;
import edu.princeton.cs.introcs.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {

	private Item[] a; // ��������
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
		// ��̬���������С
		Item[] temp = (Item[]) new Object[max];

		for (int i = 0; i < N; i++)
			temp[i] = a[i];

		a = temp;
	}
	
	//����Ŀ�쳣
	private void validateItem(Item item){
		if(item == null)
			throw new IllegalArgumentException();
	}
	
	//��deque�쳣
	private void validateQueue(){
		if(isEmpty())
			throw new NoSuchElementException();
	}
		
	public void enqueue(Item item) {
		// add the item
		validateItem(item);
		
		if (a.length == N)
			resize(a.length * 2);
		// ��β���
		a[N++] = item;
	}

	public Item dequeue() {
		// remove and return a random item
		validateQueue();
		
		int i = StdRandom.uniform(N); // ���ѡȡ[0,N)������
		Item item = a[i];
		a[i] = null;	//����
		
		N--;

		// ǰ��i+1��N-1
		for (int j = i; j < N - 1; j++)
			a[j] = a[j + 1];

		if (N > 0 && N == a.length / 4)
			resize(a.length / 2);

		return item;
	}

	public Item sample() {
		// return (but do not remove) a random item
		validateQueue();
		
		int i = StdRandom.uniform(N); // ���ѡȡ[0,N)������
		return a[i];
	}

	public Iterator<Item> iterator() {
		// return an independent iterator over items in random order
		return new RandomArrayIterator();
	}
	
	private class RandomArrayIterator implements Iterator<Item>{
		
		private int pos = 0;	//������a���±�0��ʼ
		private int[] index = new int[N];
		
		public RandomArrayIterator() {
			// TODO ����0-N-1���������
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
	
	public static void main(String[] args) throws FileNotFoundException {
		// unit testing (optional)
		RandomizedQueue<String> q = new RandomizedQueue<String>();
		
		System.setIn(new FileInputStream(new File("queues/distinct.txt")));
		
		while(!StdIn.isEmpty()){
			String s = StdIn.readString();
			q.enqueue(s);
		}
		
		
		StdOut.println("random:"+q.sample());
		
		for(String s : q)
			StdOut.print(s+" ");
		
		
	}
}
