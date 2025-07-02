package dev.xxdb.index.btree;

import static org.junit.jupiter.api.Assertions.*;

import dev.xxdb.types.Op;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class DummyLeafNode implements BPlusTreeLeafNode<Integer, Integer> {
  private List<Entry<Integer, Integer>> entries;
  private int fanout = 3;

  private Optional<BPlusTreeLeafNode<Integer, Integer>> leftSibling = Optional.empty();
  private Optional<BPlusTreeLeafNode<Integer, Integer>> rightSibling = Optional.empty();

  public DummyLeafNode(List<Entry<Integer, Integer>> entries) {
    this.entries = entries;
  }

  public DummyLeafNode(int fanout) {
    this.fanout = fanout;
    this.entries = new ArrayList<>();
  }

  public DummyLeafNode(List<Entry<Integer, Integer>> entries, int fanout) {
    this.entries = entries;
    this.fanout = fanout;
  }

  @Override
  public List<Entry<Integer, Integer>> getAllEntries() {
    return new ArrayList<>(entries);
  }

  @Override
  public void setEntries(List<Entry<Integer, Integer>> entries) {
    this.entries = entries;
  }

  @Override
  public int getFanOut() {
    return fanout;
  }

  @Override
  public void setLeftSibling(BPlusTreeLeafNode<Integer, Integer> left) {
    leftSibling = Optional.of(left);
  }

  @Override
  public void setRightSibling(BPlusTreeLeafNode<Integer, Integer> right) {
    rightSibling = Optional.of(right);
  }

  @Override
  public Optional<BPlusTreeLeafNode<Integer, Integer>> getLeftSibling() {
    return leftSibling;
  }

  @Override
  public Optional<BPlusTreeLeafNode<Integer, Integer>> getRightSibling() {
    return rightSibling;
  }

  @Override
  public boolean isFull() {
    return entries.size() == fanout;
  }

  @Override
  public int size() {
    return entries.size();
  }
}

class DummyAllocator implements BPlusTreeNodeAllocator<Integer, Integer> {
  @Override
  public BPlusTreeInnerNode<Integer, Integer> allocateInnerNode(int m) {
    throw new RuntimeException("unimplemented");
  }

  @Override
  public BPlusTreeInnerNode<Integer, Integer> allocateInnerNode(
      int m, BPlusTreeInnerNode.Entries<Integer, Integer> entries) {
    return new DummyInnerNode(entries, m);
  }

  @Override
  public BPlusTreeLeafNode<Integer, Integer> allocateLeafNode(int m) {
    return new DummyLeafNode(Collections.emptyList(), m);
  }

  @Override
  public BPlusTreeLeafNode<Integer, Integer> allocateLeafNode(
      int m, List<BPlusTreeLeafNode.Entry<Integer, Integer>> entries) {
    return new DummyLeafNode(entries, m);
  }
}

class BPlusTreeLeafNodeTest {
  private final DummyAllocator allocator = new DummyAllocator();

  DummyLeafNode constructLeafNode(List<Integer> values) {
    List<BPlusTreeLeafNode.Entry<Integer, Integer>> entries = new ArrayList<>();
    values.stream().map(v -> new BPlusTreeLeafNode.Entry<>(v, v)).forEach(entries::add);
    return new DummyLeafNode(entries);
  }

  DummyLeafNode constructLeafNode(List<Integer> values, int fanout) {
    List<BPlusTreeLeafNode.Entry<Integer, Integer>> entries = new ArrayList<>();
    values.stream().map(v -> new BPlusTreeLeafNode.Entry<>(v, v)).forEach(entries::add);
    return new DummyLeafNode(entries, fanout);
  }

  @Nested
  class FindTest {
    // Testing strategy
    // + partition on op: less than, less than or equal, equal, greater than, greater than or equal
    // + partition on the existing of the key on entries: exist, not exist
    // + partition on size of the entries: 0, 1, >1

    // cover op is less than, the key is existed, size > 1
    @Test
    void lessThanKeyExist() {
      List<Integer> keys = List.of(1, 3, 4, 6, 9);
      DummyLeafNode leaf = constructLeafNode(keys);

      List<Integer> result = leaf.find(4, Op.LESS_THAN);
      assertEquals(2, result.size());
      assertTrue(result.contains(1));
      assertTrue(result.contains(3));
    }

    // cover op is equal, the key is existed, size = 1
    @Test
    void equalKeyExist() {
      List<Integer> keys = List.of(3);
      DummyLeafNode leaf = constructLeafNode(keys);

      List<Integer> result = leaf.find(3, Op.EQUALS);
      assertEquals(1, result.size());
      assertTrue(result.contains(3));
    }

    // cover op is less than or equal, key is not existed, size = 0
    @Test
    void lessThanOrEqualKeyNotExistEmptyEntry() {
      List<Integer> keys = Collections.emptyList();
      DummyLeafNode leaf = constructLeafNode(keys);

      List<Integer> result = leaf.find(3, Op.LESS_THAN_OR_EQUAL);
      assertTrue(result.isEmpty());
    }

    // cover op is greater than, size > 1, key is not existed
    @Test
    void greaterThanKeyNotExist() {
      List<Integer> keys = List.of(1, 3, 4, 6, 9, 11, 12, 23);
      DummyLeafNode leaf = constructLeafNode(keys);

      List<Integer> result = leaf.find(10, Op.GREATER_THAN);
      assertEquals(3, result.size());
      assertTrue(result.contains(11));
      assertTrue(result.contains(12));
      assertTrue(result.contains(23));
    }

    // cover op is greater than or equal, size > 1, key is existed
    @Test
    void greaterThanOrEqualKeyExist() {
      List<Integer> keys = List.of(1, 3, 4, 6, 9, 11, 12, 23);
      DummyLeafNode leaf = constructLeafNode(keys);

      List<Integer> result = leaf.find(11, Op.GREATER_THAN_OR_EQUAL);
      assertEquals(3, result.size());
      assertTrue(result.contains(11));
      assertTrue(result.contains(12));
      assertTrue(result.contains(23));
    }
  }

  @Nested
  class SplitTest {
    // Testing strategy
    //  + partition on fanout factor: even, odd

    // cover fanout is even
    @Test
    void splitOnEvenFanout() {
      int fanout = 4;
      List<Integer> keys = List.of(1, 3, 4, 6);
      DummyLeafNode leaf = constructLeafNode(keys, fanout);
      BPlusTreeNode.SplitResult<Integer, Integer> split = leaf.split(allocator, fanout);

      List<Integer> firstHalf =
          leaf.getAllEntries().stream().map(BPlusTreeLeafNode.Entry::key).toList();

      List<Integer> secondHalf =
          ((DummyLeafNode) split.newNode())
              .getAllEntries().stream().map(BPlusTreeLeafNode.Entry::key).toList();

      Integer middleKey = split.middleKey();

      assertEquals(List.of(1, 3), firstHalf);
      assertEquals(List.of(4, 6), secondHalf);
      assertEquals(4, middleKey);
    }

    // cover fanout is odd
    @Test
    void splitOnOddFanout() {
      int fanout = 5;
      List<Integer> keys = List.of(1, 3, 5, 6, 10);
      DummyLeafNode leaf = constructLeafNode(keys, fanout);
      BPlusTreeNode.SplitResult<Integer, Integer> split = leaf.split(allocator, fanout);

      List<Integer> firstHalf =
          leaf.getAllEntries().stream().map(BPlusTreeLeafNode.Entry::key).toList();

      List<Integer> secondHalf =
          ((DummyLeafNode) split.newNode())
              .getAllEntries().stream().map(BPlusTreeLeafNode.Entry::key).toList();

      Integer middleKey = split.middleKey();

      assertEquals(List.of(1, 3), firstHalf);
      assertEquals(List.of(5, 6, 10), secondHalf);
      assertEquals(5, middleKey);
    }
  }

  @Nested
  class InsertTest {
    // Testing strategy
    // + partition on entry list: empty, has 1 entry, >1 entries

    // cover entry list is empty
    @Test
    void empty() {
      DummyLeafNode leaf = new DummyLeafNode(3);
      leaf.insert(3, 4);
      assertEquals(List.of(4), leaf.find(3, Op.EQUALS));
    }

    // cover entry list has one element
    @Test
    void hasOneEntry() {
      List<BPlusTreeLeafNode.Entry<Integer, Integer>> entries =
          new ArrayList<>(List.of(new BPlusTreeLeafNode.Entry<>(3, 4)));
      DummyLeafNode leaf = new DummyLeafNode(entries, 3);
      leaf.insert(1, 2);

      assertEquals(List.of(2, 4), leaf.find(5, Op.LESS_THAN));
    }

    // cover entry list has many elements
    @Test
    void hasManyEntries() {
      List<BPlusTreeLeafNode.Entry<Integer, Integer>> entries =
          new ArrayList<>(
              List.of(
                  new BPlusTreeLeafNode.Entry<>(3, 4),
                  new BPlusTreeLeafNode.Entry<>(4, 5),
                  new BPlusTreeLeafNode.Entry<>(5, 6),
                  new BPlusTreeLeafNode.Entry<>(6, 7)));
      DummyLeafNode leaf = new DummyLeafNode(entries, 5);
      leaf.insert(7, 8);

      assertEquals(List.of(8), leaf.find(7, Op.GREATER_THAN_OR_EQUAL));
    }
  }
}
