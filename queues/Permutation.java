/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;

public class Permutation {
    public static void main(String[] args) {
        RandomizedQueue<String> myRQ = new RandomizedQueue<String>();

        String[] str = StdIn.readAllStrings();
        for (int i = 0; i < str.length; i++) {
            myRQ.enqueue(str[i]);
        }

        for (int i = 0; i < Integer.parseInt(args[0]); i++) {
            System.out.println(myRQ.dequeue());
        }
    }
}
