# Priority Queues

**Collections.** Insert and delete items, Which item to delete?

**Stack.** Remove the item most recently added.

**Queue.** Remove the item least recently added.

**Randomized queue.** Remove a random item.

**Priority queue.** Remove the **largest** (or **smallest**)

## API

**Requirement** Generic items are Comparable.

```java
public class MaxPQ<Key extends Comparable<Key>> {
    MaxPQ() // Constructor for empty PQ
    MaxPQ(Key[] a) // Conditonal constructor for PQ
    void insert(Key v) // Insert a key into the priority queue
    Key delMax() // Return and remove the largest Key
    boolean isEmpty() // Is the PQ empty?
    Key max() // Return the largest key
    int size() // number of entries in the PQ
}
```

## Application of PQ

- Event-driven simulation. (Customers in a line, colliding particles)
- Numberical computation. (reducing roundoff error)
- Data compression. (Huffman codes)
- Graph searching. (Dijkstra's algorithm, Prim's algorithm)
- Number theory (sum of power)
- Artificial intelligence (A* search)
- Statistics. (maintain largest M values in a sequence)
- Operating Systems. (load balancing, interrupt handling)
- Discrete optimization. (bin packing, scheduling)
- Spam filtering (Bayesian spam filter)

**Generalizes:** stack, queue, randomized queue.