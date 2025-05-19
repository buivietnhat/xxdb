package dev.xxdb.index.btree;

import dev.xxdb.types.Op;
import java.util.ArrayList;
import java.util.List;

public class BPlusTree<K extends Comparable<K>, V> {
  private final BPlusTreeNodeAllocator<K, V> nodeAllocator;
  private final BPlusTreeInnerNode<K, V> root;

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
  public BPlusTree(BPlusTreeNodeAllocator<K, V> nodeAllocator) {
    this.nodeAllocator = nodeAllocator;
    this.root = nodeAllocator.allocateInnerNode();
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

  /**
   * Delete the key-value pair, given the key
   *
   * @param key: to delete
   * @return true if the key exists and delete successfully
   */
  public boolean delete(K key) {
    throw new RuntimeException("unimplemented");
  }

  /**
   * Add new key-value pair to this tree
   *
   * @param key to add
   * @param value to add
   */
  public void insert(K key, V value) {
    TraversingContext ctx = new TraversingContext();
    traverseToLeafNode(key, ctx);
    List<BPlusTreeNode<K, V>> nodes = ctx.nodes;
    BPlusTreeLeafNode<K, V> leaf = ctx.getLeafNode();

    if (leaf.isFull()) {
      leaf.split(nodes, nodes.size(), nodeAllocator);
    }

    leaf.insert(key, value);
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
