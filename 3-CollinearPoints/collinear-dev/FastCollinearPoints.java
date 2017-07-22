/**
 * ����������4���㼰���ϵ��߶Σ��ȶԵ����򣬴���㿪ʼ����õ㵽�������е��б�ʣ���б������
 * ������3��������ȵ�б�ʣ�����ɸ�б�ʵ��߶Ρ�
 * ����ÿ����һ����Ϊ���ģ������������е�����б�ʣ���������õ����߶�ʱ���϶��ᱧǸǰ���Ѿ��󵽵���߶�
 * ȥ�ظ���ֻ��ÿ��������߶ε����Ϊ���ĵ�ʱ�Ž��߶μ����б�
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
				// ��֮��ıʼǲ���ֱ�ӱʼǣ�comparaTo����
				if (auxp[i].compareTo(auxp[i + 1]) == 0)
					throw new IllegalArgumentException();
			}
		}
		
		for (int i = 0; i < N; i++) {

			Point ref = points[i];
			Arrays.sort(auxp, 0, N, ref.slopeOrder());

			// �ж��Ƿ����3��������ref��б����ȵĵ�, ��jΪ��㣬�鿴�Ƿ�����ͬб�ʵ㣬j:1~n-3
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

				// ͳ����һ������������ͬб�ʵ㣬�ۺ��жϣ�
				// ���ܷ����棬������ʱ������ֱ�߶�ʱ��ִ�е����һ����ֱ������ѭ������ֱ�߶ν�������ӽ�ȥ
				if (arr.size() >= 4) {

					// ��n(n>=4)����õ��ֱ��
					int n = arr.size();
					Point[] ps = new Point[n];
					arr.toArray(ps);
					Arrays.sort(ps);
					start = ps[0]; // �õ����

					if (start == ref) {
						num++; // num��������߶�ͬ��
						// ����߶�ֻ����arrlist�������߶���Ŀ�������޷����г�ʼ��
						segs.add(new LineSegment(ps[0], ps[n - 1]));
					}
					// ���±�����kʱ��ʱб������㲻һ�£����¸������j����jӦ��k��ʼ���㣬ǰ���Ѽ���ĺ���
					// ��ע��j������Ҫ��1�������j=k-1��ͬ�����⵽ĩβֱ�����������
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
