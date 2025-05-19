package dev.xxdb.index.btree;

public interface BPlusTreeNodeAllocator<K extends Comparable<K>, V> {
  BPlusTreeInnerNode<K, V> allocateInnerNode();

  BPlusTreeLeafNode<K, V> allocateLeafNode();
}
