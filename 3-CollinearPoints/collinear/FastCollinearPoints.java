import java.util.ArrayList;
import java.util.Arrays;

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

		Arrays.sort(points);
		for (int i = 0; i < N; i++) {
			// point is repeat
			if (i < N - 1) {
				if (points[i].compareTo(points[i + 1]) == 0)
					throw new IllegalArgumentException();
			}
		}

		num = 0;
		Point[] auxp = points.clone();

		for (int i = 0; i < N; i++) {

			Point ref = points[i];
			Arrays.sort(auxp, 0, N, ref.slopeOrder());

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

				if (arr.size() >= 4) {

					int n = arr.size();
					Point[] ps = new Point[n];
					arr.toArray(ps);
					Arrays.sort(ps);
					start = ps[0];

					if (start == ref){
						num++;
						segs.add(new LineSegment(ps[0], ps[n - 1]));
					}

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
}
