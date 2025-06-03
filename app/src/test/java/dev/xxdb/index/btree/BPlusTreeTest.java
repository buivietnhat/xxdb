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
    return new DummyInnerNode(m);
  }

  @Override
  public BPlusTreeInnerNode<Integer, Integer> allocateInnerNode(
      int m, BPlusTreeInnerNode.Entries<Integer, Integer> entries) {
    return new DummyInnerNode(entries, m);
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
                  new ArrayList<>(List.of(4)), new ArrayList<>(List.of(leftChild, rightChild))),
              3);

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

    // cover split at leaf node and inner nodes
    @Test
    void splitAtLeafAndInnerNodes() {
      int fanout = 4;
      DummyLeafNode leaf1 = constructLeafNode(List.of(1, 3), fanout);
      DummyLeafNode leaf2 = constructLeafNode(List.of(5, 7), fanout);
      DummyLeafNode leaf3 = constructLeafNode(List.of(9, 11), fanout);
      DummyLeafNode leaf4 = constructLeafNode(List.of(13, 14, 15, 17), fanout);
      DummyLeafNode leaf5 = constructLeafNode(List.of(20, 21, 23), fanout);

      DummyInnerNode inner =
          new DummyInnerNode(
              new BPlusTreeInnerNode.Entries<>(
                  new ArrayList<>(List.of(5, 9, 13, 19)),
                  new ArrayList<>(List.of(leaf1, leaf2, leaf3, leaf4, leaf5))),
              fanout);

      ArrayList<BPlusTreeNode<Integer, Integer>> rootChildren = new ArrayList<>();
      rootChildren.add(inner);
      rootChildren.add(null);
      DummyInnerNode root =
          new DummyInnerNode(
              new BPlusTreeInnerNode.Entries<>(new ArrayList<>(List.of(24)), rootChildren), fanout);
      BPlusTree<Integer, Integer> tree = new BPlusTree<>(fanout, root, new DummyAllocator());

      tree.insert(16, 16);

      BPlusTreeInnerNode<Integer, Integer> newRoot = tree.getRoot();
      assertEquals(List.of(13, 24), newRoot.getEntries().keys());
      assertEquals(3, newRoot.getEntries().children().size());

      BPlusTreeInnerNode<Integer, Integer> firstRootChild =
          (BPlusTreeInnerNode<Integer, Integer>) newRoot.getEntries().children().getFirst();
      BPlusTreeInnerNode<Integer, Integer> secondRootChild =
          (BPlusTreeInnerNode<Integer, Integer>) newRoot.getEntries().children().get(1);

      assertEquals(List.of(5, 9), firstRootChild.getEntries().keys());
      assertEquals(List.of(15, 19), secondRootChild.getEntries().keys());

      assertEquals(
          List.of(1, 3),
          ((DummyLeafNode) firstRootChild.getEntries().children().get(0))
              .getAllEntries().stream().map(BPlusTreeLeafNode.Entry::key).toList());
      assertEquals(
          List.of(5, 7),
          ((DummyLeafNode) firstRootChild.getEntries().children().get(1))
              .getAllEntries().stream().map(BPlusTreeLeafNode.Entry::key).toList());
      assertEquals(
          List.of(9, 11),
          ((DummyLeafNode) firstRootChild.getEntries().children().get(2))
              .getAllEntries().stream().map(BPlusTreeLeafNode.Entry::key).toList());

      assertEquals(
          List.of(13, 14),
          ((DummyLeafNode) secondRootChild.getEntries().children().get(0))
              .getAllEntries().stream().map(BPlusTreeLeafNode.Entry::key).toList());
      assertEquals(
          List.of(15, 16, 17),
          ((DummyLeafNode) secondRootChild.getEntries().children().get(1))
              .getAllEntries().stream().map(BPlusTreeLeafNode.Entry::key).toList());
      assertEquals(
          List.of(20, 21, 23),
          ((DummyLeafNode) secondRootChild.getEntries().children().get(2))
              .getAllEntries().stream().map(BPlusTreeLeafNode.Entry::key).toList());
    }

    // cover split at leaf node then propagate up and create a new root node
    @Test
    void splitUtilNewRoot() {
      int fanout = 4;
      DummyLeafNode leaf1 = constructLeafNode(List.of(1, 3), fanout);
      DummyLeafNode leaf2 = constructLeafNode(List.of(5, 7), fanout);
      DummyLeafNode leaf3 = constructLeafNode(List.of(9, 11), fanout);
      DummyLeafNode leaf4 = constructLeafNode(List.of(13, 14, 15, 17), fanout);
      DummyLeafNode leaf5 = constructLeafNode(List.of(20, 21, 23), fanout);
      DummyInnerNode root =
          new DummyInnerNode(
              new BPlusTreeInnerNode.Entries<>(
                  new ArrayList<>(List.of(5, 9, 13, 19)),
                  new ArrayList<>(List.of(leaf1, leaf2, leaf3, leaf4, leaf5))),
              fanout);
      BPlusTree<Integer, Integer> tree = new BPlusTree<>(fanout, root, new DummyAllocator());
      tree.insert(16, 16);

      BPlusTreeInnerNode<Integer, Integer> newRoot = tree.getRoot();
      assertEquals(List.of(13), newRoot.getEntries().keys());
      assertEquals(2, newRoot.getEntries().children().size());

      BPlusTreeInnerNode<Integer, Integer> firstRootChild =
          (BPlusTreeInnerNode<Integer, Integer>) newRoot.getEntries().children().getFirst();
      BPlusTreeInnerNode<Integer, Integer> secondRootChild =
          (BPlusTreeInnerNode<Integer, Integer>) newRoot.getEntries().children().getLast();

      assertEquals(List.of(5, 9), firstRootChild.getEntries().keys());
      assertEquals(List.of(15, 19), secondRootChild.getEntries().keys());

      assertEquals(
          List.of(1, 3),
          ((DummyLeafNode) firstRootChild.getEntries().children().get(0))
              .getAllEntries().stream().map(BPlusTreeLeafNode.Entry::key).toList());
      assertEquals(
          List.of(5, 7),
          ((DummyLeafNode) firstRootChild.getEntries().children().get(1))
              .getAllEntries().stream().map(BPlusTreeLeafNode.Entry::key).toList());
      assertEquals(
          List.of(9, 11),
          ((DummyLeafNode) firstRootChild.getEntries().children().get(2))
              .getAllEntries().stream().map(BPlusTreeLeafNode.Entry::key).toList());

      assertEquals(
          List.of(13, 14),
          ((DummyLeafNode) secondRootChild.getEntries().children().get(0))
              .getAllEntries().stream().map(BPlusTreeLeafNode.Entry::key).toList());
      assertEquals(
          List.of(15, 16, 17),
          ((DummyLeafNode) secondRootChild.getEntries().children().get(1))
              .getAllEntries().stream().map(BPlusTreeLeafNode.Entry::key).toList());
      assertEquals(
          List.of(20, 21, 23),
          ((DummyLeafNode) secondRootChild.getEntries().children().get(2))
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
