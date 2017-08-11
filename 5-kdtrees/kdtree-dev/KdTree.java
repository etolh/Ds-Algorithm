package com.exp.j_pa;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

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

		// double xmin = 0.0, ymin = 0.0, xmax = 1.0, ymax = 1.0;
		// RectHV rect = new RectHV(xmin, ymin, xmax, ymax);
		// root = insert(root, p, rect, 1);
		root = insert(root, p, null, 1);
	}

	// ����ÿ�δ���һ��rect, ���뵱ǰ����ǰ��pre
	private Node insert(Node h, Point2D p, Node pre, int layer) {

		if (h == null) {
			n++; // �����㣬n++
			if (pre == null) {
				// �����
				return new Node(p, new RectHV(0.0, 0.0, 1.0, 1.0));
			}

			RectHV rect = null;
			if ((layer - 1) % 2 == 1) {
				// pre�Ĳ���ǰ�x����
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
			return h; // ���ڼȲ���
		if (layer % 2 == 1) {
			// ��x���л���
			int cmp = dto(p.x(), h.p.x());
			if (cmp < 0) {
				// ��
				h.left = insert(h.left, p, h, layer + 1);
			} else {
				// ��
				h.right = insert(h.right, p, h, layer + 1);
			}
		} else {
			// ��y���л���
			int cmp = dto(p.y(), h.p.y());
			if (cmp < 0) {
				// ��
				h.left = insert(h.left, p, h, layer + 1);
			} else {
				// ��
				h.right = insert(h.right, p, h, layer + 1);
			}
		}
		return h;
	}

	// ����hΪ���ڵ�����������½�㣺��ֵ��(p, rect),layer��ǰ���,���Ų���ı仯��ֵrect��layer���ϱ仯
	private Node insert1(Node h, Point2D p, RectHV rect, int layer) {

		if (h == null)
			return new Node(p, rect);

		double xmin, ymin, xmax, ymax;
		if (layer % 2 == 1) {
			// ��x���л���
			int cmp = dto(p.x(), h.p.x());
			if (cmp < 0) {
				// ��
				xmin = h.rect.xmin();
				ymin = h.rect.ymin();
				xmax = h.p.x();
				ymax = h.rect.ymax();
				rect = new RectHV(xmin, ymin, xmax, ymax);
				h.left = insert1(h.left, p, rect, layer + 1);
			} else {
				// ��
				xmin = h.p.x();
				ymin = h.rect.ymin();
				xmax = h.rect.xmax();
				ymax = h.rect.ymax();
				rect = new RectHV(xmin, ymin, xmax, ymax);
				h.right = insert1(h.right, p, rect, layer + 1);
			}
		} else {
			// ��y���л���
			int cmp = dto(p.y(), h.p.y());
			if (cmp < 0) {
				// ��
				xmin = h.rect.xmin();
				ymin = h.rect.ymin();
				xmax = h.rect.xmax();
				ymax = h.p.y();
				rect = new RectHV(xmin, ymin, xmax, ymax);
				h.left = insert1(h.left, p, rect, layer + 1);

			} else {
				// ��
				xmin = h.rect.xmin();
				ymin = h.p.y();
				xmax = h.rect.xmax();
				ymax = h.rect.ymax();
				rect = new RectHV(xmin, ymin, xmax, ymax);
				h.right = insert1(h.right, p, rect, layer + 1);
			}
		}
		return h;
	}

	// ����hΪ���ڵ������������p:�ǵݹ�
	private Node Uninsert(Node h, Point2D p) {

		int layer = 1;
		double xmin = 0.0, ymin = 0.0, xmax = 1.0, ymax = 1.0;
		if (h == null)
			return new Node(p, new RectHV(xmin, ymin, xmax, ymax));

		// �Ӹ���㿪ʼ����
		while (h != null) {

			int cmp;
			if (layer % 2 == 1) {
				// ��x���л���
				cmp = dto(p.x(), h.p.x());
				if (cmp < 0) {
					// ��
					xmin = h.rect.xmin();
					ymin = h.rect.ymin();
					xmax = h.p.x();
					ymax = h.rect.ymax();
					if (h.left == null) {
						h.left = new Node(p, new RectHV(xmin, ymin, xmax, ymax));
						return root;
					}
					h = h.left;
				} else {
					// ��
					xmin = h.p.x();
					ymin = h.rect.ymin();
					xmax = h.rect.xmax();
					ymax = h.rect.ymax();

					if (h.right == null) {
						h.right = new Node(p,
								new RectHV(xmin, ymin, xmax, ymax));
						return root;
					}
					h = h.right;
				}

			} else {
				// ��y���л���
				cmp = dto(p.y(), h.p.y());

				if (cmp < 0) {
					// ��
					xmin = h.rect.xmin();
					ymin = h.rect.ymin();
					xmax = h.rect.xmax();
					ymax = h.p.y();
					if (h.left == null) {
						h.left = new Node(p, new RectHV(xmin, ymin, xmax, ymax));
						return root;
					}
					h = h.left;
				} else {
					// ��
					xmin = h.rect.xmin();
					ymin = h.p.y();
					xmax = h.rect.xmax();
					ymax = h.rect.ymax();
					if (h.right == null) {
						h.right = new Node(p,
								new RectHV(xmin, ymin, xmax, ymax));
						return root;
					}
					h = h.right;
				}
			}
			layer++;
		}

		return root;
	}

	// does the set contain point p?
	public boolean contains(Point2D p) {
		return contains(root, p, 1);
	}

	// ��������h�Ƿ��н��p
	private boolean contains1(Node h, Point2D p, int layer) {

		if (h == null)
			return false;

		// �Ƚϵ�ǰ���
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
			return contains1(h.left, p, layer + 1);
		else
			return contains1(h.right, p, layer + 1);

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
		draw(root, 1);
	}

	// �������
	private void draw(Node h, int layer) {

		if (h == null)
			return;
		StdDraw.setPenRadius(.02);
		StdDraw.setPenColor(StdDraw.BLACK);
		h.p.draw();

		StdDraw.setPenRadius(.01);
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

		Queue<Point2D> q = new Queue<Point2D>();
		range(rect, q, root);
		return q;
	}

	private void range(RectHV rect, Queue<Point2D> q, Node x) {

		if (x == null)
			return;
		if (rect.contains(x.p))
			q.enqueue(x.p);
		// ֻ�ж���rect�н���Ĳ���
		if (x.left != null && x.left.rect.intersects(rect))
			range(rect, q, x.left);
		if (x.right != null && x.right.rect.intersects(rect))
			range(rect, q, x.right);
	}

	// a nearest neighbor in the set to point p; null if the set is empty
	public Point2D nearest(Point2D p) {

		if (root == null)
			return null;

		// Queue<Point2D> q = new Queue<Point2D>();
		// Point2D minp = root.p;
		// q.enqueue(minp);
		neardis = Double.POSITIVE_INFINITY;
		nearest3(p, root, 1);
		return nearp;
	}

	private void nearest3(Point2D p, Node x, int layer) {

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
			// ��������p��ͬһ�������
			nearest3(p, x.left, layer + 1);
			if (x.right != null && x.right.rect.distanceSquaredTo(p) < neardis)
				nearest3(p, x.right, layer + 1);
		} else {
			nearest3(p, x.right, layer + 1);
			if (x.left != null && x.left.rect.distanceSquaredTo(p) < neardis)
				nearest3(p, x.left, layer + 1);

		}

	}

	private void nearest(Point2D p, Node x) {
		
		if (x == null)
			return;
		double dis = x.p.distanceSquaredTo(p);
		if (dis < neardis) {
			neardis = dis;
			nearp = x.p;
		}
		
		// ���������ڣ�����������Ӧrect��Ŀ���p�ľ���С�ڵ���mindis,��������������
		if (x.left != null && x.left.rect.distanceSquaredTo(p) < neardis)
			nearest2(p, x.left);
		if (x.right != null && x.right.rect.distanceSquaredTo(p) < neardis)
			nearest2(p, x.right);
		
	}
	
	// ����xΪ���ڵ��������p�������, ʹ��ȫ�ֱ�����֤���ܵݹ�Ӱ��
	private void nearest2(Point2D p, Node x) {

		if (x == null)
			return;

		double dis = x.p.distanceSquaredTo(p);
		if (dis < neardis) {
			nearp = x.p;
			neardis = dis;
		}
		
		if (x.left != null && x.right != null) {
			if (x.left.rect.contains(p)) {
				nearest2(p, x.right);
				nearest2(p, x.left);
			} else {
				nearest2(p, x.left);
				nearest2(p, x.right);
			}
		}

		nearest2(p, x.left);
		nearest2(p, x.right);

		// �ȱ����Ҳ��ٱ�����ࣺ���ڽ���޷��������������x.leftΪnull����p�ֲ���x.right��һ��ʱ����޷�����
		// if (x.right != null && x.right.rect.contains(p)){
		//
		// nearest(p, x.right);
		// nearest(p, x.left);
		//
		// }else if (x.left != null && x.left.rect.contains(p)){
		// // ���������ڣ���query����࣬�ȱ�������ٱ����ҲࣨҲ���ܴ��ڣ�
		// nearest(p, x.left);
		// nearest(p, x.right);
		// }
	}


	// 1 ����xΪ���ڵ��������p������㣬q�а�������㣬�����������q����
	private void nearest1(Point2D p, Queue<Point2D> q, Node x) {

		if (x == null)
			return;
		double dis = x.p.distanceSquaredTo(p);
		double mindis = q.peek().distanceSquaredTo(p);
		if (dis < mindis) {
			q.dequeue();
			q.enqueue(x.p);
		}
		// ���������ڣ�����������Ӧrect��Ŀ���p�ľ���С�ڵ���mindis,��������������
		if (x.left != null && x.left.rect.distanceSquaredTo(p) < mindis)
			nearest1(p, q, x.left);
		if (x.right != null && x.right.rect.distanceSquaredTo(p) < mindis)
			nearest1(p, q, x.right);

	}
	
	
	public static void main(String[] args) {
		// unit testing of the methods (optional)

		try {
			System.setIn(new FileInputStream(new File("kdtree/circle10.txt")));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		KdTree kdtree = new KdTree();
		while (!StdIn.isEmpty()) {
			double x = StdIn.readDouble();
			double y = StdIn.readDouble();
			StdOut.printf("%8.6f %8.6f\n", x, y);
			Point2D p = new Point2D(x, y);
			kdtree.insert(p);
		}

		Point2D query = new Point2D(0.06, 0.7);
		StdDraw.setPenRadius(.05);
		query.draw();
		Point2D pq = kdtree.nearest(query);
		pq.draw();

		Point2D qy = new Point2D(0.024472, 0.654508);
		StdOut.println(kdtree.contains(qy));

		kdtree.draw();
		StdDraw.show();
		StdDraw.pause(50);

	}
}
