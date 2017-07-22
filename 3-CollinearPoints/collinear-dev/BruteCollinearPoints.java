/**
 * ����������а����ĸ�����߶�
 */

package com.exp.f_pa;

import java.util.ArrayList;
import java.util.Arrays;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints {

	private int num;
	private ArrayList<LineSegment> arrsegments;

	/**
	 * finds all line segments containing 4 points
	 * 
	 * @param points
	 */
	public BruteCollinearPoints(Point[] points) {

		if (points == null)
			throw new IllegalArgumentException();

		int N = points.length;

		for (int i = 0; i < N; i++) {
			// point is null
			if (points[i] == null)
				throw new IllegalArgumentException();
		}

		// pointsָ���������󲻱䣬ʹ�ÿ�¡����
		num = 0;
		Point[] auxp = points.clone();
		Arrays.sort(auxp);

		for (int i = 0; i < N; i++) {
			if (i < N - 1) {
				// ��֮��ıʼǲ���ֱ�ӱʼǣ�comparaTo����
				if (auxp[i].compareTo(auxp[i + 1]) == 0)
				// if (auxp[i].slopeTo(auxp[i + 1]) == Double.NEGATIVE_INFINITY)
					throw new IllegalArgumentException();
			}
		}

		arrsegments = new ArrayList<LineSegment>();

		for (int i = 0; i < N; i++)
			for (int j = i + 1; j < N; j++)
				for (int k = j + 1; k < N; k++)
					for (int l = k + 1; l < N; l++) {
						double slope1 = auxp[i].slopeTo(auxp[j]);
						double slope2 = auxp[j].slopeTo(auxp[k]);
						double slope3 = auxp[k].slopeTo(auxp[l]);
						if (slope1 == slope2 && slope2 == slope3) {
							arrsegments.add(new LineSegment(auxp[i], auxp[l]));
							num++;
						}
					}
	}

	/**
	 * the number of line segments
	 * 
	 * @return
	 */
	public int numberOfSegments() {
		return num;
	}

	/**
	 * the line segments
	 * 
	 * @return LineSegment[]
	 */
	public LineSegment[] segments() {
		LineSegment[] a = new LineSegment[num];
		arrsegments.toArray(a);
		return a;
	}

	public static void main(String[] args) {

		// System.setIn(new FileInputStream(new File("collinear/input10.txt")));

		// read the n points from a file
		In in = new In(args[0]);
		int n = in.readInt();
		Point[] points = new Point[n];
		for (int i = 0; i < n; i++) {
			int x = in.readInt();
			int y = in.readInt();
			points[i] = new Point(x, y);
		}

		// draw the points
		StdDraw.enableDoubleBuffering();
		StdDraw.setPenRadius(.01);
		StdDraw.setXscale(0, 32768);
		StdDraw.setYscale(0, 32768);
		for (Point p : points) {
			p.draw();
		}
		StdDraw.show();

		// print and draw the line segments
		BruteCollinearPoints collinear = new BruteCollinearPoints(points);
		for (LineSegment segment : collinear.segments()) {
			StdOut.println(segment);
			segment.draw();
		}

		StdDraw.show();
	}
}