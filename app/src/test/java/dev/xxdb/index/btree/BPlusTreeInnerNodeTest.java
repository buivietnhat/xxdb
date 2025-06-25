package dev.xxdb.index.btree;

import static org.junit.jupiter.api.Assertions.*;

import dev.xxdb.index.btree.BPlusTreeInnerNode.Entries;
import java.util.*;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class DummyInnerNode implements BPlusTreeInnerNode<Integer, Integer> {
  private final int min;
  private final int max;
  private Entries<Integer, Integer> entries;
  int fanout = 3;

  private Optional<BPlusTreeInnerNode<Integer, Integer>> leftSibling = Optional.empty();
  private Optional<BPlusTreeInnerNode<Integer, Integer>> rightSibling = Optional.empty();

  public DummyInnerNode(int min, int max) {
    this.min = min;
    this.max = max;
  }

  public DummyInnerNode(int fanout) {
    this.min = 0;
    this.max = 0;
    this.fanout = fanout;
  }

  public DummyInnerNode(Entries<Integer, Integer> entries, int fanout) {
    this.entries = entries;
    this.min = Collections.min(entries.keys());
    this.max = Collections.max(entries.keys());
    this.fanout = fanout;
  }

  public int getMin() {
    return min;
  }

  public int getMax() {
    return max;
  }

  @Override
  public void setLeftSibling(BPlusTreeInnerNode<Integer, Integer> left) {
    leftSibling = Optional.of(left);
  }

  @Override
  public void setRightSibling(BPlusTreeInnerNode<Integer, Integer> right) {
    rightSibling = Optional.of(right);
  }

  @Override
  public Optional<BPlusTreeInnerNode<Integer, Integer>> getLeftSibling() {
    return leftSibling;
  }

  @Override
  public Optional<BPlusTreeInnerNode<Integer, Integer>> getRightSibling() {
    return rightSibling;
  }

  @Override
  public Entries<Integer, Integer> getEntries() {
    if (entries == null) {
      return null;
    }
    return new Entries<>(new ArrayList<>(entries.keys()), new ArrayList<>(entries.children()));
  }

  @Override
  public void setEntries(Entries<Integer, Integer> entries) {
    this.entries = entries;
  }

  @Override
  public boolean isFull() {
    return entries.keys().size() == fanout;
  }

  @Override
  public int size() {
    return 0;
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof DummyInnerNode innerNode)) return false;
    return min == innerNode.min && max == innerNode.max;
  }

  @Override
  public int hashCode() {
    return Objects.hash(min, max);
  }
}

class BPlusTreeInnerNodeTest {
  @Nested
  class EntriesTest {
    private final List<Integer> keys = new ArrayList<>(List.of(1, 3, 4, 8));
    private final List<BPlusTreeNode<Integer, Integer>> children =
        new ArrayList<>(
            List.of(
                new DummyInnerNode(-3, -2),
                new DummyInnerNode(1, 2),
                new DummyInnerNode(3, 3),
                new DummyInnerNode(4, 5),
                new DummyInnerNode(8, 10)));

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
      assertEquals(-3, child.getMin());
      assertEquals(-2, child.getMax());
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
      entries.insertWithRightChild(-1, new DummyInnerNode(-1, 0));

      DummyInnerNode child = (DummyInnerNode) entries.findChild(-3);
      assertEquals(-3, child.getMin());
      assertEquals(-2, child.getMax());
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

  @Nested
  class SplitTest {
    // Testing strategy
    //  + partition on fanout: odd, even

    // cover fanout is even
    @Test
    void evenFanout() {
      int fanout = 4;
      List<Integer> keys = new ArrayList<>(List.of(1, 3, 4, 8));

      DummyInnerNode node1 = new DummyInnerNode(0, 0);
      DummyInnerNode node2 = new DummyInnerNode(1, 2);
      DummyInnerNode node3 = new DummyInnerNode(3, 3);
      DummyInnerNode node4 = new DummyInnerNode(4, 5);
      DummyInnerNode node5 = new DummyInnerNode(8, 10);
      List<BPlusTreeNode<Integer, Integer>> children =
          new ArrayList<>(List.of(node1, node2, node3, node4, node5));

      Entries<Integer, Integer> entries = new Entries<>(keys, children);
      DummyInnerNode innerNode = new DummyInnerNode(entries, fanout);
      BPlusTreeNode.SplitResult<Integer, Integer> split =
          innerNode.split(new DummyAllocator(), fanout);

      assertEquals(List.of(1, 3), innerNode.getEntries().keys());
      assertEquals(List.of(8), ((DummyInnerNode) split.newNode()).getEntries().keys());
      assertEquals(4, split.middleKey());

      assertEquals(List.of(node1, node2, node3), innerNode.getEntries().children());
      assertEquals(
          List.of(node4, node5), ((DummyInnerNode) split.newNode()).getEntries().children());
    }

    // cover fanout is odd
    @Test
    void oddFanout() {
      int fanout = 5;
      List<Integer> keys = new ArrayList<>(List.of(1, 3, 4, 8, 11));

      DummyInnerNode node1 = new DummyInnerNode(0, 0);
      DummyInnerNode node2 = new DummyInnerNode(1, 2);
      DummyInnerNode node3 = new DummyInnerNode(3, 3);
      DummyInnerNode node4 = new DummyInnerNode(4, 5);
      DummyInnerNode node5 = new DummyInnerNode(8, 10);
      DummyInnerNode node6 = new DummyInnerNode(12, 14);
      List<BPlusTreeNode<Integer, Integer>> children =
          new ArrayList<>(List.of(node1, node2, node3, node4, node5, node6));

      Entries<Integer, Integer> entries = new Entries<>(keys, children);
      DummyInnerNode innerNode = new DummyInnerNode(entries, fanout);
      BPlusTreeNode.SplitResult<Integer, Integer> split =
          innerNode.split(new DummyAllocator(), fanout);

      assertEquals(List.of(1, 3), innerNode.getEntries().keys());
      assertEquals(List.of(8, 11), ((DummyInnerNode) split.newNode()).getEntries().keys());
      assertEquals(4, split.middleKey());

      assertEquals(List.of(node1, node2, node3), innerNode.getEntries().children());
      assertEquals(
          List.of(node4, node5, node6), ((DummyInnerNode) split.newNode()).getEntries().children());
    }
  }
}
