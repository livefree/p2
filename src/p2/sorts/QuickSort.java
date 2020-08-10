package p2.sorts;

import java.util.Comparator;

public class QuickSort {
    public static <E extends Comparable<E>> void sort(E[] array) {
        QuickSort.sort(array, (x, y) -> x.compareTo(y));
    }

    public static <E> void sort(E[] array, Comparator<E> comparator) {
        quickSortHelper(array, 0, array.length - 1, comparator);
    }

    public static <E> void quickSortHelper(E[] arr, int startIndex, int endIndex, Comparator<E> comparator) {
        int pivotIndex = findPivot(arr, startIndex, endIndex, comparator);
        swap(arr, startIndex, pivotIndex);
        E pivot = arr[startIndex];
        int lowIndex = startIndex + 1;
        int highIndex = endIndex;
        while (lowIndex <= highIndex) {
            if (comparator.compare(arr[highIndex], pivot) > 0) {
                highIndex--;
            } else if (comparator.compare(arr[lowIndex], pivot) <= 0) {
                lowIndex++;
            } else { // swap values
                swap(arr, lowIndex, highIndex);
            }
        }
        // moving the pivot
        swap(arr, startIndex, highIndex);

        int nextEndIndex = highIndex - 1;
        int nextStartIndex = highIndex + 1;
        int leftSideSize = nextEndIndex - startIndex;
        int rightSideSize = endIndex - nextStartIndex;

        if (leftSideSize > 0) {
            quickSortHelper(arr, startIndex, nextEndIndex, comparator);
        }
        if (rightSideSize > 0) {
            quickSortHelper(arr, nextStartIndex, endIndex, comparator);
        }
    }

    // Takes an array and two integers as parameters
    // Swaps the elements stored at the given two indexes
    private static <E> void swap(E[] arr, int first, int second) {
        E temp = arr[second];
        arr[second] = arr[first];
        arr[first] = temp;
    }

    // Takes an array, two ints representing the index of the boundaries of the array
    // and a Comparator
    // Approximates the "median" of the array by comparing the elements at the boundary
    // and at the middle
    // Returns the index of the "median" pivot
    private static <E> int findPivot(E[] arr, int first, int second, Comparator<E> comparator) {
        E firstElement = arr[first];
        E lastElement = arr[second];
        int middle = ((second - first) / 2) + first;
        E middleElement = arr[middle];
        boolean firstLessThanMiddle = comparator.compare(firstElement, middleElement) < 0;
        boolean middleLessThanLast = comparator.compare(middleElement, lastElement) < 0;
        boolean lastLessThanMiddle = comparator.compare(lastElement, middleElement) < 0;
        boolean middleLessThanFirst = comparator.compare(middleElement, firstElement) < 0;
        boolean firstLessThanLast = comparator.compare(firstElement, lastElement) < 0;
        boolean lastLessThanFirst = comparator.compare(lastElement, firstElement) < 0;

        if ((firstLessThanMiddle && middleLessThanLast) || (lastLessThanMiddle && middleLessThanFirst)) {
            return middle;
        } else if ((middleLessThanFirst && firstLessThanLast) || (lastLessThanFirst && firstLessThanMiddle)) {
            return first;
        } else {
            return second;
        }
    }
}
