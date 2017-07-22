/**
 * 排序求解包含4个点及以上的线段：先对点排序，从起点开始，求该点到其他所有点的斜率，将斜率排序，
 * 若存在3个以上相等的斜率，即组成该斜率的线段。
 * 由于每次以一个点为中心，计算其他所有点对其的斜率，当求包含该点的最长线段时，肯定会抱歉前面已经求到的最长线段
 * 去重复：只有每次所求最长线段的起点为中心点时才将线段加入列表
 */

package com.exp.f_pa;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class FastCollinearPoints {

	private int num;
	private LineSegment[] segments;
	private ArrayList<LineSegment> segs = new ArrayList<LineSegment>();

	/**
	 * finds all line segments containing 4 or more points
	 * 
	 * @param points
	 */
	public FastCollinearPoints(Point[] points) {

		if (points == null)
			throw new IllegalArgumentException();

		int N = points.length;

		for (int i = 0; i < N; i++) {
			// point is null
			if (points[i] == null)
				throw new IllegalArgumentException();
		}
		
		num = 0;
		Point[] auxp = points.clone();
		Arrays.sort(auxp);
		
		for (int i = 0; i < N; i++) {
			// point is repeat
			if (i < N - 1) {
				// 点之间的笔记不能直接笔记，comparaTo方法
				if (auxp[i].compareTo(auxp[i + 1]) == 0)
					throw new IllegalArgumentException();
			}
		}
		
		for (int i = 0; i < N; i++) {

			Point ref = points[i];
			Arrays.sort(auxp, 0, N, ref.slopeOrder());

			// 判断是否存在3个以上与ref的斜率相等的点, 以j为起点，查看是否有相同斜率点，j:1~n-3
			for (int j = 1; j < N - 2; j++) {

				ArrayList<Point> arr = new ArrayList<Point>();
				arr.add(ref);

				Point start = null;

				int k = j;
				for (; k < N; k++) {

					if (ref.slopeTo(auxp[k]) == ref.slopeTo(auxp[j])) {
						arr.add(auxp[k]);

					} else {
						break;
					}
				}

				// 统计完一个起点的所有相同斜率点，综合判断，
				// 不能放里面，放里面时当处理垂直线段时，执行到最后一个点直接跳出循环，垂直线段将不会添加进去
				if (arr.size() >= 4) {

					// 由n(n>=4)个点得到最长直线
					int n = arr.size();
					Point[] ps = new Point[n];
					arr.toArray(ps);
					Arrays.sort(ps);
					start = ps[0]; // 得到起点

					if (start == ref) {
						num++; // num与添加新线段同步
						// 添加线段只能用arrlist，由于线段数目不定，无法进行初始化
						segs.add(new LineSegment(ps[0], ps[n - 1]));
					}
					// 当下标移至k时此时斜率与起点不一致，重新更换起点j，且j应从k开始计算，前面已计算的忽略
					// 但注意j后面需要加1，因此设j=k-1，同样避免到末尾直接跳出的情况
					j = k - 1;
				}

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
	 * @return
	 */
	public LineSegment[] segments() {
		int n = segs.size();
		segments = new LineSegment[n];
		segs.toArray(segments);
		return segments;
	}

	public static void main(String[] args) {

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
		FastCollinearPoints collinear = new FastCollinearPoints(points);

		for (LineSegment segment : collinear.segments()) {
			StdOut.println(segment);
			segment.draw();
		}

		StdDraw.show();
	}
}
