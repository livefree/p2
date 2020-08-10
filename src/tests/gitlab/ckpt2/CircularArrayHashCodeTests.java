package tests.gitlab.ckpt2;

import datastructures.worklists.CircularArrayFIFOQueue;
import org.junit.Test;
import static org.junit.Assert.*;

public class CircularArrayHashCodeTests {

	private CircularArrayFIFOQueue<String> init() {
		return new CircularArrayFIFOQueue<String>(10);
	}

	@Test(timeout = 3000)
	public void equality() {
		CircularArrayFIFOQueue<String> l1 = init();
		CircularArrayFIFOQueue<String> l2 = init();
		for (int i = 0; i < 3; i++) {
			l1.add("a");
			l2.add("a");
		}
		assertEquals(l1.hashCode(), l2.hashCode());
	}

	@Test(timeout = 3000)
	public void ineq1() {
		CircularArrayFIFOQueue<String> l1 = init();
		CircularArrayFIFOQueue<String> l2 = init();
		l1.add("a");
		l1.add("a");
		l1.add("b");
		l2.add("a");
		l2.add("a");
		l2.add("a");
		assertNotEquals(l1.hashCode(), l2.hashCode());
	}

	@Test(timeout = 3000)
	public void ineq2() {
		CircularArrayFIFOQueue<String> l1 = init();
		CircularArrayFIFOQueue<String> l2 = init();
		l1.add("a");
		l1.add("a");
		l1.add("a");
		l1.add("a");
		l2.add("a");
		l2.add("a");
		l2.add("a");
		assertNotEquals(l1.hashCode() , l2.hashCode());
	}

	@Test(timeout = 3000)
	public void ineq3() {
		CircularArrayFIFOQueue<String> l1 = init();
		CircularArrayFIFOQueue<String> l2 = init();
		l1.add("a");
		l1.add("b");
		l1.add("c");
		l2.add("c");
		l2.add("b");
		l2.add("a");
		assertNotEquals(l1.hashCode() , l2.hashCode());
	}

	@Test(timeout = 3000)
	public void equality_consistent_with_hashcode() {
		CircularArrayFIFOQueue<String> l1 = init();
		CircularArrayFIFOQueue<String> l2 = init();
		l1.add("a");
		l1.add("b");
		l2.add("a");
		l2.add("b");
		assertEquals(l1 , l2);
		assertEquals(l1.hashCode() , l2.hashCode());
	}
}
