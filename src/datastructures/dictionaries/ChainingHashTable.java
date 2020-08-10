package datastructures.dictionaries;

import java.util.Iterator;
import java.util.function.Supplier;

import cse332.datastructures.containers.Item;
import cse332.interfaces.misc.DeletelessDictionary;
import cse332.interfaces.misc.Dictionary;

/**
 * 1. You must implement a generic chaining hashtable. You may not
 * restrict the size of the input domain (i.e., it must accept
 * any key) or the number of inputs (i.e., it must grow as necessary).
 * 3. Your HashTable should rehash as appropriate (use load factor as
 * shown in class!).
 * 5. HashTable should be able to resize its capacity to prime numbers for more
 * than 200,000 elements. After more than 200,000 elements, it should
 * continue to resize using some other mechanism.
 * 6. We suggest you hard code some prime numbers. You can use this
 * list: http://primes.utm.edu/lists/small/100000.txt
 * NOTE: Do NOT copy the whole list!
 * 7. When implementing your iterator, you should NOT copy every item to another
 * dictionary/list and return that dictionary/list's iterator.
 */
public class ChainingHashTable<K, V> extends DeletelessDictionary<K, V> {
    private Supplier<Dictionary<K, V>> newChain;
    private DeletelessDictionary<K, V>[] hashTable; // list of chains
    private int size; // number of elements
    private static final double LOAD_FACTOR = 0.7; //number of elements / table size

    // hard coded prime numbers
    private final int[] primes = {13, 23, 41, 83, 163, 331, 641, 1283, 2579, 5147, 10243, 20483, 40961, 81929, 163841};
    private int primeIndex;

    public ChainingHashTable(Supplier<Dictionary<K, V>> newChain) {
        this.newChain = newChain;
        primeIndex = 0;
        this.hashTable = new DeletelessDictionary[primes[primeIndex]];
        this.size = 0;
    }

    public int size() {
        return this.size;
    }

    /**
     * Associates the specified value with the specified key in this map. If the
     * map previously contained a mapping for the key, the old value is
     * replaced.
     *
     * @param key   key with which the specified value is to be associated
     * @param value value to be associated with the specified key
     * @return the previous value associated with <tt>key</tt>, or <tt>null</tt>
     * if there was no mapping for <tt>key</tt>.
     * @throws IllegalArgumentException if either key or value is null.
     */
    @Override
    public V insert(K key, V value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException();
        }
        // check load factor
        resize();
        int index = getIndex(key, hashTable.length);
        if (this.hashTable[index] == null) {
            this.hashTable[index] = (DeletelessDictionary<K, V>) this.newChain.get();
        }
        // record current chain size
        int chainSize = hashTable[index].size();
        // insert item to current chain dictionary
        V oldValue = this.hashTable[index].insert(key, value);
        // do not increase size if acturally update
        if (chainSize != this.hashTable[index].size()) {
            this.size++;
        }
        return oldValue;
    }

    /**
     * Expand table size once lambda exceed load factor.
     */
    private void resize() {
        if ((double) (size) / hashTable.length < LOAD_FACTOR) {
            return;
        }
        DeletelessDictionary[] newTable;
        int tableSize = hashTable.length;
        // determining new size
        if (primeIndex < primes.length - 1) {
            primeIndex++;
            tableSize = primes[primeIndex];
        } else {
            tableSize = tableSize * 2 + 1; // simple expand for size above prime list
        }

        newTable = new DeletelessDictionary[tableSize];
        // iterate items and transfer to new table
        Iterator<Item<K, V>> itr = this.iterator();
        while (itr.hasNext()) {
            Item<K, V> item = itr.next();
            int index = getIndex(item.key, newTable.length);
            if (newTable[index] == null) {
                newTable[index] = (DeletelessDictionary<K, V>) newChain.get();
            }
            newTable[index].insert(item.key, item.value); //copy item to new table
        }
        this.hashTable = newTable;
    }

    /**
     * A hash function that hash key to an integer
     *
     * @param key the key to be hashed
     * @return a integer hash number
     */
    private int getIndex(K key, int length) {
        return (Math.abs(key.hashCode() * 7)) % length;
    }

    @Override
    public V find(K key) {
        int index = getIndex(key, hashTable.length);
        if (this.hashTable[index] == null) {
            return null;
        }
        return this.hashTable[index].find(key);
    }

    @Override
    public Iterator<Item<K, V>> iterator() {
        return new CHTIterator();
    }
    private class CHTIterator implements  Iterator<Item<K, V>> {
        private Iterator<Item<K, V>> chainItr; // an iterator to separeate chain
        private int tableIndex; // index where current iterator in hash table
        public CHTIterator() {
            tableIndex = 0;
            if (ChainingHashTable.this.size() == 0) {
                chainItr = null;
            } else {
                // find first item
                while (tableIndex < ChainingHashTable.this.hashTable.length) {
                    if (ChainingHashTable.this.hashTable[tableIndex] != null) {
                        chainItr = ChainingHashTable.this.hashTable[tableIndex].iterator();
                        break;
                    }
                    tableIndex++;
                }
                if (tableIndex >= ChainingHashTable.this.hashTable.length) {
                    chainItr = null;
                }
            }
        }

    /**
     *
     * @return return next item if present. Otherwise return null.
     */
    @Override
        public Item<K, V> next() {
            if (chainItr.hasNext()) {
                return chainItr.next();
            }
            return null;
        }

    /**
     * Find if there is next item in hash table. If reach end of current chain, find out if
     * next chain has item.
     * @return return true next item present. otherwise return false
     */
    @Override
        public boolean hasNext() {
            if (chainItr!= null && chainItr.hasNext()) {
                return true;
            } else {
                // look up the rest in hash table
                while (tableIndex < ChainingHashTable.this.hashTable.length -1) {
                    tableIndex++;
                    if (ChainingHashTable.this.hashTable[tableIndex] != null) {
                        chainItr = ChainingHashTable.this.hashTable[tableIndex].iterator();
                        return chainItr.hasNext();
                    }
                }
                return false;
            }
        }
    }
}

