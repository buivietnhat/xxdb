package dev.xxdb.types;

import dev.xxdb.storage.tuple.Tuple;

public class AndPredicate implements Predicate {
  private final Predicate left;
  private final Predicate right;

  public AndPredicate(Predicate left, Predicate right) {
    this.left = left;
    this.right = right;
  }

  @Override
  public boolean evaluate(Tuple tuple) {
    return left.evaluate(tuple) && right.evaluate(tuple);
  }

  @Override
  public String toString() {
    return "AndPredicate{" + "left=" + left + ", right=" + right + '}';
  }
}
