package Deque;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Node first, last;
    private int size = 0;

    private class Node {
        private Item item;
        private Node next;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty()  {
        return (first == null && last == null);
    }

    public void addFirst(Item item) {
        checkItem(item);
        Node oldFirst = first;
        first = new Node();
        first.item = item;
        first.next = oldFirst;
        if (last == null) last = first;
        size++;

    }

    public void addLast(Item item) {
        checkItem(item);
        Node oldLast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        if (first == null) first = last;
        else oldLast.next = last;
        size++;
    }

    public Item removeFirst() {
        if (first == null) {
            throw new NoSuchElementException("Cannot remove item form an empty deque!");
        }
        Item item = first.item;
        if (first.next == null) {
            last = null;
        }
        first = first.next;
        size--;
        return item;
    }

    public Item removeLast() {
        if (first == null) {
            throw new NoSuchElementException("Cannot remove item form an empty deque!");
        }
        Node x = first;

        if (first.next == null) {
            Item item = first.item;
            first = null;
            last = null;
            size--;
            return item;
        }

        while (x.next.next != null) {
            x = x.next;
        }
        Item item = x.next.item;
        last = x;
        last.next = null;
        size--;
        return item;
    }


    private void checkItem(Item item) {
        if (item == null) {
            throw new NullPointerException("Cannot add null items to the Deque!");
        }
    }
    private class DequeIterator implements Iterator<Item> {
        private Node current = first;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            if (current == null) throw new NoSuchElementException("Item doesn't exist!");
            Item item = current.item;
            current = current.next;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException("The remove() method is not allowed here!");
        }
    }

    @Override
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }


    public static void main(String[] args) {

    }
}

