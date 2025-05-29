package dev.xxdb.storage.page;

import dev.xxdb.index.btree.BPlusTreeInnerNode;
import dev.xxdb.index.btree.BPlusTreeNode;
import dev.xxdb.index.btree.BPlusTreeNodeAllocator;
import java.util.List;

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
    return null;
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

//  @Override
//  public void split(
//      List<BPlusTreeNode<K, V>> bPlusTreeNodes,
//      int nodeIdx,
//      BPlusTreeNodeAllocator<K, V> allocator) {}
}
