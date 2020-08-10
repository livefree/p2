package datastructures.worklists;
import cse332.interfaces.worklists.LIFOWorkList;

import java.util.NoSuchElementException;

/**
 * See cse332/interfaces/worklists/LIFOWorkList.java
 * for method specifications.
 */
public class ArrayStack<E> extends LIFOWorkList<E> {
    private E[] arr;
    private int size;

    public ArrayStack() {
        arr = (E[]) new Object[10];
        size = 0;
    }

    @Override
    public void add(E work) {
        // doubles array size when capacity is reached
        if (size >= arr.length) {
            E[] temp = (E[]) new Object[arr.length * 2];
            for (int i = 0; i < arr.length; i++) {
                temp[i] = arr[i];
            }
            arr = temp;
        }
        arr[size] = work;
        size++;
    }

    @Override
    public E peek() {
        // inherits hasWork() from Worklist
        // throws exception when trying to peek when there are no elements
        if (!this.hasWork()) {
            throw new NoSuchElementException();
        }
        return arr[size - 1];
    }

    @Override
    public E next() {
        // throws exception when there are no elements
        if (!this.hasWork()) {
            throw new NoSuchElementException();
        }
        E lastValue = this.peek();
        size--;
        return lastValue;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        arr = (E[]) new Object[10];
        size = 0;
    }
}
