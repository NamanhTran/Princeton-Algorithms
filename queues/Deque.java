/* *****************************************************************************
 *  Name: Namanh Tran
 *  Date: 4/20/2019
 *  Description: http://coursera.cs.princeton.edu/algs4/assignments/queues.html
 **************************************************************************** */

import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {

    // Private class variables
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

        // Check if the user passed a parameter in the function call
        if (item == null)
            throw new java.lang.IllegalArgumentException("Cannot add a null item");

        // Create a new Node for the list
        Node newNode = new Node();
        newNode.item = item;

        // Case if it is the first node in the list
        if (front == null) {
            newNode.next = null;
            newNode.prev = null;
            front = newNode;
            back = newNode;
        }

        // Case if the list contains more than one node
        else {
            newNode.next = front;
            newNode.prev = null;
            front.prev = newNode;
            front = newNode;
        }

        // Increment size of list counter
        size++;
    }

    // Add the item to the end
    public void addLast(Item item) {

        // Check if the user passed a parameter in the function call
        if (item == null)
            throw new java.lang.IllegalArgumentException("Cannot add a null item");

        // Create a new Node for the list
        Node newNode = new Node();
        newNode.item = item;

        // Add to back of list
        // Case if it is the first node in the list
        if (front == null) {
            newNode.next = null;
            newNode.prev = null;
            front = newNode;
            back = newNode;
        }

        // Case if the list contains more than one node
        else {
            newNode.next = null;
            newNode.prev = back;
            back.next = newNode;
            back = newNode;
        }

        // Increment the size of the list
        size++;
    }

    // Remove and return the item from the front
    public Item removeFirst() {

        // Check if the list is empty
        if (isEmpty())
            throw new java.util.NoSuchElementException("The deque is empty");

        Item item = front.item;

        // Remove front
        // Case if there is only one node in the list
        if (front.next == null) {
            back = null;
            front = null;
        }

        // Case if there is more than one node in the list
        else {
            front = front.next;
            front.prev = null;
        }

        // Increment the size of the list
        size--;

        return item;
    }

    // Remove and return the item from the end
    public Item removeLast() {

        // Check if the list is empty
        if (isEmpty())
            throw new java.util.NoSuchElementException("The deque is empty");

        Item item = back.item;

        // Remove from back of list
        // Case for one node in the list
        if (back.prev == null) {
            back = null;
            front = null;
        }

        // Case for more than one node in the list
        else {
            back = back.prev;
            back.next = null;
        }

        // Increment the size of the list
        size--;

        return item;
    }

    // Return an iterator over items in order from front to end
    public Iterator<Item> iterator() {
        return new IterableDeque();
    }

    private class IterableDeque implements Iterator<Item> {

        // Create a copy of the front node
        private Node itFront = front;

        // Checks whether or not the iterator has reached the end of the list
        public boolean hasNext() {
            return itFront != null;
        }

        // Not supported
        public void remove() {
            // Not supported
            throw new java.lang.UnsupportedOperationException("remove() is an unsupported method");
        }

        // Move the iterator to the next position
        public Item next() {

            // If there are not more items to return
            if (!hasNext()) {
                throw new java.util.NoSuchElementException(
                        "The iterator has no more items to return");
            }

            // Return the item and move the iterator forward
            Item item = itFront.item;
            itFront = itFront.next;

            return item;
        }
    }

    /*
    public void print() {
        Node temp = front;
        for (int i = 0; i < size; i++) {
            System.out.print(temp.item + "->");
            temp = temp.next;
        }
        System.out.print("NULL\n");
    }
    */
    // Unit testing (optional)
    public static void main(String[] args) {
        /*
        Deque<Integer> myDeque = new Deque<Integer>();
        for (int i = 0; i < 10; i++) {
            myDeque.addFirst(i);
            myDeque.print();
        }

        System.out.print("Removing Everything\n");

        for (int i = 0; i < 10; i++) {
            myDeque.removeLast();
            myDeque.print();
        }

        System.out.print("Testing Iterator\n");

        Iterator<Integer> it = myDeque.iterator();

        System.out.println("Adding items to list");
        for (int i = 0; i < 10; i++) {
            myDeque.addLast(i);
            myDeque.print();
        }

        for (int s : myDeque) {
            System.out.println(s);
        }

        System.out.println(it.next());
        */
    }
}
