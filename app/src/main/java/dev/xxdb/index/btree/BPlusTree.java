package dev.xxdb.index.btree;

import dev.xxdb.types.Op;

import java.util.ArrayList;
import java.util.List;

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

  public BPlusTree(int m, BPlusTreeInnerNode<K, V> root, BPlusTreeNodeAllocator<K, V> nodeAllocator) {
    this.m = m;
    this.root = root;
    this.nodeAllocator = nodeAllocator;
  }

  /**
   * Find list of value given the key
   *
   * @param key: to find
   * @param op:  query option
   * @return list of value satisfied the op condition
   */
  public List<V> find(K key, Op op) {
    TraversingContext ctx = new TraversingContext();
    traverseToLeafNode(key, ctx);
    return ctx.getLeafNode().find(key, op);
  }

  /**
   * Delete the key-value pair, given the key
   *
   * @param key: to delete
   * @return true if the key exists and delete successfully
   */
  public boolean delete(K key) {
    throw new RuntimeException("unimplemented");
  }

  private void insertInnerNode(TraversingContext ctx, int nodeIdx, K newKey, BPlusTreeNode<K, V> childPointer) {
    BPlusTreeInnerNode<K, V> node = (BPlusTreeInnerNode<K, V>) ctx.nodes.get(nodeIdx);
    if (!node.isFull()) {
      node.insertWithRightChild(newKey, childPointer);
      return;
    }

    // split
    BPlusTreeInnerNode.SplitResult<K, V> split = node.split(nodeAllocator, m);
    insertInnerNode(ctx, nodeIdx - 1, split.middleKey(), split.newNode());

    if (newKey.compareTo(split.middleKey()) < 0) {
      node.insertWithRightChild(newKey, childPointer);
    } else {
      ((BPlusTreeInnerNode<K, V>)split.newNode()).insertWithRightChild(newKey, childPointer);
    }
  }

  /**
   * Add new key-value pair to this tree
   *
   * @param key   to add
   * @param value to add
   */
  public void insert(K key, V value) {
    TraversingContext ctx = new TraversingContext();
    traverseToLeafNode(key, ctx);
    BPlusTreeLeafNode<K, V> leaf = ctx.getLeafNode();
    if (!leaf.isFull()) {
      leaf.insert(key, value);
      return;
    }

    // split
    BPlusTreeLeafNode.SplitResult<K, V> splitResult = leaf.split(nodeAllocator, m);
    K middleKey = splitResult.middleKey();
    BPlusTreeLeafNode<K, V> newLeaf = (BPlusTreeLeafNode<K, V>) splitResult.newNode();

    insertInnerNode(ctx, ctx.nodes.size() - 1, middleKey, newLeaf);

    if (key.compareTo(middleKey) < 0) {
      leaf.insert(key, value);
    } else {
      newLeaf.insert(key, value);
    }
  }

  // traverse from the root the  target leaf node (to search, insert, delete the given key)
  private void traverseToLeafNode(K key, TraversingContext context) {
    BPlusTreeNode<K, V> node = root;
    while (node.isInnerNode()) {
      context.addNode(node);
      node = root.find(key);
    }
    context.addNode(node);
  }
}
