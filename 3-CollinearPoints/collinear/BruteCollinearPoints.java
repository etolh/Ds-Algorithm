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

		num = 0;
		Point[] auxp = points.clone();
		Arrays.sort(auxp);

		for(int i = 0 ; i < N; i++){
			if(i < N - 1){
				// 点之间的笔记不能直接笔记，comparaTo方法
				if (auxp[i].compareTo(auxp[i + 1]) == 0)
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
}
