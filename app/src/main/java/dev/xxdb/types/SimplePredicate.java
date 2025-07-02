package dev.xxdb.types;

import dev.xxdb.catalog.Catalog;
import dev.xxdb.catalog.Schema;
import dev.xxdb.storage.tuple.Tuple;

public class SimplePredicate implements Predicate {
  private final String table;
  private final String column;
  private final Value value;
  private final Op op;
  private final Schema schema;

  public SimplePredicate(String table, String column, Value value, Op op, Catalog catalog) {
    this.table = table;
    if (column.contains(".")) {
      this.column = column;
    } else {
      this.column = table + "." + column;
    }
    this.value = value;
    this.op = op;
    this.schema = catalog.getTableSchema(table).get();
  }

  @Override
  public boolean evaluate(Tuple tuple) {
    return tuple.getValue(schema, column).compareTo(op, value);
  }

  @Override
  public String toString() {
    return "SimplePredicate{"
        + "table='"
        + table
        + '\''
        + ", column='"
        + column
        + '\''
        + ", value="
        + value
        + ", op="
        + op
        + '}';
  }
}
