package dev.xxdb.index.btree;

public interface BPlusTreeNode<K, V> {
  boolean isInnerNode();

  boolean isFull();
}
