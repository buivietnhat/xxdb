package dev.xxdb.index.btree;

import java.util.List;

public interface BPlusTreeNode<K extends Comparable<K>, V> {
  boolean isInnerNode();

  boolean isFull();

  /**
   * Split current node into two, each half of values of current node, update the parent pointer appropriately
   * @param nodes: list node parent pointer when traversing down from the root, root node is in the first position
   * @param nodeIdx: index of the current node in the nodes list
   * @param allocator: knows how to allocate new BTree nodes
   */
  void split(List<BPlusTreeNode<K, V>> nodes, int nodeIdx, BPlusTreeNodeAllocator<K, V> allocator);
}
