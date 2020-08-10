package tests.gitlab.ckpt1;

import cse332.interfaces.worklists.PriorityWorkList;
import datastructures.worklists.MinFourHeap;
import org.junit.Before;
import org.junit.Test;
import tests.gitlab.ckpt1.WorklistGradingTests;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;

import static org.junit.Assert.*;

public class MinFourHeapTests extends WorklistGradingTests {
    private static Random RAND;

    @Before
    public void init() {
        STUDENT_STR = new MinFourHeap<>(String::compareTo);
        STUDENT_DOUBLE = new MinFourHeap<>(Double::compareTo);
        STUDENT_INT = new MinFourHeap<>(Integer::compareTo);
        RAND = new Random(42);
    }

    @Test(timeout = 3000)
    public void testHeapWith5Items() {
        PriorityWorkList<String> heap = new MinFourHeap<>(String::compareTo);
        String[] tests = { "a", "b", "c", "d", "e" };
        for (int i = 0; i < 5; i++) {
            String str = tests[i] + "a";
            heap.add(str);
        }

        for (int i = 0; i < 5; i++) {
            String str_heap = heap.next();
            String str = (char) ('a' + i) + "a";
            assertEquals(str, str_heap);
        }
    }

    @Test(timeout = 3000)
    public void testOrderingDoesNotMatter() {
        PriorityWorkList<String> ordered = new MinFourHeap<>(String::compareTo);
        PriorityWorkList<String> reversed = new MinFourHeap<>(String::compareTo);
        PriorityWorkList<String> random = new MinFourHeap<>(String::compareTo);

        addAll(ordered, new String[]{"a", "b", "c", "d", "e"});
        addAll(reversed, new String[]{"e", "d", "c", "b", "a"});
        addAll(random, new String[]{"d", "b", "c", "e", "a"});

        assertTrue(isSame("a", ordered.peek(), reversed.peek(), random.peek()));
        assertTrue(isSame("a", ordered.next(), reversed.next(), random.next()));
        assertTrue(isSame("b", ordered.next(), reversed.next(), random.next()));

        addAll(ordered, new String[] {"a", "a", "b", "c", "z"});
        addAll(reversed, new String[] {"z", "c", "b", "a", "a"});
        addAll(random, new String[] {"c", "z", "a", "b", "a"});

        String[] expected = new String[] {"a", "a", "b", "c", "c", "d", "e", "z"};
        for (String e : expected) {
            assertTrue(isSame(e, ordered.peek(), reversed.peek(), random.peek()));
            assertTrue(isSame(e, ordered.next(), reversed.next(), random.next()));
        }
    }

    private boolean isSame(String... args) {
        String first = args[0];
        for (String arg : args) {
            if (!first.equals(arg)) {
                return false;
            }
        }
        return true;
    }

    @Test(timeout = 3000)
    public void testHugeHeap() {
        PriorityWorkList<String> heap = new MinFourHeap<>(String::compareTo);
        int n = 10000;

        // Add them
        for (int i = 0; i < n; i++) {
            String str = String.format("%05d", i * 37 % n);
            heap.add(str);
        }
        // Delete them all
        for (int i = 0; i < n; i++) {
            String s = heap.next();
            assertEquals(i , Integer.parseInt(s));
        }
    }

    @Test(timeout = 3000)
    public void testWithCustomComparable() {
        PriorityWorkList<Coordinate> student = new MinFourHeap<>(Coordinate::compareTo);
        Queue<Coordinate> reference = new PriorityQueue<>();

        for (int i = 0; i < 10000; i++) {
            Coordinate coord = new Coordinate(RAND.nextInt(10000) - 5000, RAND.nextInt(10000) - 5000);
            student.add(coord);
            reference.add(coord);
        }
        assertEquals(reference.size(), student.size());

        while (!reference.isEmpty()) {
            assertEquals(reference.peek() , student.peek());
            assertEquals(reference.remove() , student.next());
        }
    }

    public static class Coordinate implements Comparable<Coordinate> {
        private int x;
        private int y;

        public Coordinate(int x, int y) {
            this.x = x;
            this.y = y;
        }

        // What exactly this comparable method is doing is somewhat arbitrary.
        public int compareTo(Coordinate other) {
            if (this.x != other.x) {
                return this.x - other.x;
            } else {
                return this.y - other.y;
            }
        }
    }

    @Test(timeout = 3000)
    public void checkStructure() {
        PriorityWorkList<Integer> heap = new MinFourHeap<>(Integer::compareTo);
        addAll(heap, new Integer[] {10, 10, 15, 1, 17, 16, 100, 101, 102, 103, 105, 106, 107, 108});

        Object[] heapData = getField(heap, "data");
        String heapStr = Arrays.toString(heapData);
        String heapExp = "[1, 10, 15, 10, 17, 16, 100, 101, 102, 103, 105, 106, 107, 108";

        heap.next();
        heap.next();
        heap.next();

        Object[] heapData2 = getField(heap, "data");
        String heapStr2 = Arrays.toString(heapData2);
        String heapExp2 = "[15, 16, 103, 107, 17, 108, 100, 101, 102, 106, 105,";

        assertTrue(heapStr.contains(heapExp));
        assertTrue(heapStr2.contains(heapExp2));
    }

    protected <T> T getField(Object o, String fieldName) {
        try {
            Field field = o.getClass().getSuperclass().getDeclaredField(fieldName);
            field.setAccessible(true);
            Object f = field.get(o);
            return (T) f;
        } catch (Exception var6) {
            try {
                Field field = o.getClass().getDeclaredField(fieldName);
                field.setAccessible(true);
                Object f = field.get(o);
                return (T) f;
            } catch (Exception var5) {
                return null;
            }
        }
    }
}
