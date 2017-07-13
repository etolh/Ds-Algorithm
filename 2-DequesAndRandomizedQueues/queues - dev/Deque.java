

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.introcs.StdIn;
import edu.princeton.cs.introcs.StdOut;

public class Deque<Item> implements Iterable<Item> {
	
	private class Node{
		Item item;
		Node next;
		Node prior;
	}
	
	//��ͷ����βָ��
	private Node head;
	private Node tail;
	private int N; 	//����Ԫ�ظ���
	
	public Deque(){
		// construct an empty deque
		head = null;
		tail = null;
		N = 0;
	}
	
	public boolean isEmpty(){
		// is the deque empty?
		return N == 0;
	}
	
	public int size(){
		// return the number of items on the deque
		return N;
	}
	
	//����Ŀ�쳣
	private void validateItem(Item item){
		if(item == null)
			throw new IllegalArgumentException();
	}
	
	//��deque�쳣
	private void validateDeque(){
		if(isEmpty())
			throw new NoSuchElementException();
	}
	
	public void addFirst(Item item){
		// add the item to the front
		validateItem(item);
		
		Node oldHead = head;
		
		head = new Node();
		head.item = item;
		
		head.prior = null;	//ͷ���ǰ��ʼ��Ϊ��
		head.next = oldHead;
		
		if(isEmpty()){
			// ֻ��һ�����
			tail = head;
		}else{
			oldHead.prior = head;
		}
		
		N++;
	}
	
	
	public void addLast(Item item){
		// add the item to the end
		
		validateItem(item);

		Node oldtail = tail;
		
		tail = new Node();
		tail.item = item;
		
		tail.prior = oldtail;
		tail.next = null;
		
		if(isEmpty())
			head = tail;
		else
			oldtail.next = tail;
		
		N++;
	}
    
	public Item removeFirst(){
		// remove and return the item from the front
		
		validateDeque();
		
		Item item = head.item;
		head = head.next;
		N--;
		
		if(isEmpty()){
			//ֻ��һ����㣬ɾ����Ϊ��
			tail = null;
		}else
			head.prior = null;
		return item;
	}
    
	public Item removeLast(){
		// remove and return the item from the end
		validateDeque();
		
		Item item = tail.item;
		
		//tailָ��ԭ�����һ����ǰ��
		tail = tail.prior;		
		N--;
		if(isEmpty()){
			//ֻ��һ�����
			head = null;
		}
		else
			tail.next = null;
			
		return item;
	}
	 
	public Iterator<Item> iterator() {
    	// return an iterator over items in order from front to end
		return new ListIterator();
	}
	
	private class ListIterator implements Iterator<Item>{
		
		private Node currentpos = head;
		
		public boolean hasNext() {
			return currentpos != null;
		}

		public Item next() {
			
			if(currentpos == null)
				throw new NoSuchElementException();
			
			Item item = currentpos.item;
			currentpos = currentpos.next;
			return item;
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}
	}
	
	public static void main(String[] args) throws FileNotFoundException{
		// unit testing (optional)
		Deque<String> d = new Deque<String>();
		
		System.setIn(new FileInputStream(new File("queues/distinct.txt")));
		
		while(!StdIn.isEmpty()){
			String s = StdIn.readString();
//			d.addFirst(s);
			d.addLast(s);
		}
		
		for(String s : d)
			StdOut.print(s+" ");
		
		
	}
}
