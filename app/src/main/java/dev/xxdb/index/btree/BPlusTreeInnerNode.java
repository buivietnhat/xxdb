package dev.xxdb.index.btree;

public interface BPlusTreeInnerNode<K extends Comparable<K>, V> extends BPlusTreeNode<K, V> {
  @Override
  default boolean isInnerNode() {
    return true;
  }

  public BPlusTreeNode<K, V> find(K key);
}
