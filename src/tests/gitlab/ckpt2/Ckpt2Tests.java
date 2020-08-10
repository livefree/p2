package tests.gitlab.ckpt2;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)

@Suite.SuiteClasses({
        AVLTreeTests.class,
        HashTableTests.class,
        CircularArrayHashCodeTests.class,
        QuickSortTests.class,
        TopKSortTests.class,
        HeapSortTests.class,
        HashTrieMapTests.class
})

public class Ckpt2Tests {

}
