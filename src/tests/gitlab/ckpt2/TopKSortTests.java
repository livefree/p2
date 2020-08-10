package tests.gitlab.ckpt2;

import java.util.Comparator;

import p2.sorts.TopKSort;
import org.junit.Test;
import static org.junit.Assert.*;

public class TopKSortTests {

	@Test(timeout = 3000)
	public void integer_sorted() {
		int K = 4;
		Integer[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
		Integer[] arr_sorted = {7, 8, 9, 10};
		TopKSort.sort(arr, K, Integer::compareTo);
		for(int i = 0; i < K; i++) {
			assertEquals(arr[i], arr_sorted[i]);
		}
	}

	@Test(timeout = 3000)
	public void integer_random() {
		int K = 4;
		Integer[] arr = {3, 1, 4, 5, 9, 2, 6, 7, 8};
		Integer[] arr_sorted = {6, 7, 8, 9};
		TopKSort.sort(arr, K, Integer::compareTo);
		for(int i = 0; i < K; i++) {
			assertEquals(arr[i], arr_sorted[i]);
		}
	}
}