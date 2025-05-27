package dev.xxdb.index.btree;

import dev.xxdb.index.btree.BPlusTreeInnerNode.Entries;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DummyInnerNode implements BPlusTreeInnerNode<Integer, Integer> {
  private final int min;
  private final int max;

  public DummyInnerNode(int min, int max) {
    this.min = min;
    this.max = max;
  }

  public int getMin() {
    return min;
  }

  public int getMax() {
    return max;
  }

  @Override
  public Entries<Integer, Integer> getEntries() {
    return null;
  }

  @Override
  public boolean isFull() {
    return false;
  }
}

class BPlusTreeInnerNodeTest {
  @Nested
  class EntriesTest {
    private final List<Integer> keys = new ArrayList<>(List.of(1, 3, 4, 8));
    private final List<BPlusTreeNode<Integer, Integer>> children = new ArrayList<>(List.of(
        new DummyInnerNode(0, 0),
        new DummyInnerNode(1, 2),
        new DummyInnerNode(3, 3),
        new DummyInnerNode(4, 5),
        new DummyInnerNode(8, 10)
    ));
    // Testing strategy
    // + findChild()
    //    + partition on appearance of the key on key-list: has the key, doesn't have the key
    //    + partition on key and key list: less than all, in middle, greater than all
    //
    // + insertWithRightChild()
    //    + partition on key and key list: less than all, in middle, greater than all


    // cover the key list has the key to query
    @Test
    void findChildHasTheKeyTest() {
      Entries<Integer, Integer> entries = new Entries<>(keys, children);
      DummyInnerNode child = (DummyInnerNode) entries.findChild(3);
      assertEquals(3, child.getMin());
      assertEquals(3, child.getMax());
    }

    // cover the key list does not have the key to query, key is in the middle range of the key list
    @Test
    void findChildDoesNotHaveTheKeyTest() {
      Entries<Integer, Integer> entries = new Entries<>(keys, children);
      DummyInnerNode child = (DummyInnerNode) entries.findChild(5);
      assertEquals(4, child.getMin());
      assertEquals(5, child.getMax());
    }

    // cover the key is less than all keys in key list
    @Test
    void findChildKeyLessThanAll() {
      Entries<Integer, Integer> entries = new Entries<>(keys, children);
      DummyInnerNode child = (DummyInnerNode) entries.findChild(-1);
      assertEquals(0, child.getMin());
      assertEquals(0, child.getMax());
    }

    // cover the key is greater than all keys in key list
    @Test
    void findChildKeyGreaterThanAll() {
      Entries<Integer, Integer> entries = new Entries<>(keys, children);
      DummyInnerNode child = (DummyInnerNode) entries.findChild(10);
      assertEquals(8, child.getMin());
      assertEquals(10, child.getMax());
    }

    // cover the key is less than all keys in the list
    @Test
    void insertTestKeyLessThanAll() {
      Entries<Integer, Integer> entries = new Entries<>(keys, children);
      entries.insertWithRightChild(-2, new DummyInnerNode(-2, -1));

      DummyInnerNode child = (DummyInnerNode) entries.findChild(-3);
      assertEquals(-2, child.getMin());
      assertEquals(-1, child.getMax());
    }

    // cover the key is in middle range of the list
    @Test
    void insertTestKeyInMiddleRange() {
      Entries<Integer, Integer> entries = new Entries<>(keys, children);
      entries.insertWithRightChild(7, new DummyInnerNode(8, 9));

      DummyInnerNode child = (DummyInnerNode) entries.findChild(6);
      assertEquals(8, child.getMin());
      assertEquals(9, child.getMax());
    }

    // cover the key is greater than all keys in key list
    @Test
    void insertTestKeyGreaterThanAll() {
      Entries<Integer, Integer> entries = new Entries<>(keys, children);
      entries.insertWithRightChild(11, new DummyInnerNode(11, 20));

      DummyInnerNode child = (DummyInnerNode) entries.findChild(100);
      assertEquals(11, child.getMin());
      assertEquals(20, child.getMax());
    }
  }
}