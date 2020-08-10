package tests.gitlab.ckpt2;

import cse332.datastructures.containers.Item;
import cse332.datastructures.trees.BinarySearchTree.BSTNode;
import cse332.interfaces.misc.Dictionary;
import datastructures.dictionaries.AVLTree;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import org.junit.Test;
import static org.junit.Assert.*;

public class AVLTreeTests {

	private AVLTree<String, Integer> init() {
		return new AVLTree<>();
	}

	private <E extends Comparable<E>> void incCount(Dictionary<E, Integer> tree, E key) {
		Integer value = tree.find(key);
		if (value == null) {
			tree.insert(key, 1);
		} else {
			tree.insert(key, value + 1);
		}
	}

	@SuppressWarnings("rawtypes")
	@Test(timeout = 3000)
	public void checkStructure() {
		AVLTree<Integer, Integer> tree = new AVLTree<>();
		incCount(tree, 10);
		incCount(tree, 14);
		incCount(tree, 10);
		incCount(tree, 31);
		incCount(tree, 10);
		incCount(tree, 13);
		incCount(tree, 10);
		incCount(tree, 10);
		incCount(tree, 12);
		incCount(tree, 10);
		incCount(tree, 13);
		incCount(tree, 10);
		incCount(tree, 10);
		incCount(tree, 11);
		incCount(tree, 10);
		incCount(tree, 14);
		incCount(tree, 9);
		incCount(tree, 8);
		incCount(tree, 7);
		incCount(tree, 6);
		incCount(tree, 5);
		incCount(tree, 4);
		incCount(tree, 3);
		incCount(tree, 2);
		incCount(tree, 1);
		incCount(tree, 0);
//		{10, 14, 10, 31, 10, 13, 10, 10, 12, 10, 13, 10, 10, 11, 10, 14, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0}
//		{10, 14, 31, 13, 12, 11, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0}

		BSTNode root = (BSTNode) getField(tree, "root");

		String trueData = " [8 [4 [2 [1 [0..].] [3..]] [6 [5..] [7..]]] [12 [10 [9..] [11..]] [14 [13..] [31..]]]]";
		String trueCounts = " [1 [1 [1 [1 [1..].] [1..]] [1 [1..] [1..]]] [1 [9 [1..] [1..]] [2 [2..] [1..]]]]";
//		String trueData = " [10 [6 [2 [1 [0..].] [4 [3..] [5..]]] [8 [7..] [9..]]] [13 [12 [11..].] [14. [31..]]]]";
//		String trueCounts = " [9 [1 [1 [1 [1..].] [1 [1..] [1..]]] [1 [1..] [1..]]] [2 [1 [1..].] [2. [1..]]]]";

//		System.err.println(nestd(root));
//		System.err.println(trueData);
		assertEquals(nestd(root), trueData);
		assertEquals(nestc(root), trueCounts);
	}

	@SuppressWarnings("rawtypes")
	public String nestd(BSTNode root) {
		if(root == null)
			return ".";
		return " [" + root.key + nestd(root.children[0]) + nestd(root.children[1]) + "]";
	}
	@SuppressWarnings("rawtypes")
	public String nestc(BSTNode root) {
		if(root == null)
			return ".";
		return " [" + root.value + nestc(root.children[0]) + nestc(root.children[1]) + "]";
	}

	@Test(timeout = 3000)
	public void testTreeWith5Items() {
		AVLTree<String, Integer> tree = init();
		String[] tests_struct = { "a", "b", "c", "d", "e" };
		String[] tests = { "b", "d", "e", "c", "a" };
		for (int i = 0; i < 5; i++) {
			String str = tests[i] + "a";
			incCount(tree, str);
		}

		int i = 0;
		for (Item<String, Integer> item : tree) {
			String str_heap = item.key;
			String str = tests_struct[i] + "a";
			assertEquals(str, str_heap);
			i++;
		}
	}

	@Test(timeout = 3000)
	public void testHugeTree() {
		AVLTree<String, Integer> tree = init();
		int n = 1000;

		// Add them
		for (int i = 0; i < 5 * n; i++) {
			int k = (i % n) * 37 % n;
			String str = String.format("%05d", k);
			for (int j = 0; j < k + 1; j ++)
				incCount(tree, str);
		}

		// Delete them all
		int totalCount = 0;
		for (Item<String, Integer> dc : tree) {
			assertEquals((Integer.parseInt(dc.key) + 1) * 5, dc.value.intValue());
			totalCount += dc.value;
		}

		// Check for accuracy
		assertEquals(totalCount, (n * (n + 1)) / 2 * 5);
		assertEquals(tree.size(), n);
		assertNotNull(tree.find("00851"));
		assertEquals(4260, (int) tree.find("00851"));
	}

	/**
	 * Get a field from an object
	 * @param o Object you want to get the field from
	 * @param fieldName Name of the field
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private <T> T getField(Object o, String fieldName) {
		try {
			Field field = o.getClass().getSuperclass().getDeclaredField(fieldName);
			field.setAccessible(true);
			Object f = field.get(o);
			return (T) f;
		} catch (Exception e) {
			try {
				Field field = o.getClass().getDeclaredField(fieldName);
				field.setAccessible(true);
				Object f = field.get(o);
				return (T) f;
			} catch (Exception e2) {
				return null;
			}
		}
	}

}
