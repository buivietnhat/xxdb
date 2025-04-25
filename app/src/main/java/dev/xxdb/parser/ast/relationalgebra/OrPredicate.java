package dev.xxdb.parser.ast.relationalgebra;

public class OrPredicate implements Predicate {

  @Override
  public <T> T accept(PredicateVisitor<T> visitor) {
    throw new RuntimeException("unimplemented");
  }
}
