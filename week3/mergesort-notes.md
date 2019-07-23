# Merge Sort

## Basic Plan

- Divide the array in half
- Recursively sort left and right half
- Merge two halves

## Merging: Java Implementation

```java
private static void merge(Comparable[] a, Comparable[] aux, int lo, int mid, int hi)
{
    assert isSorted(a, lo, mid);
    assert isSorte(a, mid+1, hi); // Must satisfy that both half is sorted

    // Copy into the auxillery array
    for (int k = lo; k <= hi; k++)
        aux[k] = a[k];

    int i = lo, j = mid + 1;
    for (int k = lo; k <= hi; k++)
    {
        // Case if we hit the mid (last element of the left half)
        if (i > mid)
            a[k] = aux[j++];

        // Case if we hit the last element of the right half
        else if (j > hi)
            a[k] = aux[i++];

        // Compare the jth item of the right half with the ith item of the left half
        else if (less(aux[j]), aux[i])
            a[k] = aux[j++];

        // If the elements are the same take from the left half
        else
            a[k] = aux[i++];
    }

    assert isSorted (a, lo, hi); // Postcondition: a[lo..hi] is sorted
}
```

## Assertion

**Assertion:** Statements to lest assumtions about your program

- Helps detect logic bugs.
- Documents code.

**Java assert statement:** Throws exception unless boolean conditon is true.

```java
assert isSorted(a, lo, hi);
```

**Can enable or disable at runtime** => No cost in production code

```java

java -ea MyProgram // enable assertions
java -da MyProgram // disable assertions (default)
```

**Best practices:** Use assertions to check internal invariants; assume assertions will be disabled in production code (do not use for external argument checking)

## Mergesort: Java Implementation

```java
public class Merge
{
    private static void merge(...)
    {/* as before */}

    private static void sort (Comparable[] a, Comparable[] aux, int lo, int hi) {
        // Recursive base case
        if (hi <= lo)
            return;

        // Calculate the middle element
        int mid = lo + (hi - lo) / 2

        sort(a, aux, lo, mid);
        sort(a, aux, mid+1, hi);
        merge(a, aux, lo, mid, hi);
    }

    public static void sort(Comparable[] a)
    {
        aux = new Comparable[a.length];
        sort(a, aux, 0, a.length - 1);
    }
}
```

## Mergesort: empirical analysis

**Running time estimates:**

- Laptop executes 10^8 compares/second
- Supercomputer executes 10^12 compares/second

1. Home computer:

   Insertion sort (N^2):
   - input size of thousand: instant
   - million: 2.8 hours
   - billion: 317 years

   Merge sort (N log N):
   - thousand: instant
   - million: **1 second**
   - billion: **18 min**

2. Super computer:

   Insertion sort(N^2)
   - thousand: instant
   - million: 1 second
   - billion: 1 week

   Mergesort (N log N):
   - thousand: instant
   - million: **instant**
   - billion: **instant**

**Bottom Line:** Good algorithms are better than supercomputers.

## Mergesort: number of compares and array accesses

**Proposition:** Mergesort uses at most N lg N compares and 6 N lg N array accesses to sort any array of size N.

**Pf sketch:**

Look back at slides when I understand proofs and reoccruences again

## Mergesort analysis: memory

----

**Prosition.** Mergesory uses extra space proportional to N.

**Pf.** The array `aux[]` needs to be size N for the last merge

**Def.** A sorting algorithm is in-place if it uses <= c log N extra memory.

**Ex.** Insertion sort, selection sort, shellsort.

## Mergesort: practical improvements

**Use insertion sort for small subarrays.**

- Mergesort has too much overhead for tiny subarrays.
- Cutoff to insertion sort for ~ 7 items.

```java
private static void sort(Comparable[] a, Comparable[] aux, int lo, int hi)
{
    if (hi <= lo + CUTOFF - 1)
    {
        Insertion.sort(a, lo, hi);
        return;
    }
    int mid = lo + (hi - lo) / 2;
    sort(a, aux, lo, mid);
    sort(a, aux, mid+1, hi);
    merge(a, aux, lo, mid, hi);
}
```

**Stop if already sorted.**

- Is biggest item in first half <= smallest item in second half?
- Helps for partially-ordered arrays.

```java
private static void sort(Comparable[] a, Comparable[] aux, int lo, int hi)
{
    if (hi <= lo) return;
    int mid = lo + (hi - lo) / 2;
    sort(a, aux, lo, mid);
    sort(a, aux, mid+1, hi);
    if (!less(a[mid+1], a[mid])) return;
    merge(a, aux, lo, mid, hi);
}
```

**Eliminate the copy to the auxiliary array.** Save time (but not space) by switching the role of the input and auxiliary array in each recursive call.

```java
private static void merge(Comparable[] a, Comparable[] aux, int lo, int hi)
{
    int i = lo;
    int j = mid + 1;
    for (int k = lo; k <= hi; k++)
    {
        // Merge from a[] to aux[]
        if (i > mid)
            aux[k] = a[j++];

        else if (j > hi)
            aux[k] = a[i++];

        else if (less(a[j], a[i]))
            aux[k] = a[j++];

        else
            aux[k] = a[i++];
    }
}

private static void sort(Comparable[] a, Comparable[] aux, int lo, int hi)
{
    if (hi <= lo)
        return;

    int mid = lo + (hi - lo) / 2;
    // Switch the roles of aux[] and a[]
    sort(aux, a, lo, mid);
    sort(aux, a, mid+1, hi);
    merge(a, aux, lo, mid, hi);
    // Note: sort(a) initialize aux[] and sets aux[i] = a[i] for each i.
}
```

# Bottom-up Mergesort

**Basic plan.**

- Pass through array, merging subarrays of size 1.
- Repeat for subarrays of size 2, 4, 8, 16, ....

```java
public class MergeBU
{
    private static void merge(...)
    {/* as before */}

    public static void sort(Comparable[] a)
    {
        int N = a.length;
        Comparable[] aux = new Comparable[N];
        for (int sz = 1; sz < N; sz = sz + sz)
            for (int lo = 0; lo < N-sz; lo += sz+sz)
                merge(a, aux, lo, lo+sz-1, Math.min(lo+sz+sz-1, N-1));
    }
}
```

**Bottom line.** Simple and non-recursive version of mergesort.

- But about 10% slower than recursive, top-down mergesort on typical systems.

# Sorting Complexity

## Complexity of sorting

**Computational complexity.** Framework to study efficiency of algorithms for solving a particular problem X.

**Model of computation.** Allowable operations.

**Cost model.** Operation counts(s).

**Upperbound.** Cost guarantee provided by **some** algorithm for X.

**Lower bound.** Proven limit on cost guarantee of **all** algorithms for X.

**Optimal algorithm.** Algorithm with best possible cost guarantee for X.

- lowerbound ~ upper bound

**Example: Sorting.**

- Model of computation: decision tree.
  - can access information only through compares (e.g., Java Comparable framework)

- Cost model: # of compares.
- Upper bound: ~ N lg N from mergesort.
- Lower bound: ?
- **Optimal algoritm: mergesort.**

**First goal of algorithm design:** optimal algorithms.

## Decision tree (for 3 distinct items a, b, and c)

Look at the slides

## Compare-based lower bound for sorting

**Proposition.** Any compare-based sorting algorithm must use at least ln (N!) ~ N lg N compares in the worst-case.

**Pf.**

- Assume array consists of N distinct values a1 through aN.
- Worst case dicitated by height h of decision tree.
- Binary tree of height h has at most 2^h leaves.
- N! different orderings => at least N! leaves.

2^h >= # of leaves >= N! => h >= lg(N!) ~ N lg N

- Stirling's formula

## Complexity results in context

**Compares?** Mergesort **is** optimal with respect to number compares.

**Space?** Mergesort **is not** optimal with respect to space usage.

**Lessons.** Use theory as a guide.

**Ex.** Design sorting algorithm that guarantees 1/2 N lg N compares?

**Ex.** Design sorting algoirthm that is both time- and space-optimal?

Lower bound may not hold if the algorithm has information about:

- The inital order of the input.
- The distribution of key values.
- The representation of keys.

**Partially-ordered arrays.** Depending on the inital order of the input, we may not need N lg N compares.

- Insertion sort requires only N-1 compares if input array is sorted

**Duplicate keys.** Depending on the input distribution of duplications, we may not need N lg N comapres

- 3-way quicksort

**Digital properties of keys.** We can use digit/character compares instead of key compares for numbers and strings.

- radix sorts

# Comparators

## Comparable interface: review

**Comparable interface:** sort using a type's **natural order**.

```java
public class Date implemnts Comparable<Date>
{
    private final int month, day, year;

    public Date(int m, int d, int y)
    {
        month = m;
        day = d;
        year = y;
    }

    ...
    // Natural order
    public int compareTo(Date that)
    {
        if (this.year < that.year ) return;
        if (this.year > that.year ) return;
        if (this.month < that.month) return;
        if (this.month > that.month) return;
        if (this.day < that.day ) return;
        if (this.day > that.day ) return;
        return 0;
    }
}
```

## Comparator Interface

Sort using an **alternate order**.

```java
public interface Comparator<Key>
    int compare(Key v, Key w) // Compare keys v and w
```

**Required property.** Must be a **total order.**

**Ex.** Sort strings by:

- Natural order
- Case insensitive
- Spanish
- British phone book.
- ...

## Comparator interface: system sort

**To use with Java system sort:**

- Create Comparator object.
- Pass as second arguemnt to Arrays.sort().

```java
String[] a;
...
// Natural order
Arrays.sort(a);
...
// Uses alternate order defined by Comparator<String> object
Arrays.sort(a, String.CASE_INSENSITIVE_ORDER);
...
Arrays.sort(a, Collator.getInstance(new Locale("es")));
...
Arrays.sort(a, new BritishPhoneBookOrder());
```

**Bottom line.** Decouples the definition of the data type from the definition of what it means to compare two objects of that type

## Comparator interface: using with our sorting libraries

**To support comparators in our sort implementations:**

- Use `Object` instead of Comparable.
- Pass `Comparator` to `sort()` and `less()` and use it in `less()`.

```java
public class Student
{
    // One Comparator for the class
    public static final Comparator<Student> BY_NAME = new ByName();
    public static final Comparator<Student> BY_SECTION = new BySection();
    private final String name;
    private final int section;
    ...

    private static class ByName implements Comparator<Student>
    {
        public int compare(Student v, Student w)
        { return v.name.compareTo(w.name); }
    }

    private static class BySection implements Comparator<Student>
    {
        public int compare(Student v, Student w)
        // This technique works here since no dancer of overflow
        { return v.section - w.section; }
    }
}
```

## Comparator interface: implementing

**To implement a comparator:**

- Define a (nested) class that implements the `Comparator` interface.
- Implement the `compare()` method.

## Polar order

Look at slides for notes on appying the graham scan to polar orders

# Stablility

**A typical application.** First, sort by name; **then** sort by section.

A **stable** sort preserves the relative order of items with equal keys

**Q.** Which sorts are stable?
**A.** Insertion sort and mergesort (but not selection sort or shellsort)

**Note.** Need to carefully check code ("less than" vs. "less than or equal to").

Basically when there is a range exchange between items (sheelsort, selection sort, quicksort) the algorithm will likely not be stable

- **Insertion sort** is **stable** because equal items never move past each other.
- **Selection sort** is **not stable** because long-distance exchange might move an item past some equal item.
- **Shellsort** is **not stable** because there is long-distance exchanges.
- **Mergesort** is **stable** because the mergesort itself is pretty self explaintory

- **Merge operation** is stable because it takes from left subarray if the keys are equal.
