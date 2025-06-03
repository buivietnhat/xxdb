package dev.xxdb.index.btree;

import static org.junit.jupiter.api.Assertions.assertEquals;

import dev.xxdb.types.Op;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class DummyNodeAllocator implements BPlusTreeNodeAllocator<Integer, Integer> {
  @Override
  public BPlusTreeInnerNode<Integer, Integer> allocateInnerNode(int m) {
    return new DummyInnerNode();
  }

  @Override
  public BPlusTreeInnerNode<Integer, Integer> allocateInnerNode(
      int m, BPlusTreeInnerNode.Entries<Integer, Integer> entries) {
    return new DummyInnerNode(entries);
  }

  @Override
  public BPlusTreeLeafNode<Integer, Integer> allocateLeafNode(int m) {
    return new DummyLeafNode(m);
  }

  @Override
  public BPlusTreeLeafNode<Integer, Integer> allocateLeafNode(
      int m, List<BPlusTreeLeafNode.Entry<Integer, Integer>> entries) {
    return new DummyLeafNode(entries, m);
  }
}

class BPlusTreeTest {
  private BPlusTree<Integer, Integer> tree;
  private final DummyNodeAllocator allocator = new DummyNodeAllocator();

  private void insert(int key) {
    tree.insert(key, key);
  }

  DummyLeafNode constructLeafNode(List<Integer> values, int fanout) {
    List<BPlusTreeLeafNode.Entry<Integer, Integer>> entries = new ArrayList<>();
    values.stream().map(v -> new BPlusTreeLeafNode.Entry<>(v, v)).forEach(entries::add);
    return new DummyLeafNode(entries, fanout);
  }

  @BeforeEach
  void setUp() {}

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

    // cover only trigger split at leaf node
    @Test
    void splitAtLeafNode() {
      DummyLeafNode leftChild = constructLeafNode(List.of(1, 2, 3), 3);
      DummyLeafNode rightChild = constructLeafNode(List.of(5, 6), 3);
      DummyInnerNode root =
          new DummyInnerNode(
              new BPlusTreeInnerNode.Entries<>(
                  new ArrayList<>(List.of(4)), new ArrayList<>(List.of(leftChild, rightChild))));

      BPlusTree<Integer, Integer> tree = new BPlusTree<>(3, root, new DummyAllocator());
      tree.insert(0, 0);

      assertEquals(2, root.getEntries().keys().size());
      assertEquals(3, root.getEntries().children().size());

      assertEquals(List.of(2, 4), root.getEntries().keys());
      List<BPlusTreeNode<Integer, Integer>> children = root.getEntries().children();
      assertEquals(
          List.of(0, 1),
          ((DummyLeafNode) children.get(0))
              .getAllEntries().stream().map(BPlusTreeLeafNode.Entry::key).toList());
      assertEquals(
          List.of(2, 3),
          ((DummyLeafNode) children.get(1))
              .getAllEntries().stream().map(BPlusTreeLeafNode.Entry::key).toList());
      assertEquals(
          List.of(5, 6),
          ((DummyLeafNode) children.get(2))
              .getAllEntries().stream().map(BPlusTreeLeafNode.Entry::key).toList());
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
