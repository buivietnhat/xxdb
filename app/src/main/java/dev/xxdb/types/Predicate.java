package dev.xxdb.types;

import dev.xxdb.catalog.Catalog;
import dev.xxdb.catalog.Schema;
import dev.xxdb.storage.tuple.Tuple;

public interface Predicate {
  boolean evaluate(Tuple tuple);

  @Override
  String toString();
}
