package dev.xxdb.index.btree;

import static org.junit.jupiter.api.Assertions.*;

import dev.xxdb.types.Op;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class DummyLeafNode implements BPlusTreeLeafNode<Integer, Integer> {
  private final List<Entry<Integer, Integer>> entries;

  public DummyLeafNode(List<Entry<Integer, Integer>> entries) {
    this.entries = entries;
  }

  @Override
  public List<Entry<Integer, Integer>> getAllEntries() {
    return entries;
  }

  @Override
  public void setEntries(List<Entry<Integer, Integer>> entries) {

  }

  @Override
  public int getFanOut() {
    return 0;
  }

  @Override
  public void insert(Integer key, Integer value) {}

  @Override
  public boolean isFull() {
    return false;
  }

//  @Override
//  public void split(
//      List<BPlusTreeNode<Integer, Integer>> bPlusTreeNodes,
//      int nodeIdx,
//      BPlusTreeNodeAllocator<Integer, Integer> allocator) {}
}

class BPlusTreeLeafNodeTest {

  DummyLeafNode constructLeafNode(List<Integer> values) {
    List<BPlusTreeLeafNode.Entry<Integer, Integer>> entries = new ArrayList<>();
    values.stream().map(v -> new BPlusTreeLeafNode.Entry<>(v, v)).forEach(entries::add);
    return new DummyLeafNode(entries);
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
}
