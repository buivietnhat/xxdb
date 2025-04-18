package dev.xxdb.parser.ast.relationalgebra;

public interface PredicateVisitor<T> {
  T visitAndPredicate(AndPredicate predicate);

  T visitOrPredicate(OrPredicate predicate);

  T visitValuePredicate(ValuePredicate predicate);
}
