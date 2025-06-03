package dev.xxdb.index.btree;

import java.util.ArrayList;
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

  @Override
  default Optional<K> getMinKey() {
    if (getEntries().keys.isEmpty()) {
      return Optional.empty();
    }
    return Optional.of(getEntries().keys().getFirst());
  }

  @Override
  default Optional<K> getMaxKey() {
    if (getEntries() == null || getEntries().keys.isEmpty()) {
      return Optional.empty();
    }
    return Optional.of(getEntries().keys().getLast());
  }

  @Override
  default SplitResult<K, V> split(BPlusTreeNodeAllocator<K, V> allocator, int fanout) {
    Entries<K, V> entries = getEntries();
    int middleIdx = fanout / 2;
    List<K> keys = entries.keys();
    List<BPlusTreeNode<K, V>> children = entries.children();

    List<K> firstHalfKeys = keys.subList(0, middleIdx);
    List<BPlusTreeNode<K, V>> firstHalfChildren = children.subList(0, middleIdx + 1);
    setEntries(new Entries<>(firstHalfKeys, firstHalfChildren));

    List<K> secondHalfKeys = keys.subList(middleIdx + 1, keys.size());
    List<BPlusTreeNode<K, V>> secondHalfChildren = children.subList(middleIdx + 1, children.size());
    BPlusTreeInnerNode<K, V> newNode =
        allocator.allocateInnerNode(fanout, new Entries<>(secondHalfKeys, secondHalfChildren));

    return new SplitResult<>(keys.get(middleIdx), newNode);
  }

  /**
   * Represent this node's entries
   *
   * @param keys sorted in ascending order
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

    public void updateChildPointer(K key, BPlusTreeNode<K, V> childpointer) {
      int childIdx = findChildIdx(key);
      assert children.get(childIdx) == null;
      children.set(childIdx, childpointer);
    }

    public void insertWithRightChild(K key, BPlusTreeNode<K, V> child) {
      int index = Collections.binarySearch(keys, key);
      if (index >= 0) {
        children.add(index + 1, child);
      } else {
        index = -index - 1;
        // if the key to insert is at the first position, its right child must have keys > current
        // left most child
        // since the child pointer is a result of splitting that old left most child
        // similarly, if the key to insert is at the end position, its right child key must >
        // current right most child
        if (index == 0 || index == keys.size()) {
          children.add(index + 1, child);
        } else {
          children.add(index, child);
        }
      }
      keys.add(index, key);
      checkRep();
    }
  }

  @Override
  default boolean isEmpty() {
    return getEntries() == null || getEntries().keys.isEmpty();
  }

  Entries<K, V> getEntries();

  void setEntries(Entries<K, V> entries);

  /**
   * Add a new key and right child pointer to this inner node, requires this node is not full
   *
   * @param key: new key to insert
   * @param childPointer: new child pointer to insert
   */
  default void insertWithRightChild(K key, BPlusTreeNode<K, V> childPointer) {
    if (getEntries() == null) {
      ArrayList<K> keys = new ArrayList<>(List.of(key));
      ArrayList<BPlusTreeNode<K, V>> children = new ArrayList<>();
      children.add(null);
      children.add(childPointer);

      setEntries(new Entries<>(keys, children));
      return;
    }

    Entries<K, V> entries = getEntries();
    entries.insertWithRightChild(key, childPointer);
    setEntries(entries);
  }

  /**
   * Update the child pointer when traversing with the key For now given the key, when traversing
   * with BTree logic, it lands at a null child (not yet allocated) So this API is used to update
   * the child pointer
   *
   * @param key: given key when traversing the tree
   * @param childPointer: pointer to the new child
   */
  default void updateChildPointer(K key, BPlusTreeNode<K, V> childPointer) {
    Entries<K, V> entries = getEntries();
    entries.updateChildPointer(key, childPointer);
    setEntries(entries);
  }
}
