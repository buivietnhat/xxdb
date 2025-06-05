package dev.xxdb.index.btree;

import java.util.Optional;

public interface BPlusTreeNode<K extends Comparable<K>, V> {
  boolean isInnerNode();

  boolean isFull();

  boolean isEmpty();

  int size();

  default boolean isAtleastHalfFull(int fanout) {
    int half = fanout / 2;
    if (fanout % 2 == 0) {
      return size() >= half;
    }

    return size() >= half + 1;
  }

  default boolean canBorrow(int fanout) {
    int half = fanout / 2;
    if (fanout % 2 == 0) {
      return size() > half;
    }

    return size() > half + 1;
  }

  /**
   * get the minimum key contained in this node
   *
   * @return Optional of the key, empty if this node has no keys
   */
  Optional<K> getMinKey();

  /**
   * get the maximum key contained in this node
   *
   * @return Optional of the key, empty if this node has no keys
   */
  Optional<K> getMaxKey();

  record SplitResult<K extends Comparable<K>, V>(K middleKey, BPlusTreeNode<K, V> newNode) {}

  /**
   * Split this node into two, the first half is stayed with this node, second half belongs to the
   * new node
   *
   * @param allocator knows how to allocate new node
   * @param fanout fanout factor
   * @return the new inner node and the middle key
   */
  SplitResult<K, V> split(BPlusTreeNodeAllocator<K, V> allocator, int fanout);
}
