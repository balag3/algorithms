package Deque;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Subset {
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        String[] arr = StdIn.readAllStrings();
        RandomizedQueue<String> q = new RandomizedQueue<String>();
        for (int i = 0; i < arr.length; i++) {
            q.enqueue(arr[i]);
        }
        for (int i = 0; i < n; i++) {
            StdOut.println(q.dequeue());
        }
    }
}