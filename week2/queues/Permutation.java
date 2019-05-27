/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;

public class Permutation {
    public static void main(String[] args) {
        RandomizedQueue<String> myRQ = new RandomizedQueue<String>();

        while(!StdIn.isEmpty()) {
            myRQ.enqueue(StdIn.readString());
        }

        for (int i = 0; i < Integer.parseInt(args[0]); i++) {
            System.out.println(myRQ.dequeue());
        }
    }
}
