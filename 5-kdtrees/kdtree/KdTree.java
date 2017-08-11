import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

public class KdTree {

	private int n;
	private Node root;
	private Point2D nearp;
	private double neardis;

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
		root = insert(root, p, null, 1);
	}

	private Node insert(Node h, Point2D p, Node pre, int layer) {

		if (h == null) {
			n++;
			if (pre == null)
				return new Node(p, new RectHV(0.0, 0.0, 1.0, 1.0));

			RectHV rect = null;
			if ((layer - 1) % 2 == 1) {
				int cmp = dto(p.x(), pre.p.x());
				if (cmp < 0)
					// left
					rect = new RectHV(pre.rect.xmin(), pre.rect.ymin(),
							pre.p.x(), pre.rect.ymax());
				else
					rect = new RectHV(pre.p.x(), pre.rect.ymin(),
							pre.rect.xmax(), pre.rect.ymax());
			} else {

				int cmp = dto(p.y(), pre.p.y());
				if (cmp < 0)
					// down
					rect = new RectHV(pre.rect.xmin(), pre.rect.ymin(),
							pre.rect.xmax(), pre.p.y());
				else
					rect = new RectHV(pre.rect.xmin(), pre.p.y(),
							pre.rect.xmax(), pre.rect.ymax());
			}
			return new Node(p, rect);
		}

		if (h.p.equals(p))
			return h;
		if (layer % 2 == 1) {
			int cmp = dto(p.x(), h.p.x());
			if (cmp < 0) {
				h.left = insert(h.left, p, h, layer + 1);
			} else {
				h.right = insert(h.right, p, h, layer + 1);
			}
		} else {
			int cmp = dto(p.y(), h.p.y());
			if (cmp < 0) {
				h.left = insert(h.left, p, h, layer + 1);
			} else {
				h.right = insert(h.right, p, h, layer + 1);
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

		int cmp = 0;
		if (layer % 2 == 1) {
			cmp = dto(p.x(), h.p.x());
		} else {
			cmp = dto(p.y(), h.p.y());
		}

		if (cmp == 0 && h.p.equals(p))
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
		neardis = Double.POSITIVE_INFINITY;
		nearest(p, root, 1);
		return nearp;
	}

private void nearest(Point2D p, Node x, int layer) {

		if (x == null)
			return;

		double dis = x.p.distanceSquaredTo(p);
		if (dis < neardis) {
			nearp = x.p;
			neardis = dis;
		}

		int cmp;
		if (layer % 2 == 1) {
			cmp = dto(p.x(), x.p.x());
		} else {
			cmp = dto(p.y(), x.p.y());
		}

		if (cmp < 0) {
			nearest(p, x.left, layer + 1);
			if (x.right != null && x.right.rect.distanceSquaredTo(p) < neardis)
				nearest(p, x.right, layer + 1);
		} else {
			nearest(p, x.right, layer + 1);
			if (x.left != null && x.left.rect.distanceSquaredTo(p) < neardis)
				nearest(p, x.left, layer + 1);
		}
	}

}
