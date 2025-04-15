package dev.xxdb.parser.ast.relational_algebra;

import dev.xxdb.types.Ops;
import dev.xxdb.types.Value;

public class ValuePredicate implements Predicate {
  private final Ops op;
  private final String column;
  private final Value value;


  public ValuePredicate(Ops op, String column, Value value) {
    this.op = op;
    this.column = column;
    this.value = value;
  }
}
