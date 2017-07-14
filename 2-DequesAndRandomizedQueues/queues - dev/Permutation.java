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
				// �ȶ�ȡk����rq��rq�䵱��ˮ��
				i++;
				rq.enqueue(s);
			} else {
				// ����[0,i)֮����������p����С��k�滻
				int p = StdRandom.uniform(i);
				if (p < k) {
					rq.dequeue();
					rq.enqueue(s);
				}
				i++; // i��ʾѡȡ��i��Ԫ��
			}
		}

		for (String s : rq)
			StdOut.println(s);

	}

	/**
	 * 
	 * @param stream
	 *            :��ʾ�����δ֪������
	 * @param reservior
	 *            ����ʾ��ˮ��
	 */
	public <Item> void reserviorsampling(Item[] stream, Item[] reservior, int k) {

		// ��ȡ��k��������ˮ��
		for (int i = 0; i < k; i++)
			reservior[i] = stream[i];

		int n = stream.length;

		for (int j = k; j < n; j++) {
			int p = StdRandom.uniform(0, j);
			if (p < k)
				// ��С�ڣ�����stream[j],���滻
				reservior[p] = stream[j];
		}
	}
}
