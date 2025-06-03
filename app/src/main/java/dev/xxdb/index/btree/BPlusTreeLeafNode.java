package dev.xxdb.index.btree;

import dev.xxdb.types.Op;
import java.util.*;

public interface BPlusTreeLeafNode<K extends Comparable<K>, V> extends BPlusTreeNode<K, V> {
  record Entry<K, V>(K key, V value) {}

  /**
   * @return the pointer to all entries of this node
   */
  List<Entry<K, V>> getAllEntries();

  /**
   * Update the entries
   *
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

  @Override
  default Optional<K> getMinKey() {
    if (getAllEntries().isEmpty()) {
      return Optional.empty();
    }
    return Optional.of(getAllEntries().getFirst().key);
  }

  @Override
  default Optional<K> getMaxKey() {
    if (getAllEntries().isEmpty()) {
      return Optional.empty();
    }
    return Optional.of(getAllEntries().getLast().key);
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
    List<Entry<K, V>> entries = getAllEntries();
    int index =
        Collections.binarySearch(entries, new Entry<>(key, null), Comparator.comparing(Entry::key));
    if (index < 0) {
      index = -index - 1;
    }

    entries.add(index, new Entry<>(key, value));

    setEntries(entries);
  }

  @Override
  default boolean isEmpty() {
    return getAllEntries() == null || getAllEntries().isEmpty();
  }

  @Override
  default SplitResult<K, V> split(BPlusTreeNodeAllocator<K, V> allocator, int fanout) {
    List<Entry<K, V>> entries = getAllEntries();

    int m = getFanOut();
    int splitIdx = m / 2;

    K middleKey = entries.get(splitIdx).key();
    List<Entry<K, V>> firstPart = entries.subList(0, splitIdx);

    // update new entries for this node
    setEntries(firstPart);

    List<Entry<K, V>> secondPart = entries.subList(splitIdx, entries.size());
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
