/* *****************************************************************************
 *  Name: Namanh Tran
 *  Date: 4/20/2019
 *  Description: http://coursera.cs.princeton.edu/algs4/assignments/queues.html
 **************************************************************************** */

import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {

    private int size;
    private Node front;
    private Node back;

    // Node class
    private class Node {
        Item item;
        Node next;
        Node prev;
    }

    // Construct an empty deque
    public Deque() {
        size = 0;
        front = null;
        back = null;
    }

    // Is the deque empty?
    public boolean isEmpty() {
        if (size == 0)
            return true;

        return false;
    }

    // Return the number of items on the deque
    public int size() {
        return size;
    }

    // Add the item to the end
    public void addFirst(Item item) {
        if (item == null)
            throw new java.lang.IllegalArgumentException("Cannot add a null item");

        Node newNode = new Node();
        newNode.item = item;

        if (front == null) {
            newNode.next = null;
            newNode.prev = null;
            front = newNode;
            back = newNode;
        }

        else {
            newNode.next = front;
            newNode.prev = null;
            front.prev = newNode;
            front = newNode;
        }

        size++;
    }

    // Add the item to the end
    public void addLast(Item item) {
        if (item == null)
            throw new java.lang.IllegalArgumentException("Cannot add a null item");

        Node newNode = new Node();
        newNode.item = item;

        if (front == null) {
            newNode.next = null;
            newNode.prev = null;
            front = newNode;
            back = newNode;
        }

        else {
            newNode.next = null;
            newNode.prev = back;
            back.next = newNode;
            back = newNode;
        }

        size++;
    }

    // Remove and return the item from the front
    public Item removeFirst() {

        // Check if the list is empty
        if (isEmpty())
            throw new java.util.NoSuchElementException("The deque is empty");

        // Remove front
        Item item = front.item;
        front = front.next;
        front.prev = null;
        size--;

        return item;
    }

    // Remove and return the item from the end
    public Item removeLast() {

        // Check if the list is empty
        if (isEmpty())
            throw new java.util.NoSuchElementException("The deque is empty");

        // Remove last
        Item item = back.item;
        back = back.prev;
        back.next = null;
        size--;

        return item;
    }

    // Return an iterator over items in order from front to end
    public Iterator<Item> iterator() {
        return new IterableDeque();
    }

    private class IterableDeque implements Iterator<Item> {

        private Node itFront = front;

        public boolean hasNext() {
            return itFront != null;
        }

        public void remove() {
            // Not supported
            throw new java.lang.UnsupportedOperationException("remove() is an unsupported method");
        }

        public Item next() {

            if (!hasNext()) {
                throw new java.util.NoSuchElementException(
                        "The iterator has no more items to return");
            }

            Item item = itFront.item;
            itFront = itFront.next;

            return item;
        }
    }

    public void print() {
        Node temp = front;
        for (int i = 0; i < size; i++) {
            System.out.print(temp.item + "->");
            temp = temp.next;
        }
        System.out.print("NULL");
    }

    // Unit testing (optional)
    public static void main(String[] args) {
        Deque<Integer> myDeque = new Deque<Integer>();
        for (int i = 0; i < 10; i++) {
            myDeque.addLast(i);
        }
        myDeque.print();
    }
}
