import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

public class KdTree {

	private int n;
	private Node root;

	private static class Node {
		private Point2D p;
		private RectHV rect;
		private Node left, right;

		public Node(Point2D p, RectHV rect) {
			this.p = p;
			this.rect = rect;
		}
	}

	public KdTree() {
		n = 0;
	}

	// is the set empty?
	public boolean isEmpty() {
		return n == 0;
	}

	// number of points in the set
	public int size() {
		return n;
	}

	// add the point to the set (if it is not already in the set)
	public void insert(Point2D p) {
		if (p == null)
			throw new IllegalArgumentException();
		if(contains(p))	return;
		double xmin = 0.0, ymin = 0.0, xmax = 1.0, ymax = 1.0;
		RectHV rect = new RectHV(xmin, ymin, xmax, ymax);
		root = insert(root, p, rect, 1);
		n++;
	}

	private Node insert(Node h, Point2D p, RectHV rect, int layer) {

		if (h == null)
			return new Node(p, rect);

		double xmin, ymin, xmax, ymax;
		if (layer % 2 == 1) {
			int cmp = dto(p.x(), h.p.x());
			if (cmp < 0) {
				xmin = h.rect.xmin();
				ymin = h.rect.ymin();
				xmax = h.p.x();
				ymax = h.rect.ymax();
				rect = new RectHV(xmin, ymin, xmax, ymax);
				h.left = insert(h.left, p, rect, layer + 1);
			} else {
				xmin = h.p.x();
				ymin = h.rect.ymin();
				xmax = h.rect.xmax();
				ymax = h.rect.ymax();
				rect = new RectHV(xmin, ymin, xmax, ymax);
				h.right = insert(h.right, p, rect, layer + 1);
			}
		} else {
			int cmp = dto(p.y(), h.p.y());
			if (cmp < 0) {
				xmin = h.rect.xmin();
				ymin = h.rect.ymin();
				xmax = h.rect.xmax();
				ymax = h.p.y();
				rect = new RectHV(xmin, ymin, xmax, ymax);
				h.left = insert(h.left, p, rect, layer + 1);

			} else {
				xmin = h.rect.xmin();
				ymin = h.p.y();
				xmax = h.rect.xmax();
				ymax = h.rect.ymax();
				rect = new RectHV(xmin, ymin, xmax, ymax);
				h.right = insert(h.right, p, rect, layer + 1);
			}
		}
		return h;
	}

	// does the set contain point p?
	public boolean contains(Point2D p) {
		if (p == null)
			throw new IllegalArgumentException();
		return contains(root, p, 1);
	}

	private boolean contains(Node h, Point2D p, int layer) {

		if (h == null)
			return false;
		int flag = Integer.MAX_VALUE;
		int cmp = 0;
		if (layer % 2 == 1) {
			cmp = dto(p.x(), h.p.x());
			if (cmp == 0)
				flag = dto(p.y(), h.p.y());
		} else {
			cmp = dto(p.y(), h.p.y());
			if (cmp == 0)
				flag = dto(p.x(), h.p.x());
		}

		if (cmp == 0 && flag == 0)
			return true;

		if (cmp < 0)
			return contains(h.left, p, layer + 1);
		else
			return contains(h.right, p, layer + 1);

	}

	private int dto(double d1, double d2) {
		if (d1 < d2)
			return -1;
		else if (d1 > d2)
			return 1;
		else
			return 0;
	}

	public void draw() {
		StdDraw.setPenRadius(.01);
		draw(root, 1);
	}

	private void draw(Node h, int layer) {

		if (h == null)
			return;
		StdDraw.setPenColor(StdDraw.BLACK);
		h.p.draw();

		if (layer % 2 == 1) {
			StdDraw.setPenColor(StdDraw.RED);
			StdDraw.line(h.p.x(), h.rect.ymin(), h.p.x(), h.rect.ymax());
		} else {
			StdDraw.setPenColor(StdDraw.BLUE);
			StdDraw.line(h.rect.xmin(), h.p.y(), h.rect.xmax(), h.p.y());
		}
		if (h.left != null)
			draw(h.left, layer + 1);
		if (h.right != null)
			draw(h.right, layer + 1);
	}

	// all points that are inside the rectangle (or on the boundary)
	public Iterable<Point2D> range(RectHV rect) {
		if (rect == null)
			throw new IllegalArgumentException();
		Queue<Point2D> q = new Queue<Point2D>();
		range(rect, q, root);
		return q;
	}

	private void range(RectHV rect, Queue<Point2D> q, Node x) {

		if (x == null)
			return;
		if (rect.contains(x.p))
			q.enqueue(x.p);
		if (x.left != null && x.left.rect.intersects(rect))
			range(rect, q, x.left);
		if (x.right != null && x.right.rect.intersects(rect))
			range(rect, q, x.right);
	}

	// a nearest neighbor in the set to point p; null if the set is empty
	public Point2D nearest(Point2D p) {
		if (p == null)
			throw new IllegalArgumentException();

		if (root == null)
			return null;
		Queue<Point2D> q = new Queue<Point2D>();
		Point2D minp = root.p;
		q.enqueue(minp);
		nearest(p, q, root);
		return q.dequeue();
	}

	private void nearest(Point2D p, Queue<Point2D> q, Node x) {

		if (x == null)
			return;
		double dis = x.p.distanceSquaredTo(p);
		double mindis = q.peek().distanceSquaredTo(p);
		if (dis < mindis) {
			q.dequeue();
			q.enqueue(x.p);
		}
		if (x.left != null && x.left.rect.distanceSquaredTo(p) < mindis)
			nearest(p, q, x.left);
		if (x.right != null && x.right.rect.distanceSquaredTo(p) < mindis)
			nearest(p, q, x.right);
	}
}
