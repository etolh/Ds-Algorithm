package com.exp.j_pa;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdIn;

/**
 * 使用红黑树暴力求解二维点范围查找和最近点
 * 
 * @author 胡亮
 *
 */
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
		if(p == null)	throw new IllegalArgumentException();
		if(contains(p))	return;
		n++;
		set.add(p);
	}

	// does the set contain point p?
	public boolean contains(Point2D p) {
		if(p == null)	throw new IllegalArgumentException();
		return set.contains(p);
	}

	public void draw() {
		// draw all points to standard draw
		Iterator<Point2D> it = set.iterator();
		while(it.hasNext()){
			Point2D p = it.next();
			p.draw();
		}
	}

	// all points that are inside the rectangle (or on the boundary)
	public Iterable<Point2D> range(RectHV rect) {
		
		if(rect == null)	throw new IllegalArgumentException();
		
		Queue<Point2D> q = new Queue<Point2D>();
		
		Iterator<Point2D> it = set.iterator();
		
		while(it.hasNext()){
			Point2D p = it.next();
			if(rect.contains(p))
				q.enqueue(p);
		}
		
		return q;
	}

	// a nearest neighbor in the set to point p; null if the set is empty
	public Point2D nearest(Point2D p) {
		
		if(p == null)	throw new IllegalArgumentException();
		
		if(set.size() == 0)
			return null;
		
		Iterator<Point2D> it = set.iterator();
		double min = Double.POSITIVE_INFINITY;
		Point2D minp = null;
		
		while(it.hasNext()){
			
			Point2D q = it.next();
			double dis = q.distanceTo(p);
			if(dis < min){
				min = dis;
				minp = q;
			}
		}
		
		return minp;
	}

	public static void main(String[] args) {
		// unit testing of the methods (optional)
		try {
			System.setIn(new FileInputStream(new File("kdtree/horizontal8.txt")));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		PointSET ps = new PointSET();
		
		while(!StdIn.isEmpty()){
			
			double x = StdIn.readDouble();
			double y = StdIn.readDouble();
			Point2D p = new Point2D(x, y);
			ps.insert(p);
		}
		
		
		StdDraw.setXscale(0, 1);
		StdDraw.setYscale(0, 1);
		StdDraw.setPenRadius(.01);
		StdDraw.clear();
		
		ps.draw();
		RectHV rect = new RectHV(0.1, 0.1, 0.8, 0.8);
		rect.draw();
		
		for(Point2D p: ps.range(rect)){
			StdDraw.setPenColor(StdDraw.RED);
			p.draw();
		}
		
		Point2D qp = new Point2D(0.1, 0.1);
		Point2D minp = ps.nearest(qp);
		StdDraw.setPenColor(StdDraw.BLUE);
		qp.draw();
		minp.draw();
		StdDraw.line(qp.x(), qp.y(), minp.x(), minp.y());
	}
}
