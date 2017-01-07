package UnionFind;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import java.util.Arrays;

public class Percolation {
    private int n;
    private int size;
    private int top;
    private int bottom;
    private int[][] grid;
    private WeightedQuickUnionUF uf;

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

    private int coordToIndex(int row, int col) {
        return row * n + col;
    }

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

    private void tryUnion(int row, int col, int index) {
        if (isOpen(row, col)) {
            uf.union(coordToIndex(row - 1, col - 1), index);
        }
    }

    public void open(int row, int col) {
        checkInput(row, col);
        int tempRow = row - 1;
        int tempCol = col - 1;
        grid[tempRow][tempCol] = 1;
        connectOpenNeighbours(row, col);

    }

    public boolean isOpen(int row, int col) {
        checkInput(row, col);
        int tempRow = row - 1;
        int tempCol = col - 1;
        return grid[tempRow][tempCol] == 1;
    }

    public boolean isFull(int row, int col) {
        checkInput(row, col);
        return uf.connected(top, coordToIndex(row - 1, col - 1));
    }

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
        System.out.println(p.isOpen(1,6));
        System.out.println(p.isFull(1,6));
        System.out.println(p.isFull(2,6));
        System.out.println(p.toString());
        System.out.println(p.uf.count());
    }
}
