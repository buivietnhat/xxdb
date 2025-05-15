package dev.xxdb.types;

import dev.xxdb.storage.tuple.Tuple;

public class OrPredicate implements Predicate {
  private final Predicate left;
  private final Predicate right;

  public OrPredicate(Predicate left, Predicate right) {
    this.left = left;
    this.right = right;
  }

  @Override
  public boolean evaluate(Tuple tuple) {
    return left.evaluate(tuple) || right.evaluate(tuple);
  }

  @Override
  public String toString() {
    return "OrPredicate{" + "left=" + left + ", right=" + right + '}';
  }
}
