package experiments;

import datastructures.dictionaries.ChainingHashTable;
import cse332.datastructures.trees.BinarySearchTree;
import datastructures.dictionaries.AVLTree;
import datastructures.dictionaries.MoveToFrontList;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Part3Test {
    /*
    Your ChainingHashTable should take as an argument to its constructor
    the type of "chains" it uses. Determine which type of chain is
    (on average, not worst case) best: an MTFList, a BST, or an AVL Tree.
    If it doesn’t make a difference, explain why.
    Explain your intuition on why the answer you got makes sense (or doesn’t!).
    */

    public static ArrayList<Integer> list;
    public static Random rand;
    public static void initList(long size) {
        list = new ArrayList<Integer>();
        rand = new Random();
        while(size-- > 0) {
//            list.add((int)size);
            list.add(rand.nextInt());
//            list.add(Integer.format("%5d",rand.nextInt()));
        }
    }
    private static void insert(ChainingHashTable cht){

        int size = list.size();
        for(int i = 0; i<size; i++) {
            cht.insert(list.get(i),list.get(i));
        }
    }
    private static void find(ChainingHashTable cht) {
        int size = list.size();
        Collections.shuffle(list);
        for(int i = 0; i<size; i++) {
            cht.find(list.get(i));
        }
    }
    public static void main(String[] args) throws IOException {
        int times = 11;
        long[] time = new long[3];
        long[] average = new long[3];
        ChainingHashTable<Integer, Integer> mtfCHT = new ChainingHashTable<>(MoveToFrontList::new);
        ChainingHashTable<Integer, Integer> bstCHT = new ChainingHashTable<>(BinarySearchTree::new);
        ChainingHashTable<Integer, Integer> avlCHT = new ChainingHashTable<>(AVLTree::new);
        var file = new FileWriter(System.getProperty("user.dir")+"/src/experiments/part3.txt");
        file.write(String.format("n, trils, MTF, BST, AVL\n"));
        for (int test = 0; test < times; test++) {
            long n = (long) (1000 * Math.pow(2, test));
            // insert
            for(int i = 0; i < 10; i++) {
                mtfCHT = new ChainingHashTable<>(MoveToFrontList::new);
                bstCHT = new ChainingHashTable<>(BinarySearchTree::new);
                avlCHT = new ChainingHashTable<>(AVLTree::new);

                initList(n);
                // move to front
                long start = System.currentTimeMillis();
                insert(mtfCHT);
                long end = System.currentTimeMillis();
                time[0] = end - start;
                average[0] += time[0];
                // binary search tree
                start = System.currentTimeMillis();
                insert(bstCHT);
                end = System.currentTimeMillis();
                time[1] = end - start;
                average[1] += time[1];
                // balanced tree
                start = System.currentTimeMillis();
                insert(avlCHT);
                end = System.currentTimeMillis();
                time[2] = end - start;
                average[2] += time[2];
                file.write(String.format("%d, %d, %d, %d, %d\n",n, i, time[0],time[1],time[2]));
            }
            file.write(String.format("%d, %s, %d, %d, %d\n\n",n, "avg", average[0]/10,average[1]/10,average[2]/10));
            // search
            for(int i = 0; i < 10; i++) {
                long start = System.currentTimeMillis();
                find(mtfCHT);
                long end = System.currentTimeMillis();
                time[0] = end - start;
                average[0] += time[0];
                // binary search tree
                start = System.currentTimeMillis();
                find(bstCHT);
                end = System.currentTimeMillis();
                time[1] = end - start;
                average[1] += time[1];
                // balanced tree
                start = System.currentTimeMillis();
                find(avlCHT);
                end = System.currentTimeMillis();
                time[2] = end - start;
                average[2] += time[2];
                file.write(String.format("%d, ,%d, %d, %d\n",n, time[0],time[1],time[2]));
            }
        }
        file.close();
    }
}
