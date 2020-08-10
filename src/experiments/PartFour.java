package experiments;

import cse332.interfaces.misc.Dictionary;
import cse332.types.AlphabeticString;
import datastructures.dictionaries.ChainingHashTable;
import datastructures.dictionaries.MoveToFrontList;

import java.io.File;
import java.util.Scanner;

public class PartFour {
    public static void main(String[] args) throws Exception{
        File alice = new File("alice.txt");
        ChainingHashTable<AlphabeticString, Integer> hashTable = new ChainingHashTable<>(MoveToFrontList::new);
        insertWords(alice, hashTable);
        // I changed the hash code for the CircularFIFOQueue directly in its
        // folder under datastructures.worklist.CircularArrayFIFOQueue
        // The bad hash code was only hashing the first and last elements in the array and summing the hash together
    }

    public static void insertWords(File alice, Dictionary<AlphabeticString, Integer> dictionary) throws Exception {
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
