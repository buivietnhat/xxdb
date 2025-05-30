package dev.xxdb.index.btree;

import dev.xxdb.types.Op;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DummyNodeAllocator implements BPlusTreeNodeAllocator<Integer, Integer> {
  @Override
  public BPlusTreeInnerNode<Integer, Integer> allocateInnerNode(int m) {
    return new DummyInnerNode();
  }

  @Override
  public BPlusTreeInnerNode<Integer, Integer> allocateInnerNode(int m, BPlusTreeInnerNode.Entries<Integer, Integer> entries) {
    return new DummyInnerNode(entries);
  }

  @Override
  public BPlusTreeLeafNode<Integer, Integer> allocateLeafNode(int m) {
    return new DummyLeafNode(m);
  }

  @Override
  public BPlusTreeLeafNode<Integer, Integer> allocateLeafNode(int m, List<BPlusTreeLeafNode.Entry<Integer, Integer>> entries) {
    return new DummyLeafNode(entries, m);
  }
}

class BPlusTreeTest {
  private BPlusTree<Integer, Integer> tree;
  private final DummyNodeAllocator allocator = new DummyNodeAllocator();

  private void insert(int key) {
    tree.insert(key, key);
  }

  @BeforeEach
  void setUp() {
  }

  @Nested
  class InsertTest {
    // Testing strategy
    // + partition on trigger splitting node: yes, no
    // + partition on places that trigger splitting:
    //    + only leaf node
    //    + leaf and inner nodes
    //    + leaf and inner and then create a new root node

    // cover insert doesn't trigger splitting
    @Test
    void notSplit() {
      tree = new BPlusTree<>(allocator, 3);
      insert(3);
      insert(2);
      insert(1);

      assertEquals(List.of(3), tree.find(3, Op.EQUALS));
      assertEquals(List.of(2), tree.find(2, Op.EQUALS));
      assertEquals(List.of(1), tree.find(1, Op.EQUALS));
    }
  }

  @Nested
  class FindTest {
    // Testing strategy
    // + partition on existing of the key: exist, not exist

    // cover the key does not exist
    @Test
    void keyDoesNotExist() {
      //      insert(10);
      //      insert(31);
      //      insert(20);
      //      insert(38);
      //      insert(6);
      //      insert(44);
      //
      //      assertTrue(tree.find(22).isEmpty());
    }
  }
}
