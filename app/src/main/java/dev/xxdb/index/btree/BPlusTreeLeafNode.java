package dev.xxdb.index.btree;

import dev.xxdb.types.Op;
import java.util.*;

public interface BPlusTreeLeafNode<K extends Comparable<K>, V> extends BPlusTreeNode<K, V> {
  record Entry<K, V>(K key, V value) {}

  List<Entry<K, V>> getAllEntries();

  @Override
  default boolean isInnerNode() {
    return false;
  }

  default List<V> find(K key, Op op) {
    List<Entry<K, V>> allEntries = getAllEntries();
    int index =
        Collections.binarySearch(
            allEntries, new Entry<>(key, null), Comparator.comparing(Entry::key));
    return extractValues(op, index, allEntries);
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

  void insert(K key, V value);
}
