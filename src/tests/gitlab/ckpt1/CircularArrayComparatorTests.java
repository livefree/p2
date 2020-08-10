package tests.gitlab.ckpt1;

import datastructures.worklists.CircularArrayFIFOQueue;
import org.junit.Test;

import static org.junit.Assert.*;

public class CircularArrayComparatorTests {

    private CircularArrayFIFOQueue<String> init() {
        return new CircularArrayFIFOQueue<String>(10);
    }

    private boolean result(int a, int b) {
        return Integer.signum(a) == Integer.signum(b);
    }

    @Test(timeout = 3000)
    public void test_empty_empty() {
        CircularArrayFIFOQueue<String> l1 = init();
        CircularArrayFIFOQueue<String> l2 = init();
        assertTrue(result(l1.compareTo(l2), "".compareTo("")));
    }

    @Test(timeout = 3000)
    public void test_ab_ab() {
        CircularArrayFIFOQueue<String> l1 = init();
        CircularArrayFIFOQueue<String> l2 = init();
        l1.add("a");
        l1.add("b");
        l2.add("a");
        l2.add("b");
        assertTrue(result(l1.compareTo(l2), "ab".compareTo("ab")));
    }

    @Test(timeout = 3000)
    public void test_ab_abc() {
        CircularArrayFIFOQueue<String> l1 = init();
        CircularArrayFIFOQueue<String> l2 = init();
        l1.add("a");
        l1.add("b");
        l2.add("a");
        l2.add("b");
        l2.add("c");
        assertTrue(result(l1.compareTo(l2), "ab".compareTo("abc")));
    }

    @Test(timeout = 3000)
    public void test_abc_ab() {
        CircularArrayFIFOQueue<String> l1 = init();
        CircularArrayFIFOQueue<String> l2 = init();
        l1.add("a");
        l1.add("b");
        l1.add("c");
        l2.add("a");
        l2.add("b");
        assertTrue(result(l1.compareTo(l2), "abc".compareTo("ab")));
    }

    @Test(timeout = 3000)
    public void test_ac_abc() {
        CircularArrayFIFOQueue<String> l1 = init();
        CircularArrayFIFOQueue<String> l2 = init();
        l1.add("a");
        l1.add("c");
        l2.add("a");
        l2.add("b");
        l2.add("c");
        assertTrue(result(l1.compareTo(l2), "ac".compareTo("abc")));
    }

    @Test(timeout = 3000)
    public void test_a_aa() {
        CircularArrayFIFOQueue<String> l1 = init();
        CircularArrayFIFOQueue<String> l2 = init();
        l1.add("a");
        l2.add("a");
        l2.add("a");
        assertTrue(result(l1.compareTo(l2), "a".compareTo("aa")));
    }

    @Test(timeout = 3000)
    public void test_compare_transitive() {
        CircularArrayFIFOQueue<String> l1 = init();
        CircularArrayFIFOQueue<String> l2 = init();
        CircularArrayFIFOQueue<String> l3 = init();

        l1.add("abc");
        l2.add("def");
        l3.add("efg");
        assertTrue(l1.compareTo(l2) < 0 && l2.compareTo(l3) < 0 && l1.compareTo(l3) < 0) ;
    }

    @Test(timeout = 3000)
    public void test_equality_consistent_with_compare() {
        CircularArrayFIFOQueue<String> l1 = init();
        CircularArrayFIFOQueue<String> l2 = init();
        l1.add("a");
        l1.add("b");
        l2.add("a");
        l2.add("b");
        assertEquals(l1, l2);
        assertEquals(l1.compareTo(l2), 0);
    }

    @Test(timeout = 3000)
    public void test_equals_doesnt_modify() {
        CircularArrayFIFOQueue<String> l1 = init();
        CircularArrayFIFOQueue<String> l2 = init();
        l1.add("a");
        l2.add("a");
        l1.equals(l2);
        assertEquals(l1.size(), 1);
    }
}
