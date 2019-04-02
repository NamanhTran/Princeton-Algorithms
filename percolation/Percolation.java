/* *****************************************************************************
 *  Name: Namanh Tran
 *  Date:
 *  Description: Assignment link: http://coursera.cs.princeton.edu/algs4/assignments/percolation.html
 *               To install all the prerequisites on linux: https://lift.cs.princeton.edu/java/linux/
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int[][] grid;
    private int size;
    private int openSites = 0;
    private int head;
    private int tail;

    private WeightedQuickUnionUF uf;

    public Percolation(int n) {                // create n-by-n grid, with all sites blocked
        if (n <= 0) {
            throw new java.lang.IllegalArgumentException("n cannot be less than or equal to 0");
        }
        else {
            grid = new int[n][n];
            uf = new WeightedQuickUnionUF(n * n + 2);
            size = n;
            head = n * n;
            tail = n * n + 1;
            //System.out.printf("n: %d head: %d tail: %d\n", n, head, tail);
        }
    }

    public void open(int row, int col) {       // open site (row, col) if it is not open already
        if (row <= 0 || col <= 0 || row > size || col > size) {
            throw new java.lang.IllegalArgumentException("n cannot be less than or equal to 0");
        }

        if (!isOpen(row, col)) {
            grid[row - 1][col - 1] = 1;
            openSites++;
        }
        // If it is already open
        else
            return;

        if (row == 1) {
            uf.union(head, xyTo1D(row, col));
        }

        if (row == size) {
            uf.union(tail, xyTo1D(row, col));
        }

        // check if top is open
        if (row != 1 && isOpen(row - 1, col)) {
            uf.union(xyTo1D(row - 1, col), xyTo1D(row, col));
        }

        // check if bottom is open
        if (row + 1 <= size && isOpen(row + 1, col)) {
            uf.union(xyTo1D(row + 1, col), xyTo1D(row, col));
        }

        // check if left is open
        if (col != 1 && isOpen(row, col - 1)) {
            uf.union(xyTo1D(row, col - 1), xyTo1D(row, col));
        }

        // check if right is open
        if (col + 1 <= size && isOpen(row, col + 1)) {
            uf.union(xyTo1D(row, col + 1), xyTo1D(row, col));
        }
    }

    public boolean isOpen(int row, int col) {  // is site (row, col) open?
        if (row <= 0 || col <= 0 || row > size || col > size) {
            throw new java.lang.IllegalArgumentException("n cannot be less than or equal to 0");
        }

        if (grid[row - 1][col - 1] == 0) {
            return false;
        }

        return true;
    }

    public boolean isFull(int row, int col) {  // is site (row, col) full?
        if (row <= 0 || col <= 0 || row > size || col > size) {
            throw new java.lang.IllegalArgumentException("n cannot be less than or equal to 0");
        }

        if (isOpen(row, col)) {
            return uf.connected(xyTo1D(row, col), head);
        }
        return false;
    }

    public int numberOfOpenSites() {           // number of open sites
        return openSites;
    }

    public boolean percolates() {              // does the system percolate?
        if (uf.connected(head, tail))
            return true;
        return false;
    }

    private int xyTo1D(int r, int c) {
        int val = size * (r - 1) + (c - 1);
        //System.out.printf("xyTo1D: r: %d c: %d val: %d\n", r, c, val);
        return size * (r - 1) + (c - 1);
    }

    public static void main(String[] args) {   // test client (optional)
        Percolation per = new Percolation(Integer.parseInt(args[0]));
        per.open(1, 1);
        per.open(1, 2);
    }
}
