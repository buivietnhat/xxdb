package dev.xxdb.parser.ast.relationalgebra;

public interface Predicate {
  <T> T accept(PredicateVisitor<T> visitor);
}
