package dev.xxdb.parser.ast.relationalgebra;

public class AndPredicate implements Predicate {
  private final Predicate left;
  private final Predicate right;

  public AndPredicate(Predicate left, Predicate right) {
    this.left = left;
    this.right = right;
  }
}
