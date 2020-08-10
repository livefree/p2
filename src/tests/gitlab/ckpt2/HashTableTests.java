package tests.gitlab.ckpt2;

import cse332.datastructures.containers.Item;
import cse332.interfaces.misc.Dictionary;
import datastructures.dictionaries.ChainingHashTable;
import datastructures.dictionaries.MoveToFrontList;
import org.junit.Test;
import static org.junit.Assert.*;


public class HashTableTests {

	private void incCount(Dictionary<String, Integer> list, String key) {
		Integer find = list.find(key);
		if (find == null)
			list.insert(key, 1);
		else
			list.insert(key, 1 + find);
	}

	@Test(timeout = 3000)
	public void testHugeHashTable() {
		ChainingHashTable<String, Integer> list = new ChainingHashTable<>(MoveToFrontList::new);

		int n = 1000;

		// Add them
		for (int i = 0; i < 5 * n; i++) {
			int k = (i % n) * 37 % n;
			String str = String.format("%05d", k);
			for (int j = 0; j < k + 1; j ++)
				incCount(list, str);
		}

		// Delete them all
		int totalCount = 0;
		for (Item<String, Integer> dc : list) {
			assertEquals((Integer.parseInt(dc.key) + 1) * 5, (int) dc.value);
			totalCount += dc.value;
		}
		assertEquals(totalCount, (n * (n + 1)) / 2 * 5);
		assertEquals(list.size(), n);
		assertNotNull(list.find("00851"));
		assertEquals(4260, (int) list.find("00851"));
	}
}
