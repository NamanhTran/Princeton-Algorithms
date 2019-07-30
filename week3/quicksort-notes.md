# Quicksort

**Basic Plan.**

- **Shuffle** the array.
- **Partition** so that, for some j
  - entry a[j] is in place
  - no larger entry to the left of j
  - no smaller entry to the right of j
- **Sort** each piece recursively.

## Quicksort partitioning demo

**Repeat until i and j pointers cross**

- Scan i from left to right so long as (a[j] < a[lo]).
- Scan j from right to left so long as (a[j] > a[lo]).
- Exchange a[i] with a[j].

Look at video for visual demo

## Quicksort: Java code for partitioning

```java
private static int partition(Comparable[] a, int lo, int hi)
{
    int i = lo
    int j = hi+1;
    while(true)
    {
        // Find item on left to swap
        while(less(a[++i], a[lo]))
            if (i == hi)
                break;

        // Find item on right to swap
        while (less(a[lo], a[--j]))
            if (j == lo)
                break;

        // check if pointers cross then swap if true
        if (i >= j) break;
        exch(a, i, j);
    }

    // swap with partitioning item
    exch(a, lo, j);

    // Return index of item now known to be in place
    return j;
}
```

## Quicksort: Java Implementation

```java
public class Quick
{
    private static int partition(Comparable[] a, int lo, int hi)
    { /* see previous slide */ }

    public static void sort(Comparable[] a)
    {
        // Shuffle needed for performance guartantee (stay tuned)
        StdRandom.shuffle(a);
        sort(a, 0, a.length - 1);
    }

    private static void sort(Comparable[] a, int lo, int hi)
    {
        if (hi <= lo) return;
        int j = partiton(a, lo, hi);
        sort(a, lo, j - 1);
        sort(a, j + 1, hi);
    }
}
```

## Quicksort: implementation details

**Partitioning in-place** Using an extra array makes partitioning easier (and stable), but is not worth the cost.

**Terminating the loop.** Testing whether the pointers cross is a bit tricker than it might seem.

**Staying in bounds.** The (j == lo) test is redundant (why?), but the (i == hi) test is not.

**Preserving randomnes.** Shuffling is needed for performance guarantee.

**Equal Keys.** When duplicates are presentm it is (counter-intuitively) bettwe to stop on keys equal to the partitioning item's key

## Quicksort: empirical analysis

**Running time estimates:**

- Home PC executes 10^8 compares/second
- Supecomputer executes 10^12 compares/second

1. Home computer:

   Insertion sort (N^2):
   - input size of thousand: instant
   - million: 2.8 hours
   - billion: 317 years

   Mergesort (N log N):
   - thousand: instant
   - million: 1 second
   - billion: 18 min

   Quicksort (N log N)
   - thousand: instant
   - million: **.6 sec**
   - billion: **12 mins**

2. Super computer:

   Insertion sort(N^2)
   - thousand: instant
   - million: 1 second
   - billion: 1 week

   Mergesort (N log N):
   - thousand: instant
   - million: instant
   - billion: instant

   Mergesort (N log N):
   - thousand: instant
   - million: instant
   - billion: instant

**Lesson 1.** Good algorithms are better than supercomputers.

**Lesson 2.** Great algorithms are better than good ones.

## Quicksort: summary of performance characteristics

**Worst case.** Number of compares is quadratic.

- 1/2 N^2
- More likely that your computer is struck by lightning bolt


**Average Case.** Number of compares is ~1.39 N lg N.

- 39% more compares than mergesort.
- **BUT** faster than mergesort in practice because of less data movement.

**Random shuffle.**

- Probabilistic guarantee against worst case.
- Basis for math model that can be avalidated with experiments.

**Caveat emptor.** Many textbook implmentation go **quadratic** if array

- Is sorted or reverse sorted.
- Has many duplicates (even if randomized!)

## Quicksort properties

**Proposition.** Quicksort is an **in-place** sorting algorith,.

**Pf.**

- Partitioning: constant extra space.
- Depth of recursion: Logarithmic extra space (with high probability).
  - Can guarantee logarithmic depth by recurring on smaller subarray before larger subarray

Quicksort is **not stable**.

## Quicksort: practical improvements

**Insertion srot small subarrays.**

- Even quicksort has too much overhead for tiny subarrays.
- Cutoff to insertion sort for ~10 items
- Note: could delay insertion sort until one pass at end.

```java
private static void sort(Comparable[] a, int lo, int hi)
{
    if (hi <= lo + CUTOFF - 1)
    [
        Insertion.sort(a, lo, hi);
        return;
    ]
    int j = partition(a, lo, hi);
    sort(a, lo, j-1);
    sort(a, j+1, hi);
}
```

**Meadian of sample.**

- Best choice of pivot item = median
- Estimate true median by taking median of sample.
- Median-of-3 (random) items
  - ~12/7 N ln N compares (slightly fewer)
  - 12/35 N ln N exchanges (slightly more)

```java
private static void sort(Comparable[] a, int lo, int hi)
{
    if (hi <= lo) return;

    int m = medianOf3(a, lo, lo + (hi - lo)/2, hi);
    swap(a, lo, m);

    int j = partition(a, lo, hi);
    sort(a, lo, j-1);
    sort(a, j+1, hi);
}
```

# Selection

**Goal.** Given an array of N items, find a kth smallest item.

**Ex.** Min (k = 0), max (k = N -1), median (k = N/2).

**Applications.**

- Order statistics.
- Find the "top k"
  
**Use theory as a guide.**

- Easy N log N upper bound. How?
  - Sort the array if want to look at first look at first or if want last look at last or middle look at middle
- Easy N upper bound for k = 1, 2, 3. How?
  - If k is small we can just go through the array and just find it.
- Easy N lower bound. Why?
  - Have to look at everything or else you miss what you are looking for

**Which is true?**

- N log N lower bound?
  - Is selection as hard as sorting?
- N upper bound?
  - is there a linear-time algorithm for each k?

## Quick Select

**Partition array so that:**

- Entry `a[j]` is in place.
- No larger entry to the left of j
- No smaller entry to the right of j.

**Repeat** in **one** subarray, depending on j; finished whe nj equals k.

```java
public static Comparable select(Comparable[] a, int k)
{
    StdRandom.shuffle(a);
    int lo = 0;
    int hi = a.length - 1;
    while (hi > lo)
    {
        int j = partition(a, lo, hi);
        if (j < k)
            lo = j + 1;

        else if (j > k)
            hi = j - 1;

        else
            return a[k];
    }

    return a[k];
}
```

## Quick-select: mathematical analysis

**Proposition.** Quick-select takes **linear** time on average.

**Pf sketch.**

Look back to slides

**Remark.** Quick-slection uses ~1/2 N^2 compares in the worst case, but (as with quicksort) the random shuffle provides a probabilistic guarantee.

# Duplicate keys

**Often, purpose of sort is to bring items with equal keys together.**

- Sort population by age.
- Remove duplicates from mailing list
- Sort job applicats by college attended

** Typeical characteristics of such applications.** 

- Huge array.
- Small number of key values.

**Mergesort with duplicate keys.** Beitween 1/2 N lg N and N lg N compares.

**Quicksort with duplicate keys.**

- Algorithm goes **quadratic** unless pratitioning stops on equal keys!
- 1990s C user found this defect in qsort()
  - several textbook and system implementation also have this defect

## Duplicate keys: the problem

**Mistake**. Put all items equal to the partitioning item on one side.

**Consequence.** ~1/2 N^2 compares when all keys equal.

```text
B A A B A B B **B** C C C
A A A A A A A A **A**
```

**Recommended.** Stop scans on items equal to the partitioning item.

**Consequence.** ~ N lg N compares when all keys equal

```text
B A A B A **B** C C B C B
A A A A **A** A A A 
```

**Desirable.** Put all tiems equal to the partitioning item in place.

```text
A A A B B B B B C C C
A A A A A A A A A A A
```

## 3-way partitioning

**Goal.** Partition array into 3 parts so that:

- Entries between `lt` and `gt` equal to partition item v.
- No larger entries to left of `lt`.
- No smaller entries to right of `gt`.

## 3-way quicksort: Java implementation

```java
private static void sort(Comparable[] a, int lo, int hi) 
{
    if (hi <= lo) return;
    int lt = lo;
    int gt = hi;
    Comparable v = a[lo];
    int i = lo;
    while (i <= gt)
    {
        int cmp = a[i].compareTo(v);
        if (cmp < 0)
            exch(a, lt++, i++);

        else if (cmp > 0)
            exch(a, i, gt--);

        else
            i++;
    }

    sort(a, lo, lt - 1);
    sort(a, gt + 1, hi);
}
```

## Duplicate keys: lower bound

**Sorting lower bound.** If there are n distinct keys and the ith one occurs x times, any compare-based sorting algorithm must use at least  x<sub>i</sub> lg $\frac{xi}{N}$

- N lg N when all distinct
- linear when only a constant number of distanct keys

Quicksort with 3-way partitioning is entropy-optimal.

**Bottom line.** Randomized quicksort with 3-way partitioning reduces running time from linearithmic to linear in broad class of applications.

# System sort

## Sorting applications

**Sorting algorithms are essential in a broad variety of applications:**

Obvious applications

- Sort a list of names.
- Organize a MP3 library.

Problems become easy once items are in sorted order

- Find the midian.
- Identify statistical outliers.
- Binary search in a database.
- Find duplicates in a mailing list.

None-obvious applications

- Data compression
- Computer graphics
- Computational biology
- Load balancing on a parallet computer.
- ...

## Java system sorts

**Arrays.sort()**

- Has different method for each primitive type.
- Has a method for data types that implement `Comparable`
- Has a method thaht uses a `Comparator`
- Uses tuned quicksort for primitve types; tuned mergesort for objects.

**Q.** Why use different algorithms for primitive and reference types?

- Designer assessment that if the user is using objects they may not care about space so extra space in merge sort might not be a problem (allows for stablility)
- If using primitive prgrammer might care about space so it uses quicksort.

## Engineering a system sort

**Basic algorithm = quicksort.**

- Cutoff to insertion sort for small subarrays.
- Partitioning scheme: Bentley-Mcllroy 3-way partitioning.
- Partitioning item.
  - small arrays: middle entry
  - medium arrays: median of 3
  - large arrays: Tukey's ninther

**Now widely uses.** C, C++, Java 6, ....

## Tukey's ninther

Median of the median of 3 samples, each of 3 entries.

- Approximates the midian of 9
- Uses at most 12 compares.

**Q.** Why use Tukey's ninther?

**A.** Better partition than random shuffling and less costly

## Achilles heel in Bentley-Mcllroy implementation (Java system sort)

**Q.** Based on all this research, Java's system sort is solid, right?

**A.** No: A killer input.

- Overflows function call stack in java and crashes program.
- Would take quadratic time if it didn't crash first.

## System sort: Which algorithm to use?

Many sorting algorithms to choose from:

**Internal sorts.** 

- Insertion sort, selection sort, bubblesort, shaker sort
- Quicksort, mergesort, heapsort, samplesort, shellsort
- Solitaire sort, re-black sort, splaysort, **Yaroslavskiy sort**, psort, ...


**External sorts.** Poly-phase mergesort, cascade-merge, oscillating sort.

**String/radix sorts.** Distribution, MSD, LSD, 3-way string quicksort.

**Parallel sorts**

- Bitonic sort, Batcher even-odd sort
- Smooth sort, cube sort, column sort.
- GPUsort

## System sort: Which algorithm to use?

**Applications have diverse attributes.**

- Stable?
- Parallet?
- Deterministic?
- Keys all distinct?
- Multiple key types?
- Linked list or arrays?
- Large or small items?
- Is your array randomly ordered?
- Need guaranteed performance?

Elementary sort may be method of choice for some combination. Cannot cover **all** combinations of attributes.

System sort is usally good enough.
