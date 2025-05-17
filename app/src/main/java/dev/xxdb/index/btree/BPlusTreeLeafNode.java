package dev.xxdb.index.btree;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public interface BPlusTreeLeafNode<K extends Comparable<K>, V> extends BPlusTreeNode<K, V> {
  record Entry<K, V>(K key, V value) {
  }

  List<Entry<K, V>> getAllEntries();

  @Override
  default boolean isInnerNode() {
    return false;
  }

  default Optional<V> find(K key) {
    List<Entry<K, V>> allEntries = getAllEntries();
    int index = Collections.binarySearch(allEntries, new Entry<>(key, null),
        Comparator.comparing(Entry::key));
    return index >= 0 ? Optional.of(allEntries.get(index).value()) : Optional.empty();
  }

  void insert(K key, V value);


}
