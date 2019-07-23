# Merge Sort

## Basic Plan

----

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

-----

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

----

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

----

**Proposition:** Mergesort uses at most N lg N compares and 6 N lg N array accesses to sort any array of size N.

**Pf sketch:**

Look back at slides when I understand proofs and reoccruences again

## Mergesort analysis: memory

---

**Prosition.** Mergesory uses extra space proportional to N.

**Pf.** The array ```aux[]``` needs to be size N for the last merge

**Def.** A sorting algorithm is in-place if it uses <= c log N extra memory.

**Ex.** Insertion sort, selection sort, shellsort.
