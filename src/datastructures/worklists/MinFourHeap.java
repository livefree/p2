package datastructures.worklists;

import cse332.interfaces.worklists.PriorityWorkList;

import java.util.Comparator;
import java.util.NoSuchElementException;

/**
 * See cse332/interfaces/worklists/PriorityWorkList.java
 * for method specifications.
 */
public class MinFourHeap<E extends Comparable<E>> extends PriorityWorkList<E> {
    /* Do not change the name of this field; the tests rely on it to work correctly. */
    private E[] data; // an array to store works from index 0
    private int size; // number of works in data array
    private Comparator<E> comparator;
    private static final int NUM_CHILDREN = 4;

    public MinFourHeap(Comparator<E> comparator) {
        this.data = (E[]) (new Comparable[10]);
        this.size = 0;
        this.comparator = comparator;
    }

    @Override
    public boolean hasWork() {
        return this.size != 0;
    }

    /**
     * add new work to heap and maintain min-heap data structure
     *
     * @param work  the work to add to the worklist
     */
    @Override
    public void add(E work) {
        // expand data size if full
        if (this.size == this.data.length) {
            E[] tempData = (E[]) new Comparable[this.data.length * 2];
            for (int index = 0; index < this.data.length; index++) {
                tempData[index] = data[index];
            }
            data = tempData;
        }
        // add new work to rightmost
        data[percolateUp(this.size, work)] = work;
        this.size++;
    }

    private int percolateUp(int hole, E work) {
        while ((hole >= 1) && comparator.compare(work, this.data[(hole - 1) / NUM_CHILDREN]) < 0) {
            this.data[hole] = this.data[(hole - 1) / NUM_CHILDREN];
            hole = (hole - 1) / NUM_CHILDREN;
        }
        return hole;
    }

    /**
     * Returns a view to the next element of the worklist.
     *
     * @return the next element in this worklist
     * @throws NoSuchElementException if hasWork() is false
     */
    @Override
    public E peek() {
        if (this.hasWork()) {
            return this.data[0];
        } else {
            throw new NoSuchElementException();
        }
    }

    /**
     * Returns and removes the next element of the heap
     *
     * @return the next element in this heap
     * @throws NoSuchElementException if hasWork() is false
     */
    @Override
    public E next() {
        E nextWork;
        if (!this.hasWork()) {
            throw new NoSuchElementException();
        }
        nextWork = data[0];
        int hole = percolateDown(data[this.size - 1]);
        this.data[hole] = this.data[this.size - 1];
        this.size--;
        return nextWork;
    }

    /**
     * @param work a work need to percolate down
     * @return index of where work should go.
     */
    private int percolateDown(E work) {
        int hole = 0;
        while (NUM_CHILDREN * hole < this.size - 1) {
            int target = minOf4(hole);
            if (comparator.compare(data[target], work) < 0) {
                data[hole] = data[target];
                hole = target;
            } else {
                break;
            }
        }
        return hole;
    }

    // Takes an integer that represent index of the heap
    // returns the index of the smallest child of the given index
    private int minOf4(int hole) {
        int child = NUM_CHILDREN * hole + 1;
        int min = child;
        for (int i = 1; i < NUM_CHILDREN + 1 && child < size; i++, child++) {
            if (comparator.compare(data[min], data[child]) > 0) {
                min = child;
            }
        }
        return min;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public void clear() {
        this.data = (E[]) new Comparable[10];
        this.size = 0;
    }
}
