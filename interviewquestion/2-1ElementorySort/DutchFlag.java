package com.exp.d_iq;

import edu.princeton.cs.algs4.StdOut;

public class DutchFlag {

	private String[] a;

	public static void sort(String[] a) {
		// Ï£¶ûÅÅÐò
		int N = a.length;
		int h = 1;

		while (h < N / 3)
			h = h * 3 + 1;

		while (h >= 1) {

			for (int i = h; i < N; i++)
				for (int j = i; j >= h; j -= h)
					if (color(a, j) <color(a, j-h))
						swap(a, j, j - h);

			h = h / 3;
		}

	}

	// exchange a[i] and a[j]
	private static void swap(String[] a, int i, int j) {
		String temp = a[i];
		a[i] = a[j];
		a[j] = temp;
	}

	private static int color(String[] a, int i) {
		if(a[i].equals("red"))
			return 0;
		else if(a[i].equals("white"))
			return 1;
		else
			return 2;
	}
	
}
