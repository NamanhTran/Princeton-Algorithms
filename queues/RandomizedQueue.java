/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private int size;
    private int front;
    private int end;
    private Item[] queue;

    // Construct an empty randomized queue
    public RandomizedQueue() {
        queue = (Item[]) new Object[1];
        front = 0;
        end = 0;
        size = 0;
    }

    // Is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // Return the number of items on the randomized queue
    public int size() {
        return size;
    }

    // Add the item
    public void enqueue(Item item) {
        // If array is full then extend
        if (size == queue.length) {
            resizeArr(2 * queue.length);
        }

        // Add item to the end of the array
        queue[end++ % queue.length] = item;
        size++;
    }

    // Remove and return a random item
    public Item dequeue() {

        // Remove item from the front
        Item item = queue[++front % queue.length];
        queue[front % queue.length] = null;

        // If array is 1/4th full then reduce the size
        if (size > 0 && size == queue.length / 4) {
            resizeArr(queue.length / 2);
        }

        return item;
    }

    // Return a random item (but do not remove it)
    public Item sample() {
        // Use StdRandom to get a random array index between front and end
        int random = StdRandom.uniform(front, end) % queue.length;
        return queue[random];
    }

    // Return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new IterableQueue();
    }

    private class IterableQueue implements Iterator<Item> {
        // Seed?
    }

    private void resizeArr(int n) {
        Item[] newArr = (Item[]) new Object[n];
        int counter = 0;
        for (int i = front; i < end + 1; i++) {
            newArr[counter++] = queue[i % queue.length];
        }

        end -= front;
        front = 0;
        queue = newArr;
    }

    private void print() {

    }

    // Unit testing (optional)
    public static void main(String[] args) {

    }
}
