package dev.xxdb.index;

import static org.junit.jupiter.api.Assertions.*;

import dev.xxdb.index.btree.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class DummyNodeAllocator implements BPlusTreeNodeAllocator<Integer, Integer> {

  @Override
  public BPlusTreeInnerNode<Integer, Integer> allocateInnerNode() {
    return null;
  }

  @Override
  public BPlusTreeLeafNode<Integer, Integer> allocateLeafNode() {
    return null;
  }
}

class BPlusTreeTest {
  private BPlusTree<Integer, Integer> tree;
  private final DummyNodeAllocator nodeAllocator = new DummyNodeAllocator();

  private void insert(int key) {
    tree.insert(key, key);
  }

  @BeforeEach
  void setUp() {
    tree = new BPlusTree<>(nodeAllocator);
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
