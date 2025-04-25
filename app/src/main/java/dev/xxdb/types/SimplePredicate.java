package dev.xxdb.types;

import dev.xxdb.catalog.Schema;
import dev.xxdb.storage.tuple.Tuple;

public class SimplePredicate implements Predicate {
  private final String column;
  private final Value value;
  private final Ops op;

  public SimplePredicate(String column, Value value, Ops op) {
    this.column = column;
    this.value = value;
    this.op = op;
  }

  @Override
  public boolean evaluate(Schema schema, Tuple tuple) {
    return tuple.getValue(schema, column).compareTo(op, value);
  }

  @Override
  public String toString() {
    return "SimplePredicate{" +
        "column='" + column + '\'' +
        ", value=" + value +
        ", op=" + op +
        '}';
  }
}
