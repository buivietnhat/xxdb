package dev.xxdb.index.btree;

import dev.xxdb.types.Op;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BPlusTree<K extends Comparable<K>, V> {
  private final BPlusTreeNodeAllocator<K, V> nodeAllocator;
  private BPlusTreeInnerNode<K, V> root;
  // fanout factor
  private final int m;

  private class TraversingContext {
    public List<BPlusTreeNode<K, V>> nodes = new ArrayList<>();

    public void addNode(BPlusTreeNode<K, V> node) {
      nodes.add(node);
    }

    BPlusTreeLeafNode<K, V> getLeafNode() {
      return (BPlusTreeLeafNode<K, V>) nodes.getLast();
    }
  }

  void checkRep() {
    assert root != null;
  }

  public BPlusTreeInnerNode<K, V> getRoot() {
    return root;
  }

  /**
   * Construct the BPlusTree
   *
   * @param nodeAllocator: knows how to allocate new node
   */
  public BPlusTree(BPlusTreeNodeAllocator<K, V> nodeAllocator, int m) {
    this.nodeAllocator = nodeAllocator;
    this.root = nodeAllocator.allocateInnerNode(m);
    this.m = m;
  }

  public BPlusTree(
      int m, BPlusTreeInnerNode<K, V> root, BPlusTreeNodeAllocator<K, V> nodeAllocator) {
    this.m = m;
    this.root = root;
    this.nodeAllocator = nodeAllocator;
  }

  /**
   * Find list of value given the key
   *
   * @param key: to find
   * @param op: query option
   * @return list of value satisfied the op condition
   */
  public List<V> find(K key, Op op) {
    TraversingContext ctx = new TraversingContext();
    traverseToLeafNode(key, ctx);
    return ctx.getLeafNode().find(key, op);
  }

  private boolean canBorrowFrom(BPlusTreeNode<K, V> sibling, BPlusTreeInnerNode<K, V> parent) {
    boolean sameParent = parent.isMyChild(sibling);
    return sameParent && sibling.canBorrow(m);
  }

  private boolean tryMerge(
      BPlusTreeNode<K, V> original,
      BPlusTreeNode<K, V> anotherNode,
      BPlusTreeInnerNode<K, V> parentOfOriginal) {
    if (!parentOfOriginal.isMyChild(anotherNode)) {
      return false;
    }

    return true;
  }

  /**
   * Delete the key-value pair, given the key
   *
   * @param key: to delete
   * @return true if the key exists and delete successfully
   */
  public boolean delete(K key) {
    TraversingContext ctx = new TraversingContext();
    traverseToLeafNode(key, ctx);
    BPlusTreeLeafNode<K, V> leaf = ctx.getLeafNode();
    boolean ok = leaf.delete(key);

    if (!leaf.isAtleastHalfFull(m)) {
      // this node is underfill
      // first try to borrow from another sibling
      BPlusTreeInnerNode<K, V> parent =
          (BPlusTreeInnerNode<K, V>) ctx.nodes.get(ctx.nodes.size() - 2);
      Optional<BPlusTreeLeafNode<K, V>> rightSibling = leaf.getRightSibling();
      Optional<BPlusTreeLeafNode<K, V>> leftSibling = leaf.getLeftSibling();
      if (rightSibling.isPresent() && canBorrowFrom(rightSibling.get(), parent)) {
        Optional<BPlusTreeLeafNode.Entry<K, V>> maybeBorrowEntry = rightSibling.get().popMinEntry();
        // the borrowing entry must exist
        leaf.insert(maybeBorrowEntry.get().key(), maybeBorrowEntry.get().value());
        parent.updateKey(key, rightSibling.get().getMinKey().get());
      } else if (leftSibling.isPresent() && canBorrowFrom(leftSibling.get(), parent)) {
        Optional<BPlusTreeLeafNode.Entry<K, V>> maybeBorrowEntry = leftSibling.get().popMaxEntry();
        leaf.insert(maybeBorrowEntry.get().key(), maybeBorrowEntry.get().value());
        parent.updateKey(key, maybeBorrowEntry.get().key());
      } else {
        // can not borrow, try to merge
        if (rightSibling.isPresent() && tryMerge(leaf, rightSibling.get(), parent)) {

        } else if (leftSibling.isPresent() && tryMerge(leaf, leftSibling.get(), parent)) {

        } else {
          System.out.println("the leaf node is underfilled but cannot merge");
        }
      }
    }

    return ok;
  }

  private void insertInnerNode(
      TraversingContext ctx, int nodeIdx, K newKey, BPlusTreeNode<K, V> childPointer) {
    BPlusTreeInnerNode<K, V> node = (BPlusTreeInnerNode<K, V>) ctx.nodes.get(nodeIdx);
    if (!node.isFull()) {
      node.insertWithRightChild(newKey, childPointer);
      return;
    }

    // split
    BPlusTreeInnerNode.SplitResult<K, V> split = node.split(nodeAllocator, m);
    K middleKey = split.middleKey();
    BPlusTreeInnerNode<K, V> newNode = (BPlusTreeInnerNode<K, V>) split.newNode();

    node.setRightSibling(newNode);
    newNode.setLeftSibling(node);

    if (nodeIdx > 0) {
      // not a root node
      insertInnerNode(ctx, nodeIdx - 1, middleKey, newNode);
    } else {
      // allocate a new root node
      this.root =
          nodeAllocator.allocateInnerNode(
              m,
              new BPlusTreeInnerNode.Entries<>(
                  new ArrayList<>(List.of(middleKey)), new ArrayList<>(List.of(node, newNode))));
    }

    if (newKey.compareTo(middleKey) < 0) {
      node.insertWithRightChild(newKey, childPointer);
    } else {
      newNode.insertWithRightChild(newKey, childPointer);
    }
  }

  /**
   * Add new key-value pair to this tree
   *
   * @param key to add
   * @param value to add
   */
  public void insert(K key, V value) {
    if (root.isEmpty()) {
      BPlusTreeLeafNode<K, V> leaf = nodeAllocator.allocateLeafNode(m);
      leaf.insert(key, value);
      root.insertWithRightChild(key, leaf);
      return;
    }

    TraversingContext ctx = new TraversingContext();
    traverseToLeafNode(key, ctx);
    BPlusTreeLeafNode<K, V> leaf = ctx.getLeafNode();
    if (leaf == null) {
      leaf = nodeAllocator.allocateLeafNode(m);
      ((BPlusTreeInnerNode<K, V>) ctx.nodes.get(ctx.nodes.size() - 2))
          .updateChildPointer(key, leaf);
    }

    if (!leaf.isFull()) {
      leaf.insert(key, value);
      return;
    }

    // split
    BPlusTreeLeafNode.SplitResult<K, V> splitResult = leaf.split(nodeAllocator, m);
    K middleKey = splitResult.middleKey();
    BPlusTreeLeafNode<K, V> newLeaf = (BPlusTreeLeafNode<K, V>) splitResult.newNode();

    leaf.setRightSibling(newLeaf);
    newLeaf.setLeftSibling(leaf);

    insertInnerNode(ctx, ctx.nodes.size() - 2, middleKey, newLeaf);

    if (key.compareTo(middleKey) < 0) {
      leaf.insert(key, value);
    } else {
      newLeaf.insert(key, value);
    }
  }

  // traverse from the root the target leaf node (to search, insert, delete the given key)
  private void traverseToLeafNode(K key, TraversingContext context) {
    BPlusTreeNode<K, V> node = root;
    while (node != null && node.isInnerNode()) {
      context.addNode(node);
      node = ((BPlusTreeInnerNode<K, V>) node).find(key);
    }
    context.addNode(node);
  }
}
