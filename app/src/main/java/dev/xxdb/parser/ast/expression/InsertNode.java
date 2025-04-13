package dev.xxdb.parser.ast.expression;

import java.util.List;

public class InsertNode implements Expression {
  private final String tableName;
  private final List<String> columns;
  private final List<ValueNode> values;

  public InsertNode(String tableName, List<String> columns, List<ValueNode> values) {
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
