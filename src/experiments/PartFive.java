package experiments;

import cse332.datastructures.trees.BinarySearchTree;
import cse332.interfaces.misc.Dictionary;
import cse332.types.AlphabeticString;
import datastructures.dictionaries.AVLTree;
import datastructures.dictionaries.ChainingHashTable;
import datastructures.dictionaries.HashTrieMap;
import datastructures.dictionaries.MoveToFrontList;

import java.io.File;
import java.util.Scanner;

public class PartFive {
    public static void main(String[] args) throws Exception {
        File alice = new File("alice.txt");

        BinarySearchTree<String, Integer> bst = new BinarySearchTree<>();
        System.out.println("Binary Search Tree");
        insertWords(alice, bst);

        AVLTree<String, Integer> avl = new AVLTree<>();
        System.out.println("AVL");
        insertWords(alice, avl);

        ChainingHashTable<String, Integer> hashTable = new ChainingHashTable<>(MoveToFrontList::new);
        System.out.println("Chaining Hash Table");
        insertWords(alice, hashTable);

        HashTrieMap<Character, AlphabeticString, Integer> hashTrieMap = new HashTrieMap<>(AlphabeticString.class);
        System.out.println("Hash Trie Map");
        insertWordAlphabetic(alice, hashTrieMap);
    }

    public static void insertWords(File alice, Dictionary<String, Integer> dictionary) throws Exception {
        long totalTime = 0;
        for (int trials = 0; trials < 10; trials++) {
            Scanner fileScan = new Scanner(alice);
            long startTime = System.currentTimeMillis();
            while (fileScan.hasNextLine()) {
                String line = fileScan.nextLine();
                Scanner wordScan = new Scanner(line);
                while (wordScan.hasNext()) {
                    String word = wordScan.next();
                    Integer value = dictionary.find(word);
                    int count;
                    if (value != null) {
                        count = value;
                    } else {
                        count = 0;
                    }
                    dictionary.insert(word, count + 1);
                }
            }
            long endTime = System.currentTimeMillis();
            long timeDiff = endTime - startTime;
            totalTime += timeDiff;
            System.out.println(timeDiff);
        }
        System.out.println("Average time: " + (totalTime / 10));
        System.out.println();
    }

    public static void insertWordAlphabetic(File alice, HashTrieMap<Character, AlphabeticString, Integer> dictionary) throws Exception{
        long totalTime = 0;
        for (int trials = 0; trials < 10; trials++) {
            Scanner fileScan = new Scanner(alice);
            long startTime = System.currentTimeMillis();
            while (fileScan.hasNextLine()) {
                String line = fileScan.nextLine();
                Scanner wordScan = new Scanner(line);
                while (wordScan.hasNext()) {
                    String word = wordScan.next();
                    AlphabeticString alphaWord = new AlphabeticString(word);
                    Integer value = dictionary.find(alphaWord);
                    int count;
                    if (value != null) {
                        count = value;
                    } else {
                        count = 0;
                    }
                    dictionary.insert(alphaWord, count + 1);
                }
            }
            long endTime = System.currentTimeMillis();
            long timeDiff = endTime - startTime;
            totalTime += timeDiff;
            System.out.println(timeDiff);
        }
        System.out.println("Average time: " + (totalTime / 10));
        System.out.println();
    }
}
