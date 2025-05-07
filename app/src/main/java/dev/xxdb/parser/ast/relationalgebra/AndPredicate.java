package dev.xxdb.parser.ast.relationalgebra;

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
}
