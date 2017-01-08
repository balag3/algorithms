package PatternRecognition;

import java.util.Arrays;

public class BruteCollinearPoints {
    private LineSegment[] segments;
    private int numberOfSegments;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        checkInput(points);
        int n = points.length;
        this.segments = new LineSegment[0];
        this.numberOfSegments = 0;
        for (int p = 0; p < n; p++) {
            for (int q = p + 1; q < n; q++) {
                for (int r = q + 1; r < n; r++) {
                    if ((points[p].slopeTo(points[q]) != points[q].slopeTo(points[r]))) continue;
                        for (int s = r + 1; s < n; s++) {
                        if ((points[p].slopeTo(points[q]) == points[q].slopeTo(points[r])) && (points[q].slopeTo(points[r]) == points[r].slopeTo(points[s]))) {
                            Point[] temp = new Point[4];
                            temp[0] = points[p];
                            temp[1] = points[q];
                            temp[2] = points[r];
                            temp[3] = points[s];
                            Arrays.sort(temp);
                            if (numberOfSegments == segments.length) resize(numberOfSegments + 1);
                            segments[numberOfSegments++] = new LineSegment(temp[0], temp[3]);


                        }
                    }
                }
            }
        }
    }

    // the number of line segments
    public int numberOfSegments() {
        return this.numberOfSegments;
    }

    // the line segments
    public LineSegment[] segments() {
        LineSegment[] copy = segments;
        return copy;
    }

    private void checkInput(Point[] points) {
        if (points == null) throw new NullPointerException("Point array can't be null!");

        for (int i = 0; i < points.length - 1; i++) {
            if (points[i] == null) throw new NullPointerException("Cannot contain null items!");
            for (int j = i + 1; j < points.length; j++) {
                if (points[i].compareTo(points[j]) == 0)
                    throw new IllegalArgumentException("Cannot contain duplicates!");

            }
        }
    }

    private void resize(int capacity) {
        LineSegment[] copy = new LineSegment[capacity];
        int acc = 0;
        for (int i = 0; i < segments.length; i++) {
            copy[acc++] = segments[i];
        }
        segments = copy;
    }
}