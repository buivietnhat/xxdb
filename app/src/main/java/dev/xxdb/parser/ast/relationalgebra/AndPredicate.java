package dev.xxdb.parser.ast.relationalgebra;

import dev.xxdb.storage.tuple.Tuple;

public class AndPredicate implements Predicate {
  private final Predicate left;
  private final Predicate right;

  public Predicate getLeft() {
    return left;
  }

  public Predicate getRight() {
    return right;
  }

  public AndPredicate(Predicate left, Predicate right) {
    this.left = left;
    this.right = right;
  }

  @Override
  public <T> T accept(PredicateVisitor<T> visitor) {
    return visitor.visitAndPredicate(this);
  }

//  @Override
//  public boolean evaluate(Tuple tuple) {
//    return left.evaluate(tuple) && right.evaluate(tuple);
//  }
}
