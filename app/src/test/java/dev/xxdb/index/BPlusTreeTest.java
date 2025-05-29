package dev.xxdb.index;

import static org.junit.jupiter.api.Assertions.*;

import dev.xxdb.index.btree.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

class DummyNodeAllocator implements BPlusTreeNodeAllocator<Integer, Integer> {
  @Override
  public BPlusTreeInnerNode<Integer, Integer> allocateInnerNode(int m) {
    return null;
  }

  @Override
  public BPlusTreeInnerNode<Integer, Integer> allocateInnerNode(int m, BPlusTreeInnerNode.Entries<Integer, Integer> entries) {
    return null;
  }

  @Override
  public BPlusTreeLeafNode<Integer, Integer> allocateLeafNode(int m) {
    return null;
  }

  @Override
  public BPlusTreeLeafNode<Integer, Integer> allocateLeafNode(int m, List<BPlusTreeLeafNode.Entry<Integer, Integer>> entries) {
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
    tree = new BPlusTree<>(nodeAllocator, 3);
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
