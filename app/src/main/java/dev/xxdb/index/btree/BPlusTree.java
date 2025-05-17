package dev.xxdb.index.btree;

import java.util.Optional;

public class BPlusTree<K, V> {
  private final BPlusTreeNodeAllocator<K, V> nodeAllocator;
  private final BPlusTreeInnerNode<K, V> root;

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
   * Find the value given the key
   *
   * @param key: to find
   * @return Optional.empty() if the value not found, otherwise Optional.of(V)
   */
  public Optional<V> find(K key) {
    BPlusTreeLeafNode<K, V> leafNode = findLeafNode(key);
    return leafNode.find(key);
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
    BPlusTreeLeafNode<K, V> leafNode = findLeafNode(key);
    if (!leafNode.isFull()) {
      leafNode.insert(key, value);
      return;
    }

    // TODO: split
  }

  private BPlusTreeLeafNode<K, V> findLeafNode(K key) {
    BPlusTreeNode<K, V> node = root;
    while (node.isInnerNode()) {
      node = root.find(key);
    }

    return (BPlusTreeLeafNode<K, V>) node; // trust me
  }
}
