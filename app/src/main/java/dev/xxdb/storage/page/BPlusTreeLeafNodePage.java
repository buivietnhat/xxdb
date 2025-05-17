package dev.xxdb.storage.page;

import dev.xxdb.index.btree.BPlusTreeLeafNode;
import dev.xxdb.index.btree.BPlusTreeNode;
import dev.xxdb.index.btree.BPlusTreeNodeAllocator;

import java.util.List;
import java.util.Optional;

public class BPlusTreeLeafNodePage<K, V> extends Page implements BPlusTreeLeafNode<K, V> {
  public BPlusTreeLeafNodePage(int pageId) {
    super(pageId);
  }

  public BPlusTreeLeafNodePage(byte[] data) {
    super(data);
  }

  @Override
  public Optional<V> find(K key) {
    return Optional.empty();
  }

  @Override
  public void insert(K key, V value) {

  }

  @Override
  public boolean isFull() {
    return false;
  }

  @Override
  public void split(List<BPlusTreeNode<K, V>> bPlusTreeNodes, int nodeIdx, BPlusTreeNodeAllocator<K, V> allocator) {

  }
}
