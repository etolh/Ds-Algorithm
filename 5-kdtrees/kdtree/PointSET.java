
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;

public class PointSET {

	private int n;
	private Set<Point2D> set;

	// construct an empty set of points
	public PointSET() {
		this.n = 0;
		this.set = new TreeSet<Point2D>();
	}

	// is the set empty?
	public boolean isEmpty() {
		return n == 0;
	}

	public int size() {
		// number of points in the set
		return n;
	}

	// add the point to the set (if it is not already in the set)
	public void insert(Point2D p) {
		if (p == null)
			throw new IllegalArgumentException();
		if (contains(p))
			return;
		n++;
		set.add(p);
	}

	// does the set contain point p?
	public boolean contains(Point2D p) {
		if (p == null)
			throw new IllegalArgumentException();
		return set.contains(p);
	}

	public void draw() {
		// draw all points to standard draw
		Iterator<Point2D> it = set.iterator();
		while (it.hasNext()) {
			Point2D p = it.next();
			p.draw();
		}
	}

	// all points that are inside the rectangle (or on the boundary)
	public Iterable<Point2D> range(RectHV rect) {

		if (rect == null)
			throw new IllegalArgumentException();

		Queue<Point2D> q = new Queue<Point2D>();

		Iterator<Point2D> it = set.iterator();

		while (it.hasNext()) {
			Point2D p = it.next();
			if (rect.contains(p))
				q.enqueue(p);
		}

		return q;
	}

	// a nearest neighbor in the set to point p; null if the set is empty
	public Point2D nearest(Point2D p) {

		if (p == null)
			throw new IllegalArgumentException();

		if (set.size() == 0)
			return null;

		Iterator<Point2D> it = set.iterator();
		double min = Double.POSITIVE_INFINITY;
		Point2D minp = null;

		while (it.hasNext()) {

			Point2D q = it.next();
			double dis = q.distanceTo(p);
			if (dis < min) {
				min = dis;
				minp = q;
			}
		}

		return minp;
	}
}
