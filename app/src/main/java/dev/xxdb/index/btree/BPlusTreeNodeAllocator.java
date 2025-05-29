package dev.xxdb.index.btree;


import java.util.List;

public interface BPlusTreeNodeAllocator<K extends Comparable<K>, V> {
  /**
   * allocate new inner node
   * @param m fanout factor
   */
  BPlusTreeInnerNode<K, V> allocateInnerNode(int m);

  BPlusTreeInnerNode<K, V> allocateInnerNode(int m, BPlusTreeInnerNode.Entries<K, V> entries);

  /**
   * allocate new leaf node
   * @param m fanout factor
   */
  BPlusTreeLeafNode<K, V> allocateLeafNode(int m);

  BPlusTreeLeafNode<K, V> allocateLeafNode(int m, List<BPlusTreeLeafNode.Entry<K, V>> entries);
}
