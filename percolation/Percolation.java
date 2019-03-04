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

    private WeightedQuickUnionUF uf;

    public Percolation(int n) {                // create n-by-n grid, with all sites blocked
        if (n <= 0) {
            throw new java.lang.IllegalArgumentException("n cannot be less than or equal to 0");
        }
        else {
            grid = new int[n][n];
            uf = new WeightedQuickUnionUF(xyTo1D(n, n));
            size = n;
        }
    }

    public void open(int row, int col) {       // open site (row, col) if it is not open already
        if (row <= 0 || col <= 0 || row > size || col > size) {
            throw new java.lang.IllegalArgumentException("n cannot be less than or equal to 0");
        }

        if (!isOpen(row, col)) {
            grid[row - 1][col - 1] = 1;
        }

        openSites++;

        System.out.println(uf.count());
        System.out.println(uf.find(xyTo1D(row, col)));

        // check if top is open
        if (isOpen(row + 1, col)) {
            uf.union(xyTo1D(row + 1, col), xyTo1D(row, col));
        }
        // check if down is open
        if (isOpen(row - 1, col)) {
            uf.union(xyTo1D(row - 1, col), xyTo1D(row, col));
        }

        // check if left is open
        if (isOpen(row, col - 1)) {
            uf.union(xyTo1D(row + 1, col - 1), xyTo1D(row, col));
        }

        // check if right is open
        if (isOpen(row, col + 1)) {
            uf.union(xyTo1D(row, col + 1), xyTo1D(row, col));
        }

        System.out.println(uf.connected(xyTo1D(1, 1), xyTo1D(1, 2)));
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

        if (row == 1 && isOpen(row, col)) {
            System.out.println(row * col);
            uf.union(xyTo1D(row, col), 1);
            return true;
        }

        return false;
    }

    public int numberOfOpenSites() {           // number of open sites

        return openSites;
    }

    public boolean percolates() {              // does the system percolate?

        return false;
    }

    private int xyTo1D(int r, int c) {
        return (r - 1) * (c - 1);
    }

    public static void main(String[] args) {   // test client (optional)
        Percolation per = new Percolation(Integer.parseInt(args[0]));
        per.open(1, 1);
        per.open(1, 2);

    }
}
