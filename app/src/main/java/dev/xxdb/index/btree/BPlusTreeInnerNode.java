package dev.xxdb.index.btree;

import java.util.List;
import java.util.Optional;

public interface BPlusTreeInnerNode<K extends Comparable<K>, V> extends BPlusTreeNode<K, V> {
  @Override
  default boolean isInnerNode() {
    return true;
  }

  BPlusTreeNode<K, V> find(K key);

  /**
   * Add a new key-child pointer to this inner node
   *
   * @param nodes      parent pointer traversing from root to the leaf, root at the first index
   * @param nodeIdx    index of the current node in nodes
   * @param key:       new key to insert
   * @param pointer:   new child pointer to insert
   * @param allocator: knows how to allocate new node
   * @param fanout:    fanout factor
   * @return optional a new root of the tree
   */
  default Optional<BPlusTreeInnerNode<K, V>> insert(List<BPlusTreeNode<K, V>> nodes, int nodeIdx, K key, BPlusTreeNode<K, V> pointer,
                                                    BPlusTreeNodeAllocator<K, V> allocator, int fanout) {

  }
}
