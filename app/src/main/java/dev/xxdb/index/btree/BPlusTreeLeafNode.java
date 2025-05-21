package dev.xxdb.index.btree;

import dev.xxdb.storage.page.BPlusTreeInnerNodePage;
import dev.xxdb.types.Op;

import java.util.*;

public interface BPlusTreeLeafNode<K extends Comparable<K>, V> extends BPlusTreeNode<K, V> {
  record Entry<K, V>(K key, V value) {
  }

  /**
   * @return the pointer to all entries of this node
   */
  List<Entry<K, V>> getAllEntries();

  /**
   * Update the entries
   * @param entries: new entries to update
   */
  void setEntries(List<Entry<K, V>> entries);

  /**
   * @return the fanout factor
   */
  int getFanOut();

  @Override
  default boolean isInnerNode() {
    return false;
  }

  @Override
  default boolean isFull() {
    return getAllEntries().size() == getFanOut();
  }

  default List<V> find(K key, Op op) {
    List<Entry<K, V>> allEntries = getAllEntries();
    int index =
        Collections.binarySearch(
            allEntries, new Entry<>(key, null), Comparator.comparing(Entry::key));
    return extractValues(op, index, allEntries);
  }

  default void insert(K key, V value) {
    assert (!isFull());

  }

  record SplitResult<K extends Comparable<K>, V>(K middleKey, BPlusTreeLeafNode<K, V> newLeaf) {}

  /**
   * Split current node into two, each half of values of current node, update the parent pointer
   * appropriately
   *
   * @param nodes: list node parent pointer when traversing down from the root, root node is in the
   *     first position
   * @param nodeIdx: index of the current node in the nodes list
   * @param allocator: knows how to allocate new BTree nodes
   * @param fanout fanout factor
   * @return the middle key, and new leaf node after split
   */
  default SplitResult<K, V> split(List<BPlusTreeNode<K, V>> nodes, int nodeIdx, BPlusTreeNodeAllocator<K, V> allocator, int fanout) {
    // should have a parent, since it is a leaf node
    assert nodeIdx > 0;

    BPlusTreeInnerNode<K, V> parent = (BPlusTreeInnerNode<K, V>) nodes.get(nodeIdx - 1);

    List<Entry<K, V>> entries = getAllEntries();

    int m = getFanOut();
    int splitIdx = m / 2;

    K middleKey = entries.get(splitIdx).key();
    List<Entry<K, V>> firstPart = entries.subList(0, splitIdx);

    // update new entries for this node
    setEntries(firstPart);

    List<Entry<K,V>> secondPart = entries.subList(splitIdx, entries.size());
    BPlusTreeLeafNode<K, V> newLeaf = allocator.allocateLeafNode(fanout, secondPart);

    return new SplitResult<>(middleKey, newLeaf);
  }

  private List<V> extractValues(Op op, int index, List<Entry<K, V>> entries) {
    switch (op) {
      case EQUALS -> {
        return extractEqual(index, entries);
      }
      case GREATER_THAN -> {
        return extractGreaterThan(index, entries);
      }
      case LESS_THAN -> {
        return extractLessThan(index, entries);
      }
      case GREATER_THAN_OR_EQUAL -> {
        return extractGreaterThanOrEqual(index, entries);
      }
      case LESS_THAN_OR_EQUAL -> {
        return extractLessThanOrEqual(index, entries);
      }
      default -> throw new RuntimeException("unsupported op");
    }
  }

  private List<V> extractEqual(int index, List<Entry<K, V>> entries) {
    return index >= 0 ? List.of(entries.get(index).value()) : Collections.emptyList();
  }

  private List<V> extractGreaterThan(int index, List<Entry<K, V>> entries) {
    // if key not found, binarySearch returns (-insertionPoint - 1)
    index = index >= 0 ? index + 1 : -index - 1;
    List<V> result = new ArrayList<>();
    for (int i = index; i < entries.size(); i++) {
      result.add(entries.get(i).value);
    }
    return result;
  }

  private List<V> extractLessThan(int index, List<Entry<K, V>> entries) {
    // if key not found, binarySearch returns (-insertionPoint - 1)
    index = index >= 0 ? index : -index - 1;
    List<V> result = new ArrayList<>();
    for (int i = 0; i < index; i++) {
      result.add(entries.get(i).value);
    }
    return result;
  }

  private List<V> extractGreaterThanOrEqual(int index, List<Entry<K, V>> entries) {
    // if key not found, binarySearch returns (-insertionPoint - 1)
    index = index >= 0 ? index : -index - 1;
    List<V> result = new ArrayList<>();
    for (int i = index; i < entries.size(); i++) {
      result.add(entries.get(i).value);
    }
    return result;
  }

  private List<V> extractLessThanOrEqual(int index, List<Entry<K, V>> entries) {
    // if key not found, binarySearch returns (-insertionPoint - 1)
    index = index >= 0 ? index + 1 : -index - 1;
    List<V> result = new ArrayList<>();
    for (int i = 0; i < index; i++) {
      result.add(entries.get(i).value);
    }
    return result;
  }

}
