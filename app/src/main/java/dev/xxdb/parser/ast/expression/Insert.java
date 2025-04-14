package dev.xxdb.parser.ast.expression;

import java.util.List;

public class Insert implements Expression {
  private final String tableName;
  private final List<String> columns;
  private final List<Value> values;

  public Insert(String tableName, List<String> columns, List<Value> values) {
    this.tableName = tableName;
    this.columns = columns;
    this.values = values;
  }

  @Override
  public String toString() {
    throw new RuntimeException("unimplemented");
  }

  @Override
  public void accept(ExpressionVisitor visitor) {
    visitor.visitInsertNode(this);
  }
}
