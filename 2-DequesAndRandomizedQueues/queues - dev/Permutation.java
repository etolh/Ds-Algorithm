import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Random;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class Permutation {

	public static void main(String[] args) throws FileNotFoundException {

		RandomizedQueue<String> rq = new RandomizedQueue<String>();
		int k = Integer.parseInt(args[0]);

		System.setIn(new FileInputStream(new File("queues/permutation4.txt")));

		// while (!StdIn.isEmpty()) {
		// String s = StdIn.readString();
		// rq.enqueue(s);
		// }
		//
		// int i = 0;
		//
		// for (String s : rq) {
		// if (i++ < k) {
		// StdOut.println(s);
		// }
		// }

		int i = 1;
		while (!StdIn.isEmpty()) {
			String s = StdIn.readString();
			if (k == 0)
				break;
			if (i <= k) {
				// 先读取k个到rq，rq充当蓄水池
				i++;
				rq.enqueue(s);
			} else {
				// 生成[0,i)之间的随机整数p，若小于k替换
				int p = StdRandom.uniform(i);
				if (p < k) {
					rq.dequeue();
					rq.enqueue(s);
				}
				i++; // i表示选取第i个元素
			}
		}

		for (String s : rq)
			StdOut.println(s);

	}

	/**
	 * 
	 * @param stream
	 *            :表示输入的未知数据流
	 * @param reservior
	 *            ：表示蓄水池
	 */
	public <Item> void reserviorsampling(Item[] stream, Item[] reservior, int k) {

		// 先取出k个放入蓄水池
		for (int i = 0; i < k; i++)
			reservior[i] = stream[i];

		int n = stream.length;

		for (int j = k; j < n; j++) {
			int p = StdRandom.uniform(0, j);
			if (p < k)
				// 若小于，则保留stream[j],并替换
				reservior[p] = stream[j];
		}
	}
}
