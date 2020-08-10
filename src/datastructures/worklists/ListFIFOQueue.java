package datastructures.worklists;

import cse332.interfaces.worklists.FIFOWorkList;
import java.util.NoSuchElementException;

/**
 * See cse332/interfaces/worklists/FIFOWorkList.java
 * for method specifications.
 */
public class ListFIFOQueue<E> extends FIFOWorkList<E> {

    private int size;
    private Node<E> front;
    private Node<E> back;
    
    public ListFIFOQueue() {
        size = 0;
        front = null;
        back = null;
    }

    @Override
    public void add(E work) {
        Node<E> w = new Node<E>(work);
        if (size == 0){
            front = w;
            back = w;
        } else {
            back.next = w;
            back = w;
        }
        size++;
    }

    @Override
    public E peek() {
        if (!hasWork()){
            throw new NoSuchElementException();
        }
        return front.work;
    }

    @Override
    public E next() {
        if (!hasWork()){
            throw new NoSuchElementException();
        }
        E w = front.work;
        front = front.next;
        size--;
        return w;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        back = null;
        front = null;
        size = 0;
    }

    public static class Node<E> {
        public E work;
        public Node<E> next;

        public Node(E work){
            this.work = work;
        }
    }
}
