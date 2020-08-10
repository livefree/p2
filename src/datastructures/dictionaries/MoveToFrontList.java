package datastructures.dictionaries;

import java.util.Iterator;
import java.util.NoSuchElementException;

import cse332.datastructures.containers.Item;
import cse332.interfaces.misc.DeletelessDictionary;

/**
 * 1. The list is typically not sorted.
 * 2. Add new items to the front of the list.
 * 3. Whenever find is called on an item, move it to the front of the
 * list. This means you remove the node from its current position
 * and make it the first node in the list.
 * 4. You need to implement an iterator. The iterator SHOULD NOT move
 * elements to the front.  The iterator should return elements in
 * the order they are stored in the list, starting with the first
 * element in the list. When implementing your iterator, you should
 * NOT copy every item to another dictionary/list and return that
 * dictionary/list's iterator.
 */
public class MoveToFrontList<K, V> extends DeletelessDictionary<K, V> {
    private MTFListNode<K, V> frontNode;

    public MoveToFrontList() {
        frontNode = null;
    }

    @Override
    // inserts the key and value pair into the list
    // returns the old value if there was already a key value pair with the same key
    // otherwise return null
    public V insert(K key, V value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException();
        }
        MTFListNode<K, V> oldNode = removeKey(key);
        MTFListNode<K, V> insertNode = new MTFListNode<>(key, value);
        if (frontNode != null) {
            insertNode.next = frontNode;
            frontNode.prev = insertNode;
        }
        frontNode = insertNode;
        if (oldNode == null) {
            size++;
            return null;
        } else {
            return oldNode.item.value;
        }
    }

    @Override
    // finds the given key in the list and returns the value associated with the key
    // returns null otherwise
    // Note: the node containing the key will be moved to the front of the list
    public V find(K key) {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        MTFListNode<K, V> matchNode = removeKey(key);
        if (matchNode == null) {
            return null;
        }
        else if (frontNode == null) {
            frontNode = matchNode;
        } else {
            matchNode.prev = null;
            matchNode.next = frontNode;
            frontNode.prev = matchNode;
            frontNode = matchNode;
        }
        return matchNode.item.value;
    }

    // removes the node that contains the given key from the list
    // returns the node if the key is found, otherwise return null
    private MTFListNode<K, V> removeKey(K key) {
        MTFListNode<K, V> current = frontNode;
        while (current != null) {
            Item<K, V> curItem = current.item;
            if (curItem.key.equals(key)) {
                if (current.prev == null) { // front node case
                    if (current.next != null) {
                        current.next.prev = null;
                    }
                    frontNode = frontNode.next;
                } else if (current.next == null) { // last node case
                    current.prev.next = null;
                } else {
                    current.prev.next = current.next;
                    current.next.prev = current.prev;
                }
                return current;
            }
            current = current.next;
        }
        return null;
    }

    @Override
    public Iterator<Item<K, V>> iterator() {
        return new MTFListIterator();
    }

    private class MTFListIterator implements Iterator<Item<K, V>> {
        private MTFListNode<K, V> current;

        public MTFListIterator() {
            current = frontNode;
        }

        public boolean hasNext() {
            return current != null;
        }

        public Item<K, V> next() {
            if (current == null) {
                throw new NoSuchElementException();
            }
            Item<K, V> item = current.item;
            current = current.next;
            return item;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof MoveToFrontList)) {
            return false;
        }
        Iterator thisIterator = this.iterator();
        Iterator otherIterator = ((MoveToFrontList)obj).iterator();
        // compare each node from list
        while (thisIterator.hasNext() && otherIterator.hasNext()) {
            MTFListNode<K, V> thisNode = (MTFListNode<K, V>)thisIterator.next();
            MTFListNode<K, V> otherNode = (MTFListNode<K, V>)otherIterator.next();
            if(!thisNode.equals(otherNode)) {
                return false;
            }
        }
        // both list should reach end after loop
        if(thisIterator.hasNext() || otherIterator.hasNext()){
            return false;
        }
        return true;
    }

    public class MTFListNode<K, V> {
        public Item<K, V> item;
        public MTFListNode<K, V> next;
        public MTFListNode<K, V> prev;

        public MTFListNode(K key, V value) {
            this.item = new Item<>(key, value);
            next = null;
            prev = null;
        }
        @Override
        public boolean equals(Object obj) {
            if(!(obj instanceof MTFListNode)) {
                return false;
            }
            if(!(this).item.equals(((MTFListNode)obj).item)){
                return false;
            }
            if(this.next == null) {
                if(((MTFListNode)obj).next != null) {
                    return false;
                }
            } else {
                if(!this.next.equals(((MTFListNode)obj))) {
                    return false;
                }
            }
            if(this.prev == null) {
                if(((MTFListNode)obj).prev != null) {
                    return false;
                }
            } else {
                if(!this.prev.equals(((MTFListNode)obj))) {
                    return false;
                }
            }
            return true;
        }
    }
}
