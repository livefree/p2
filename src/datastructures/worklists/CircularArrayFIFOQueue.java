package datastructures.worklists;

import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.worklists.FixedSizeFIFOWorkList;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * See cse332/interfaces/worklists/FixedSizeFIFOWorkList.java
 * for method specifications.
 */
public class CircularArrayFIFOQueue<E extends  Comparable<E>> extends FixedSizeFIFOWorkList<E> {
    private E[] arr;
    private int front; // front of queue
    private int back; // back of queue
    private int size;
    private int capacity;

    public CircularArrayFIFOQueue(int capacity) {
        super(capacity);
        this.capacity = capacity;
        arr = (E[]) new Comparable[capacity];
        front = 0;
        back = 0;
        size = 0;
    }

    @Override
    public void add(E work) {
        if (this.isFull()) {
            throw new IllegalStateException();
        }
        arr[back] = work;
        back = (back + 1) % capacity;
        size++;
    }

    @Override
    public E peek() {
        if (!this.hasWork()) {
            throw new NoSuchElementException();
        }
        return arr[front];
    }
    
    @Override
    public E peek(int i) {
        if (!this.hasWork()) {
            throw new NoSuchElementException();
        }
        if (i < 0 || i >= size) {
            throw new IndexOutOfBoundsException();
        }
        int placeInArray = front + i;
        return arr[placeInArray % capacity];
    }
    
    @Override
    public E next() {
        if (!this.hasWork()) {
            throw new NoSuchElementException();
        }
        E currentValue = arr[front];
        front = (front + 1) % capacity;
        size--;
        return currentValue;
    }
    
    @Override
    public void update(int i, E value) {
        if (!this.hasWork()) {
            throw new NoSuchElementException();
        }
        if (i < 0 || i >= size) {
            throw new IndexOutOfBoundsException();
        }
        int placeInArray = front + i;
        arr[placeInArray % capacity] = value;
    }
    
    @Override
    public int size() {
        return size;
    }
    
    @Override
    public void clear() {
        arr = (E[]) new Comparable[capacity];
        front = 0;
        back = 0;
        size = 0;
    }

    @Override
    // todo can't find specs to compareTo so did not implement correctly
    // do i need to return -1, 0, 1 or can i return negative, 0 , positive?
    public int compareTo(FixedSizeFIFOWorkList<E> other) {
        Iterator<E> otherIterator = other.iterator();
        Iterator<E> thisIterator = this.iterator();
        while (otherIterator.hasNext() && thisIterator.hasNext()) {
            E otherElement = otherIterator.next();
            E thisElement = thisIterator.next();
            if (!thisElement.equals(otherElement)) {
                return thisElement.compareTo(otherElement);
            }
        }
        // queue has different sizes
        if (thisIterator.hasNext() || otherIterator.hasNext()) {
            return this.size() - other.size();
        }
        return 0;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean equals(Object obj) {
        // You will finish implementing this method in project 2. Leave this method unchanged for project 1.
        if (this == obj) {
            return true;
        }
        else if (!(obj instanceof FixedSizeFIFOWorkList<?>)) {
            return false;
        }
        else {
            FixedSizeFIFOWorkList<E> other = (FixedSizeFIFOWorkList<E>) obj;
            if(this.size() != other.size()) {
                return  false;
            } else {
                for(int index = 0; index < this.size(); index++) {
                    if(this.peek(index) != other.peek(index)) {
                        return false;
                    }
                }
            }
            return true;
        }
    }

    @Override

    public int hashCode() {
        // You will implement this method in project 2. Leave this method unchanged for project 1.
        int result = 1;
        // {11, 17, 19,  31, 61, 151, 359, 701, 1481, 2851, 6073, 12437, 24029 , 48049, 94117, 211859};
        int[] primes ={11, 359};
        for (int index = 0; index < this.arr.length; index++) {
            if (this.arr[index] != null) {
                result = result * primes[0] + this.arr[index].hashCode() * primes[1];
            }
        }
        return result;
    }

    // hashCode for experiment
    // make sure to comment this out unless you are running experiment
    /*
    public int hashCode() {
        int sum = 0;
        if (this.arr[0] != null) {
            sum += this.arr[0].hashCode();
        }
        E lastElement = this.arr[this.arr.length - 1];
        if (lastElement != null) {
            sum += lastElement.hashCode();
        }
        return sum;
    }
     */
}
