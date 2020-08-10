package p2.sorts;

import java.util.Comparator;
import cse332.interfaces.worklists.WorkList;
import datastructures.worklists.MinFourHeap;

public class HeapSort {
    public static <E extends Comparable<E>> void sort(E[] array) {
        sort(array, (x, y) -> x.compareTo(y));
    }

    public static <E> void sort(E[] array, Comparator<E> comparator) {
        WorkList<E> heap = new MinFourHeap(comparator);
        for (E element: array) {
            heap.add(element);
        }
        for (int i = 0; i < array.length; i++) {
            array[i] = heap.next();
        }
    }
}
