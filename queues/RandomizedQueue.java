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
        if (size == queue.length)
            resizeArr(2 * queue.length);

        // Add item to the end of the array
        queue[end++ % queue.length] = item;

        // Increase the size of the queue
        size++;
    }

    // Remove and return a random item
    public Item dequeue() {
        if (isEmpty())
            throw new java.util.NoSuchElementException("The queue is empty");

        // Generate a random number between front and end
        int random = StdRandom.uniform(front, end);

        // Remove item from the front
        Item item = queue[random];
        queue[random % queue.length] = null;

        // Shift the array so they are all alined
        shiftArr(random);

        // Reduced the size of the queue
        size--;

        // If array is 1/4th full then reduce the size
        if (size > 0 && size == queue.length / 4)
            resizeArr(queue.length / 2);

        // Return the item
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

        // If the front and end is the same then the queue is empty
        public boolean hasNext() {
            if (itFront == itEnd)
                return false;

            return true;
        }

        // Return the next random item
        public Item next() {
            // If the queue is empty then throw exception
            if (!hasNext())
                throw new java.util.NoSuchElementException(
                        "The iterator has no more items to return");

            // Case for the first iteration 
            if (firstRun) {
                // Calculate the actual array index of start and end
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

                // Shuffle the array once
                StdRandom.shuffle(itQueue, start, end);
                firstRun = false;
            }

            // Return the front of the shuffled array
            return itQueue[itFront++ % itQueue.length];
        }
    }

    private void resizeArr(int n) {
        // Create a new queue array of size N
        Item[] newArr = (Item[]) new Object[n];

        // Copy the content of the queue array into the new array
        int counter = 0;
        for (int i = front; i < end + 1; i++) {
            newArr[counter++] = queue[i % queue.length];
        }

        // Recalculate the new front and end of the array
        end -= front;
        front = 0;

        // Set the class's queue to the new array
        queue = newArr;
    }

    private void shiftArr(int pos) {
        // If the removed element is the front just increment the front by one
        if (pos == front)
            front++;

        // If the removed element is the end just decrement the end by one
        else if (pos == end)
            end--;

        // If the removed element is somewhere in the middle of the array
        else {
            // Calcuate the distance from pos to the front and end
            int disToFront = Math.abs(front - pos);
            int disToBack = Math.abs(end - pos);

            // Shift the array one up (to pos) if the front is closer
            if (disToFront < disToBack) {
                // Holds the next item to shift up
                Item nextItem = null;

                // Shift the array up by one
                for (int i = front; i <= pos; i++) {
                    Item temp = queue[i % queue.length];
                    queue[i % queue.length] = nextItem;
                    nextItem = temp;
                }

                // Readjust the front to move up by one
                front++;
            }

            // Shift the array one down (to pos) if the end is closer
            else {
                // Holds the next item to shift down
                Item prevItem = null;

                // Shift the array down by one
                for (int i = end - 1; i >= pos; i--) {
                    Item temp = queue[i % queue.length];
                    queue[i % queue.length] = prevItem;
                    prevItem = temp;
                }

                // Readjust the front to move down by one
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
        Iterator<Integer> it2 = myRQ.iterator();

        myRQ.dequeue();
        myRQ.dequeue();
        myRQ.dequeue();
        myRQ.print();

        for (int i = 0; i < 8; i++) {
            System.out.println("it: " + it.next());
        }

        for (int i = 0; i < 8; i++) {
            System.out.println("it2: " + it2.next());
        }

    }
}
