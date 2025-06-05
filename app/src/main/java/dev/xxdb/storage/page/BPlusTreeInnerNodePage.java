package dev.xxdb.storage.page;

import dev.xxdb.index.btree.BPlusTreeInnerNode;
import dev.xxdb.index.btree.BPlusTreeLeafNode;
import dev.xxdb.index.btree.BPlusTreeNode;

import java.util.Optional;

public class BPlusTreeInnerNodePage<K extends Comparable<K>, V> extends Page
    implements BPlusTreeInnerNode<K, V> {

  public BPlusTreeInnerNodePage(int pageId) {
    super(pageId);
  }

  public BPlusTreeInnerNodePage(byte[] data) {
    super(data);
  }

  @Override
  public BPlusTreeNode<K, V> find(K key) {
    throw new RuntimeException("unimplemented");
  }

  @Override
  public void setLeftSibling(BPlusTreeInnerNode<K, V> left) {
    throw new RuntimeException("unimplemented");
  }

  @Override
  public void setRightSibling(BPlusTreeInnerNode<K, V> right) {
    throw new RuntimeException("unimplemented");
  }

  @Override
  public Optional<BPlusTreeInnerNode<K, V>> getLeftSibling() {
    throw new RuntimeException("unimplemented");
  }

  @Override
  public Optional<BPlusTreeInnerNode<K, V>> getRightSibling() {
    throw new RuntimeException("unimplemented");
  }

  @Override
  public Entries<K, V> getEntries() {
    throw new RuntimeException("unimplemented");
  }

  @Override
  public void setEntries(Entries<K, V> entries) {
    throw new RuntimeException("unimplemented");
  }

  @Override
  public boolean isFull() {
    return false;
  }

  @Override
  public int size() {
    return 0;
  }

  //  @Override
  //  public void split(
  //      List<BPlusTreeNode<K, V>> bPlusTreeNodes,
  //      int nodeIdx,
  //      BPlusTreeNodeAllocator<K, V> allocator) {}
}
