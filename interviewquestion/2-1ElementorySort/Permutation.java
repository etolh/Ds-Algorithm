package com.exp.d_iq;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

/**
 * Given two integer arrays of size n, design a subquadratic algorithm to
 * determine whether one is a permutation of the other.
 * 
 * @author ºúÁÁ
 *
 */
public class Permutation {

	public static boolean isPermutation(Comparable[] a, Comparable[] b) {
		Permutation.sort(a);
		Permutation.sort(b);

		int N = a.length;
		for (int i = 0; i < N; i++)
			if (a[i].compareTo(b[i]) != 0)
				return false;
		return true;
	}

	public static void sort(Comparable[] a) {
		// Ï£¶ûÅÅÐò

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

		int N = 10;
		Integer[] a = new Integer[N];
		Integer[] b = new Integer[N];
		
		for (int i = 0; i < N; i++) {
			int x = StdRandom.uniform(N+1);
			a[i] = x;
			int y = StdRandom.uniform(N+1);
			b[i] = y;
		}
		
		if(Permutation.isPermutation(a, b)){
			StdOut.print("Yes");
		}
		Permutation.show(a);
		Permutation.show(b);
	}
}
