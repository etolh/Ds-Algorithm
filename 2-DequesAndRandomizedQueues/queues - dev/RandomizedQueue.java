

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.introcs.StdIn;
import edu.princeton.cs.introcs.StdOut;
import edu.princeton.cs.introcs.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {

	private Item[] a; // 队列数组
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
		// 动态调整数组大小
		Item[] temp = (Item[]) new Object[max];

		for (int i = 0; i < N; i++)
			temp[i] = a[i];

		a = temp;
	}
	
	//空项目异常
	private void validateItem(Item item){
		if(item == null)
			throw new IllegalArgumentException();
	}
	
	//空deque异常
	private void validateQueue(){
		if(isEmpty())
			throw new NoSuchElementException();
	}
		
	public void enqueue(Item item) {
		// add the item
		validateItem(item);
		
		if (a.length == N)
			resize(a.length * 2);
		// 对尾入队
		a[N++] = item;
	}

	public Item dequeue() {
		// remove and return a random item
		validateQueue();
		
		int i = StdRandom.uniform(N); // 随机选取[0,N)的整数
		Item item = a[i];
		a[i] = null;	//游离
		
		N--;

		// 前移i+1到N-1
		for (int j = i; j < N - 1; j++)
			a[j] = a[j + 1];

		if (N > 0 && N == a.length / 4)
			resize(a.length / 2);

		return item;
	}

	public Item sample() {
		// return (but do not remove) a random item
		validateQueue();
		
		int i = StdRandom.uniform(N); // 随机选取[0,N)的整数
		return a[i];
	}

	public Iterator<Item> iterator() {
		// return an independent iterator over items in random order
		return new RandomArrayIterator();
	}
	
	private class RandomArrayIterator implements Iterator<Item>{
		
		private int pos = 0;	//从序列a的下标0开始
		private int[] index = new int[N];
		
		public RandomArrayIterator() {
			// TODO 生成0-N-1的随机序列
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
