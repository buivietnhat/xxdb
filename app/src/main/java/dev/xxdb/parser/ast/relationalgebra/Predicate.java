package dev.xxdb.parser.ast.relationalgebra;

import dev.xxdb.storage.tuple.Tuple;

public interface Predicate {
  <T> T accept(PredicateVisitor<T> visitor);
}
