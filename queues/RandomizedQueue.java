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
        if (isEmpty())
            throw new java.util.NoSuchElementException("The queue is empty");

        int random = StdRandom.uniform(front, end) % queue.length;

        // Remove item from the front
        Item item = queue[random];
        queue[random % queue.length] = null;

        shiftArr(random);

        size--;

        // If array is 1/4th full then reduce the size
        if (size > 0 && size == queue.length / 4) {
            //System.out.println("Resizing from " + queue.length + " to " + queue.length / 2);
            resizeArr(queue.length / 2);
        }

        return item;
    }

    // Return a random item (but do not remove it)
    public Item sample() {

        if (isEmpty())
            throw new java.util.NoSuchElementException("The queue is empty");

        // Use StdRandom to get a random array index between front and end
        int random = StdRandom.uniform(front, end) % queue.length;

        return queue[random];
    }

    // Return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new IterableQueue();
    }

    private class IterableQueue implements Iterator<Item> {
        private Item[] itQueue = queue;
        private int itFront = front;
        private int itEnd = end;
        private boolean firstRun = true;

        // Seed?
        public boolean hasNext() {
            if (itFront == itEnd)
                return false;

            return true;
        }

        public Item next() {
            if (!hasNext())
                throw new java.util.NoSuchElementException(
                        "The iterator has no more items to return");

            if (firstRun) {
                int start;
                int end;

                if (itFront % itQueue.length < itEnd % itQueue.length) {
                    start = itFront % itQueue.length;
                    end = itEnd % itQueue.length;
                }

                else {
                    start = itEnd % itQueue.length;
                    end = itFront % itQueue.length;
                }

                StdRandom.shuffle(itQueue, start, end);
                firstRun = false;
            }

            return itQueue[itFront++ % itQueue.length];
        }
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

    private void shiftArr(int pos) {
        if (pos == front) {
            front++;
        }

        else if (pos == end) {
            end--;
        }

        else {
            int disToFront = Math.abs(front - pos);
            int disToBack = Math.abs(end - pos);

            // Shift the array one up (to pos)
            if (disToFront < disToBack) {
                Item nextItem = null;

                for (int i = front; i <= pos; i++) {
                    Item temp = queue[i % queue.length];
                    queue[i % queue.length] = nextItem;
                    nextItem = temp;
                }

                front++;
            }

            // Shift the array one down (to pos)
            else {
                Item prevItem = null;

                for (int i = end; i >= pos; i--) {
                    Item temp = queue[i % queue.length];
                    queue[i % queue.length] = prevItem;
                    prevItem = temp;
                }

                end--;
            }
        }
    }

    private void print() {
        for (int i = 0; i < queue.length; i++) {
            System.out.print(queue[i] + " | ");
        }

        System.out.print("END\n");
    }

    // Unit testing (optional)
    public static void main(String[] args) {
        RandomizedQueue<Integer> myRQ = new RandomizedQueue<Integer>();

        myRQ.print();

        System.out.println("\nAdding to the RQ");
        for (int i = 0; i < 8; i++) {
            myRQ.enqueue(i);
            myRQ.print();
        }

        myRQ.print();

        System.out.println("\nRemoving from the RQ");
        for (int i = 0; i < 8; i++) {
            System.out.println("Removed: " + myRQ.dequeue());
            myRQ.print();
        }

        //myRQ.sample();

        System.out.println("\nTesting Iterator\n");

        System.out.println("\nAdding to the RQ");
        for (int i = 0; i < 10; i++) {
            myRQ.enqueue(i);
            myRQ.print();
        }

        Iterator<Integer> it = myRQ.iterator();

        for (int s : myRQ) {
            System.out.println(s);
        }

    }
}
