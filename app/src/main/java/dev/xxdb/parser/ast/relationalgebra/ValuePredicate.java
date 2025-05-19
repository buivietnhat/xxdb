package dev.xxdb.parser.ast.relationalgebra;

import dev.xxdb.types.Op;
import dev.xxdb.types.Value;

public class ValuePredicate implements Predicate {
  private Op op;
  private String column;
  private Value value;

  public Op getOp() {
    return op;
  }

  public void setOp(Op op) {
    this.op = op;
  }

  public String getColumn() {
    return column;
  }

  public void setColumn(String column) {
    this.column = column;
  }

  public Value getValue() {
    return value;
  }

  public void setValue(Value value) {
    this.value = value;
  }

  @Override
  public String toString() {
    return "ValuePredicate{" + "op=" + op + ", column='" + column + '\'' + ", value=" + value + '}';
  }

  @Override
  public <T> T accept(PredicateVisitor<T> visitor) {
    return visitor.visitValuePredicate(this);
  }

  public ValuePredicate() {}

  public ValuePredicate(Op op, String column, Value value) {
    this.op = op;
    this.column = column;
    this.value = value;
  }
}
