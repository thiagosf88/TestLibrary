package edu.performance.test.memoryoperation;

import edu.performance.test.PerformanceTest;
import edu.performance.test.PerformanceTestActivity;
/**
 * This class has code of sort algorithms. Some of them are hard coded and other
 * use JAVA API sorting methods. Merge, Quick (Princeton).
 * @author Thiago
 *
 */
@SuppressWarnings("rawtypes")
public class OrderOperation extends PerformanceTest<Comparable[]>{

	public OrderOperation(Comparable[] level, PerformanceTestActivity activity) {
		super(level, activity);
		activity.executeTest();
	}

	@Override
	public void execute() {
		//TODO testar essas operações
		testTJMmergesort(this.getLevel());
		testTJMquicksort(this.getLevel());
		
	}
	
// Merge Sort Princeton --------------------------------------------------------------------------------
	
	public void testTJMmergesort(Comparable[] a) {
        mergeSort(a, 0, a.length);
    } 

    // Sort a[lo, hi). 
    @SuppressWarnings("unchecked")
	public static void mergeSort(Comparable[] a, int lo, int hi) {
        int N = hi - lo;        // number of elements to sort

        // 0- or 1-element file, so we're done
        if (N <= 1) return; 

        // recursively sort left and right halves
        int mid = lo + N/2; 
        mergeSort(a, lo, mid); 
        mergeSort(a, mid, hi); 

        // merge two sorted subarrays
        Comparable[] aux = new Comparable[N];
        int i = lo, j = mid;
        for (int k = 0; k < N; k++) {
            if      (i == mid)  aux[k] = a[j++];
            else if (j == hi)   aux[k] = a[i++];
            else if (a[j].compareTo(a[i]) < 0) aux[k] = a[j++];
            else                               aux[k] = a[i++];
        }

        // copy back
        for (int k = 0; k < N; k++) {
            a[lo + k] = aux[k]; 
        }
    }
    
    //Quick Sort ---------------------------------------------------------------
    public static void testTJMquicksort(Comparable[] a) {
        //StdRandom.shuffle(a);
        quickSort(a, 0, a.length - 1);
        //assert isSorted(a);
    }

    // quicksort the subarray from a[lo] to a[hi]
    private static void quickSort(Comparable[] a, int lo, int hi) { 
        if (hi <= lo) return;
        int j = partition(a, lo, hi);
        quickSort(a, lo, j-1);
        quickSort(a, j+1, hi);
        //assert isSorted(a, lo, hi);
    }

    // partition the subarray a[lo..hi] so that a[lo..j-1] <= a[j] <= a[j+1..hi]
    // and return the index j.
    private static int partition(Comparable[] a, int lo, int hi) {
        int i = lo;
        int j = hi + 1;
        Comparable v = a[lo];
        while (true) { 

            // find item on lo to swap
            while (less(a[++i], v))
                if (i == hi) break;

            // find item on hi to swap
            while (less(v, a[--j]))
                if (j == lo) break;      // redundant since a[lo] acts as sentinel

            // check if pointers cross
            if (i >= j) break;

            exch(a, i, j);
        }

        // put partitioning item v at a[j]
        exch(a, lo, j);

        // now, a[lo .. j-1] <= a[j] <= a[j+1 .. hi]
        return j;
    }
    
    @SuppressWarnings("unchecked")
	private static boolean less(Comparable v, Comparable w) {
        return (v.compareTo(w) < 0);
    }
        
    // exchange a[i] and a[j]
    private static void exch(Object[] a, int i, int j) {
        Object swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }

}
