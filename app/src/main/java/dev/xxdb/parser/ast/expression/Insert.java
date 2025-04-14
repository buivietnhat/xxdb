package dev.xxdb.parser.ast.expression;

import java.util.List;

public class Insert implements Expression {
  private final String tableName;
  private final Expression columns;
  private final Expression values;

  public String getTableName() {
    return tableName;
  }

  public Expression getColumns() {
    return columns;
  }

  public Expression getValues() {
    return values;
  }

  public Insert(String tableName, Expression columns, Expression values) {
    this.tableName = tableName;
    this.columns = columns;
    this.values = values;
  }

  @Override
  public String toString() {
    StringBuilder rep = new StringBuilder("Insert ");
    rep.append(columns.toString()).append(" ");
    rep.append(values.toString());
    return rep.toString();
  }

  @Override
  public void accept(ExpressionVisitor visitor) {
    visitor.visitInsertNode(this);
  }
}
