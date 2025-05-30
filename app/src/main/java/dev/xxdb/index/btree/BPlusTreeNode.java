package dev.xxdb.index.btree;

import java.util.List;
import java.util.Optional;

public interface BPlusTreeNode<K extends Comparable<K>, V> {
  boolean isInnerNode();

  boolean isFull();

  record SplitResult<K extends Comparable<K>, V>(K middleKey, BPlusTreeNode<K, V> newNode) {}

  /**
   * Split this node into two, the first half is stayed with this node, second half belongs to the new node
   * @param allocator knows how to allocate new node
   * @param fanout fanout factor
   * @return the new inner node and the middle key
   */
  SplitResult<K, V> split(BPlusTreeNodeAllocator<K, V> allocator, int fanout);
}
