package dev.xxdb.index.btree;

public interface BPlusTreeNodeAllocator<K, V> {
  BPlusTreeInnerNode<K, V> allocateInnerNode();

  BPlusTreeLeafNode<K, V> allocateLeafNode();
}
