package Deque;

import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] queue;
    private int head = 0;
    private int tail = 0;

    public RandomizedQueue() {
        queue = (Item[]) new Object[1];
    }

    public boolean isEmpty() {
        return head == tail;
    }

    public int size() {
        return tail - head;
    }

    public void enqueue(Item item) {
        checkItem(item);
        if (tail == queue.length) resize(2 * queue.length);
        queue[tail++] = item;
    }

    public Item dequeue() {
        checkQueue();
        int pick = StdRandom.uniform(head,tail);
        Item item = queue[pick];
        queue[pick] = null;
        removeInconsistency();
        if (tail > 0 && tail - head == queue.length / 4) resize(queue.length / 2);
        return item;
    }

    public Item sample() {
        checkQueue();
        return queue[StdRandom.uniform(head, tail)];
    }

    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        int acc = 0;
        for (int i = head; i < tail; i++) {
            copy[acc++] = queue[i];
        }
        queue = copy;
        tail = tail - head;
        head = 0;

    }

    private void removeInconsistency() {
        Item[] copy = (Item[]) new Object[queue.length];
        int acc = 0;
        for (int i = head; i < tail; i++) {
            if (queue[i] == null) {
                continue;
            }
            copy[acc++] = queue[i];
        }
        queue = copy;
        tail = tail - head - 1;
        head = 0;
    }

    private void checkItem(Item item) {
        if (item == null) throw new NullPointerException("Cannot add null item!");
    }

    private void checkQueue() {
        if (isEmpty()) throw new NoSuchElementException("Cannot retrieve element from an empty queue!");
    }

    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        private int start = head;
        private int stop = tail;

        @Override
        public boolean hasNext() {
            return start != stop;
        }

        @Override
        public Item next() {
            if (queue[start] == null) throw new NoSuchElementException("No more items to return!");
            Item item = queue[start++];
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException("The remove() method is not supported here!");
        }
    }
}