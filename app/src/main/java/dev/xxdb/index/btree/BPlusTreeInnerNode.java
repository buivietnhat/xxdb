package dev.xxdb.index.btree;

import dev.xxdb.types.Op;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public interface BPlusTreeInnerNode<K extends Comparable<K>, V> extends BPlusTreeNode<K, V> {
  @Override
  default boolean isInnerNode() {
    return true;
  }

  default BPlusTreeNode<K, V> find(K key) {
    return getEntries().findChild(key);
  }

  record SplitResult<K extends Comparable<K>, V>(BPlusTreeInnerNode<K, V> newNode, K middleKey) {}

  /**
   * Split this inner node into two, the first half is stayed with this node, second half belongs to the new node
   * @param allocator know how to allocate new node
   * @param fanout fanout factor
   * @return the new inner node and also the middle key
   */
  default SplitResult<K, V> split(BPlusTreeNodeAllocator<K, V> allocator, int fanout) {
    throw new RuntimeException("unimplemeted");
  }

  /**
   * Represent this node's entries
   *
   * @param keys     sorted in ascending order
   * @param children children pointer
   */
  record Entries<K extends Comparable<K>, V>(List<K> keys, List<BPlusTreeNode<K, V>> children) {
    private void checkRep() {
      assert keys.size() == children.size() - 1;
    }

    private int findChildIdx(K key) {
      int index = Collections.binarySearch(keys, key);
      return index >= 0 ? index + 1 : -index - 1;
    }

    public BPlusTreeNode<K, V> findChild(K key) {
      return children.get(findChildIdx(key));
    }

//    public void insertWithLeftChild(K key, BPlusTreeNode<K, V> child) {
//      int childIdx = findChildIdx(key);
//      keys.add(childIdx, key);
//      children.add(childIdx, child);
//      checkRep();
//    }

    public void insertWithRightChild(K key, BPlusTreeNode<K, V> child) {
      int index = Collections.binarySearch(keys, key);
      if (index >= 0) {
        children.add(index + 1, child);
      } else {
        index = -index - 1;
        if (index < keys.size()) {
          children.add(index, child);
        } else {
          children.add(index + 1, child);
        }
      }
      keys.add(index, key);
      checkRep();
    }
  }

  Entries<K, V> getEntries();

//  /**
//   * Add a new key and left child pointer to this inner node, requires this node is not full
//   *
//   * @param key:          new key to insert
//   * @param childPointer: new child pointer to insert
//   */
//  default void insertWithLeftChild(K key, BPlusTreeNode<K, V> childPointer) {
//    getEntries().insertWithLeftChild(key, childPointer);
//  }

  /**
   * Add a new key and right child pointer to this inner node, requires this node is not full
   *
   * @param key:          new key to insert
   * @param childPointer: new child pointer to insert
   */
  default void insertWithRightChild(K key, BPlusTreeNode<K, V> childPointer) {
    getEntries().insertWithRightChild(key, childPointer);
  }
}
