package dev.xxdb.index.btree;

import java.util.Optional;

public abstract class BPlusTreeLeafNode<K, V> implements BPlusTreeNode<K, V> {
  @Override
  public boolean isInnerNode() {
    return false;
  }

  public abstract Optional<V> find(K key);

  public abstract void insert(K key, V value);
}
