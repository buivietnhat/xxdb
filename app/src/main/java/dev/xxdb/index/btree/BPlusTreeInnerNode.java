package dev.xxdb.index.btree;

public abstract class BPlusTreeInnerNode<K, V> implements BPlusTreeNode<K, V> {
  @Override
  public boolean isInnerNode() {
    return true;
  }

  public abstract BPlusTreeNode<K, V> find(K key);

}
