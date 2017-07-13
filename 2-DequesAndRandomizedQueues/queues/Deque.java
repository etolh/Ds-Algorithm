import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Deque<Item> implements Iterable<Item> {
	
	private class Node{
		Item item;
		Node next;
		Node prior;
	}
	
	private Node head;
	private Node tail;
	private int N;
	
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
	

	private void validateItem(Item item){
		if(item == null)
			throw new IllegalArgumentException();
	}
	
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
		
		head.prior = null;	
		head.next = oldHead;
		
		if(isEmpty()){
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
			tail = null;
		}else
			head.prior = null;
		return item;
	}
    
	public Item removeLast(){
		// remove and return the item from the end
		validateDeque();
		
		Item item = tail.item;
		
		//tail指向原来最后一个的前驱
		tail = tail.prior;		
		N--;
		if(isEmpty()){
			//只有一个结点
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
	
	public static void main(String[] args) {

		Deque<String> d = new Deque<String>();	
		while(!StdIn.isEmpty()){
			String s = StdIn.readString();
			d.addLast(s);
		}
		
		for(String s : d)
			StdOut.print(s+" ");
	}
}
