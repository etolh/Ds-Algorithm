/**
 * 排序求解包含4个点及以上的线段：先对点排序，从起点开始，求该点到其他所有点的斜率，将斜率排序，
 * 若存在3个以上相等的斜率，即组成该斜率的线段。
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class FastCollinearPoints {

	private int num;
	// private LineSegment[] segments;
	private ArrayList<LineSegment> segs = new ArrayList<LineSegment>();

	/**
	 * finds all line segments containing 4 or more points
	 * 
	 * @param points
	 */
	public FastCollinearPoints(Point[] points) {

		int N = points.length;
		num = 0;
		Point[] auxp = points.clone();

		Arrays.sort(points);

		for (int i = 0; i < N; i++) {

			Point ref = points[i];
			Arrays.sort(auxp, 0, N, ref.slopeOrder());
			
			// 判断是否存在3个以上与ref的斜率相等的点, j-起点
			for (int j = 1; j < N; j++) {

				ArrayList<Point> arr = new ArrayList<Point>();
				arr.add(ref);
				arr.add(auxp[j]);
				
				Point start = null, end = null;
				
 				for (int k = j + 1; k < N; k++) {

					if (ref.slopeTo(auxp[k]) == ref.slopeTo(auxp[j])) {
						arr.add(auxp[k]);

					} else {

						if (arr.size() >= 4) {
							num++;
							
							LineSegment line = getLine(arr);
							segs.add(line);

						}
						
						j = k;
						break;
					}
				}

			}
		}

	}

	// 由n(n>=4)个点得到最长直线
	private LineSegment getLine(List<Point> arr) {

		int n = arr.size();
		Point[] ps = new Point[n];
		arr.toArray(ps);
		Arrays.sort(ps);
		return new LineSegment(ps[0], ps[n - 1]);
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
		LineSegment[] segments = new LineSegment[n];
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
