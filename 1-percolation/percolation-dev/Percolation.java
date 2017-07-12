package com.exp.a_UFexercise;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Percolation {

	private byte[] fopen;
	private int opencount;
	private WeightedQuickUnionUF uf;
	private int n;

	// create n-by-n grid, with all sites blocked
	public Percolation(int n) {

		if (n <= 0)
			throw new IllegalArgumentException();
		this.n = n; // ��n��ʼ��
		int count = n * n;
		this.opencount = 0;

		// ��ʼ�����ϱ�־��ά���� 0-block��1-open��2-full
		fopen = new byte[count];
		// ��ʼ����0-count-1
		uf = new WeightedQuickUnionUF(count);
		// Ĭ�ϳ�ʼ��0
		// for (int i = 0; i < count; i++)
		// fopen[i] = 0;
	}

	// validate that p is a valid index
	private void validate(int r, int c) {
		if (r < 1 || r > n || c < 1 || c > n)
			throw new IllegalArgumentException();
	}

	// open site (row, col) if it is not open already
	public void open(int row, int col) {
		validate(row, col);
		if (isOpen(row, col))
			return;

		// �ж������������Ƿ���ڿ�������ͬһ�����ϵ������fopen������Դ���fopen[root]==2,����Ҳ��Ϊ2
		int nowc = toNum(row, col);
		// int nowroot = uf.find(nowc); //
		// ������Ӽ���root�����ϱ仯��ÿ����һ�α仯һ�Σ���Ӧ�÷�������̶����䡣

		opencount++;

		if (row == 1)
			fopen[nowc] = 2;
		else
			fopen[nowc] = 1;

		// ��
		if (row > 1) {
			int upc = nowc - n;
			if (isOpen(row - 1, col)) {
				int uproot = uf.find(upc); // �Ϸ����Ӽ���root
				int nowroot = uf.find(nowc); // ������Ӽ���root�����ϱ仯��ÿ����һ�α仯һ�Ρ�
				uf.union(nowc, upc); // ����
				int root = uf.find(nowc); // �¼���root
				// ��������ֻҪ��һ�����ϵ�fopen[root]=2�����¼��ϵ�fopen[root]=2
				if (fopen[uproot] == 2 | fopen[nowroot] == 2)
					fopen[root] = 2;

			}
		}
		// ��
		if (row < n) {
			int downc = nowc + n;
			if (isOpen(row + 1, col)) {
				int downroot = uf.find(downc);
				int nowroot = uf.find(nowc); // ������Ӽ���root�����ϱ仯��ÿ����һ�α仯һ�Ρ�
				uf.union(nowc, downc);
				int root = uf.find(nowc);
				if (fopen[downroot] == 2 | fopen[nowroot] == 2)
					fopen[root] = 2;
			}
		}
		// ��
		if (col > 1) {
			int leftc = nowc - 1;
			if (isOpen(row, col - 1)) {
				int lroot = uf.find(leftc);
				int nowroot = uf.find(nowc); // ������Ӽ���root�����ϱ仯��ÿ����һ�α仯һ�Ρ�
				uf.union(nowc, leftc);
				int root = uf.find(nowc);
				if (fopen[lroot] == 2 | fopen[nowroot] == 2)
					fopen[root] = 2;
			}
		}

		// ��
		if (col < n) {
			int rightc = nowc + 1;
			if (isOpen(row, col + 1)) {
				int rroot = uf.find(rightc);
				int nowroot = uf.find(nowc); // ������Ӽ���root�����ϱ仯��ÿ����һ�α仯һ�Ρ�
				uf.union(nowc, rightc);
				int root = uf.find(nowc);
				if (fopen[rroot] == 2 | fopen[nowroot] == 2)
					fopen[root] = 2;
			}
		}
	}

	// is site (row, col) open?
	public boolean isOpen(int row, int col) {
		validate(row, col);
		return fopen[toNum(row, col)] != 0;
	}

	// is site (row, col) full?
	public boolean isFull(int row, int col) {
		validate(row, col);
		if (!isOpen(row, col))
			return false;
		int root = uf.find(toNum(row, col));
		return fopen[root] == 2;
	}

	// number of open sites
	public int numberOfOpenSites() {
		return opencount;
	}

	// does the system percolate?
	public boolean percolates() {
		// �������еײ��ĸ��ӣ��鿴�Ƿ���up����
		// �ж�n==1
		if (n == 1)
			return isOpen(1, 1);
		for (int i = 1; i <= n; i++)
			if (isFull(n, i))
				return true;
		return false;
	}

	// (r,c)-->int
	private int toNum(int r, int c) {
		return (r - 1) * n + c - 1;
	}

	// test client (optional)
	public static void main(String[] args) throws FileNotFoundException {

		System.setIn(new FileInputStream(new File("percolation/input2.txt")));

		int n = StdIn.readInt(); // n=1Ҳ�ڴ�����

		Percolation p = new Percolation(n);

		StdOut.println(p.percolates());

		boolean flag = false;

		while (!StdIn.isEmpty()) {

			// ��ȡ
			int rrow = StdIn.readInt();
			int rcol = StdIn.readInt();

			if (!p.isOpen(rrow, rcol))
				p.open(rrow, rcol);

			StdOut.println(p.percolates());

			if (p.isFull(rrow, rcol)) {
				// ������ϣ��ж��Ƿ���͸
				if (p.percolates()) {
					flag = true;
					break;
				}
			}
		}

		if (flag) {
			int pn = p.numberOfOpenSites();
			StdOut.println("opencount = " + pn);
		} else {
			StdOut.println("No percolation");
		}
	}
}
