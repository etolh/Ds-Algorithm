package com.exp.d_iq;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

/**
 * Intersection of two set
 * @author 胡亮
 *
 */

public class Intersection {

	// 2D点数据结构
	private class Point implements Comparable<Point> {

		private int x;
		private int y;

		public Point(int x, int y) {
			this.x = x;
			this.y = y;
		}

		public int compareTo(Point o) {

			if (this.x < o.x)
				return -1;
			else if (this.x > o.x)
				return 1;
			else {
				if (this.y < o.y)
					return -1;
				else if (this.y > o.y)
					return 1;
			}

			return 0;
		}

		public String toString() {
			return "(" + x + "," + y + ")";
		}
	}

	public static Comparable[] exactSame(Comparable[] a, Comparable[] b) {

		Intersection.sort(a);
		Intersection.sort(b);

		int N = a.length;
		Comparable[] s = new Point[N];
		int k = 0;

		for (int i = 0; i < N; i++) {

			for (int j = 0; j < N; j++) {

				if (less(a[i], b[j]))
					// a[i] < b[j]
					break;
				else if (a[i].compareTo(b[j]) == 0) {
					// a[i] == b[j]
					s[k++] = a[i];
					break;
				}
			}
		}

		return s;
	}

	public static void sort(Comparable[] a) {
		// 希尔排序

		int N = a.length;
		int h = 1;

		while (h < N / 3)
			h = h * 3 + 1;

		while (h >= 1) {
			
			for (int i = h; i < N; i++)
				for (int j = i; j >= h; j -= h) 
					if (less(a[j], a[j - h]))
						exch(a, j, j - h);
			
			h = h / 3;
		}
		
	}

	// if v < w return true
	private static boolean less(Comparable v, Comparable w) {
		return v.compareTo(w) < 0;
	}

	// exchange a[i] and a[j]
	private static void exch(Comparable[] a, int i, int j) {
		Comparable temp = a[i];
		a[i] = a[j];
		a[j] = temp;
	}

	private static void show(Comparable[] a) {
		int N = a.length;

		for (int i = 0; i < N; i++)
			StdOut.print(a[i] + " ");

		StdOut.println();
	}

	public static boolean isSorted(Comparable[] a) {

		int N = a.length;
		for (int i = 1; i < N; i++)
			if (less(a[i], a[i - 1]))
				return false;

		return true;
	}

	public static void main(String[] args) {

		// int N = StdIn.readInt();
		int N = 10;
		Point[] a = new Point[10];
		Point[] b = new Point[10];
		Intersection ins = new Intersection();

		for (int i = 0; i < N; i++) {
			int ax = StdRandom.uniform(N);
			int ay = StdRandom.uniform(N);

			// 内部类
			a[i] = ins.new Point(ax, ay);

			int bx = StdRandom.uniform(N);
			int by = StdRandom.uniform(N);

			b[i] = ins.new Point(bx, by);
		}

		Intersection.sort(a);
		Intersection.sort(b);

		Intersection.show(a);
		Intersection.show(b);

		Comparable[] s = Intersection.exactSame(a, b);
		Intersection.show(s);
	}
}
