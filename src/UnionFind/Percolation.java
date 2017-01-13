package UnionFind;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import java.util.Arrays;

/**
 * We model a percolation system using an n-by-n grid of sites.
 * Each site is either open or blocked.
 * A full site is an open site that can be connected to an open site
 * in the top row via a chain of neighboring (left, right, up, down) open sites.
 * We say the system percolates if there is a full site in the bottom row.
 * In other words, a system percolates if we fill all open sites connected to the top row
 * and that process fills some open site on the bottom row.
 */
public class Percolation {
    /**
     * The size of the grid's side
     */
    private int n;
    /**
     * The square of the grid.
     */
    private int size;
    /**
     * Represents the top row of the grid
     */
    private int top;
    /**
     * Represents the bottom row of the grid
     */
    private int bottom;
    /**
     * The grid as a two dimensional array
     */
    private int[][] grid;
    /**
     * The Weighted Quick Union Find algorithm.
     */
    private WeightedQuickUnionUF uf;


    /**
     *
     * @param n the size of the grid (n by n) where n > 0
     */
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        this.n = n;
        this.size = n * n;
        this.top = size;
        this.bottom = size + 1;
        this.grid = new int[n][n];
        this.uf = new WeightedQuickUnionUF(size + 2);
    }

    /**
     * A method to convert the two dimensional array coordinates to a single index integer.
     * @param row The row coordinate of the grid.
     * @param col The column coordinate of the grid
     * @return The index number that represents the coordinates.
     */
    private int coordToIndex(int row, int col) {
        return row * n + col;
    }

    /**
     * Method to validate input arguments.
     * @param row The row coordinate of the block.
     * @param col The column coordinate of the block.
     */
    private void checkInput(int row, int col) {
        if (row > n || col > n) {
            throw new IndexOutOfBoundsException("n");
        }
        if (row < 1 || col < 1) {
            System.out.println(Integer.toString(row));
            System.out.println(Integer.toString(col));

            throw new IndexOutOfBoundsException("1");
        }
    }

    /**
     * Checking and connecting the open neighbouring blocks to the currently opened block.
     * Also connects every 0 and n - 1 row blocks to the top and bottom properties.
     * @param row The row coordinate of the currently opened block.
     * @param col The column coordinate of the currently opened block.
     */
    private void connectOpenNeighbours(int row, int col) {
        int tempRow = row - 1;
        int tempCol = col - 1;

        int index = coordToIndex(tempRow, tempCol);

        // check right
        if (tempCol > 0) tryUnion(row, col - 1, index);
        // check left
        if (tempCol < n - 1) tryUnion(row, col + 1, index);

        // check up
        if (tempRow > 0) {
            tryUnion(row - 1, col, index);
        } else {
            uf.union(coordToIndex(tempRow, tempCol), top);
        }
        // check down
        if (tempRow < n - 1) {
            tryUnion(row + 1, col, index);
        } else {
            uf.union(coordToIndex(tempRow, tempCol), bottom);
        }


    }

    /**
     * The if the block is open connect it to the base block.
     * @param row The row coordinate of neighbouring block.
     * @param col The column coordinate of the neighbouring block.
     * @param index The index of the base block.
     */
    private void tryUnion(int row, int col, int index) {
        if (isOpen(row, col)) {
            uf.union(coordToIndex(row - 1, col - 1), index);
        }
    }

    /**
     * Open the block.
     * @param row The row coordinate of the block.
     * @param col The column coordinate of the block.
     */
    public void open(int row, int col) {
        checkInput(row, col);
        int tempRow = row - 1;
        int tempCol = col - 1;
        grid[tempRow][tempCol] = 1;
        connectOpenNeighbours(row, col);

    }

    /**
     * Check if the block is open.
     * @param row The row coordinate of the block.
     * @param col The column coordinate of the block.
     */
    public boolean isOpen(int row, int col) {
        checkInput(row, col);
        int tempRow = row - 1;
        int tempCol = col - 1;
        return grid[tempRow][tempCol] == 1;
    }

    /**
     * Check if the block is connected to the top row.
     * @param row The row coordinate of the block.
     * @param col The column coordinate of the block.
     */
    public boolean isFull(int row, int col) {
        checkInput(row, col);
        return uf.connected(top, coordToIndex(row - 1, col - 1));
    }

    /**
     * Check if the system percolates, is there a route between top and bottom row.
     * @return true if the system percolates else false.
     */
    public boolean percolates() {
        return uf.connected(top, bottom);
    }


    public String toString() {
        Arrays.stream(grid).map(a -> Arrays.toString(a)).forEach(System.out::println);
        return "Percolation{" +
                "n=" + n +
                ", grid=" + Arrays.deepToString(grid) +
                '}';
    }

    public static void main(String[] args) {
        Percolation p = new Percolation(6);
        p.open(1, 6);
        p.open(2, 6);
        System.out.println(p.isOpen(1, 6));
        System.out.println(p.isFull(1, 6));
        System.out.println(p.isFull(2, 6));
        System.out.println(p.toString());
        System.out.println(p.uf.count());
    }
}
