package dev.xxdb.storage.page;

import dev.xxdb.index.btree.BPlusTreeLeafNode;
import java.util.List;

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
    return 0;
  }

  @Override
  public void insert(K key, V value) {}

  @Override
  public boolean isFull() {
    return false;
  }

  //  @Override
  //  public void split(
  //      List<BPlusTreeNode<K, V>> bPlusTreeNodes,
  //      int nodeIdx,
  //      BPlusTreeNodeAllocator<K, V> allocator) {}
}
