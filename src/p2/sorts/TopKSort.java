package p2.sorts;

import cse332.interfaces.worklists.WorkList;
import datastructures.worklists.MinFourHeap;

import java.util.Comparator;

public class TopKSort {
    public static <E extends Comparable<E>> void sort(E[] array, int k) {
        sort(array, k, (x, y) -> x.compareTo(y));
    }

    public static <E> void sort(E[] array, int k, Comparator<E> comparator) {
        WorkList<E> heap = new MinFourHeap(comparator);
        for (int i = 0; i < Math.min(k, array.length); i++) {
            heap.add(array[i]);
        }
        // loop through n - k elements
        for (int i = k; i < array.length; i++) {
            // adding and removing from heap is O(log k)
            E currentElement = array[i];
            if (comparator.compare(currentElement, heap.peek()) > 0) {
                heap.add(currentElement);
                heap.next();
            }
        }
        // O(n) runtime
        for (int i = 0; i < array.length; i++) {
            if (i < k) {
                array[i] = heap.next();
            } else {
                array[i] = null;
            }
        }
    }
}
