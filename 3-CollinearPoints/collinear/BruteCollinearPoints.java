import java.util.ArrayList;
import java.util.Arrays;

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

		Arrays.sort(points);
		for (int i = 0; i < N; i++) {
			// point is repeat
			if (i < N - 1) {
				if (points[i].compareTo(points[i + 1]) == 0)
					throw new IllegalArgumentException();
			}
		}

		num = 0;
		arrsegments = new ArrayList<LineSegment>();

		for (int i = 0; i < N; i++)
			for (int j = i + 1; j < N; j++)
				for (int k = j + 1; k < N; k++)
					for (int l = k + 1; l < N; l++) {
						double slope1 = points[i].slopeTo(points[j]);
						double slope2 = points[j].slopeTo(points[k]);
						double slope3 = points[k].slopeTo(points[l]);
						if (slope1 == slope2 && slope2 == slope3) {
							arrsegments.add(new LineSegment(points[i],
									points[l]));
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
}
