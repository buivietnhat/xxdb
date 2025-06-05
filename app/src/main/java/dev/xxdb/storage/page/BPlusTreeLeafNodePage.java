package dev.xxdb.storage.page;

import dev.xxdb.index.btree.BPlusTreeLeafNode;
import java.util.List;
import java.util.Optional;

public class BPlusTreeLeafNodePage<K extends Comparable<K>, V> extends Page
    implements BPlusTreeLeafNode<K, V> {
  public BPlusTreeLeafNodePage(int pageId) {
    super(pageId);
  }

  public BPlusTreeLeafNodePage(byte[] data) {
    super(data);
  }

  @Override
  public List<Entry<K, V>> getAllEntries() {
    throw new RuntimeException("unimplemented");
  }

  @Override
  public void setEntries(List<Entry<K, V>> entries) {
    throw new RuntimeException("unimplemented");
  }

  @Override
  public int getFanOut() {
    throw new RuntimeException("unimplemented");
  }

  @Override
  public void setLeftSibling(BPlusTreeLeafNode<K, V> left) {
    throw new RuntimeException("unimplemented");
  }

  @Override
  public void setRightSibling(BPlusTreeLeafNode<K, V> left) {
    throw new RuntimeException("unimplemented");
  }

  @Override
  public Optional<BPlusTreeLeafNode<K, V>> getLeftSibling() {
    throw new RuntimeException("unimplemented");
  }

  @Override
  public Optional<BPlusTreeLeafNode<K, V>> getRightSibling() {
    throw new RuntimeException("unimplemented");
  }

  @Override
  public void insert(K key, V value) {
    throw new RuntimeException("unimplemented");
  }

  @Override
  public boolean isFull() {
    throw new RuntimeException("unimplemented");
  }

  @Override
  public int size() {
    throw new RuntimeException("unimplemented");
  }
}
