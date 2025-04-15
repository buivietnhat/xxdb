package dev.xxdb.parser.ast.relational_algebra;

public class AndPredicate implements Predicate {
  private final Predicate left;
  private final Predicate right;

  public AndPredicate(Predicate left, Predicate right) {
    this.left = left;
    this.right = right;
  }
}
