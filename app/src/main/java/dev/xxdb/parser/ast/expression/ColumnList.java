package dev.xxdb.parser.ast.expression;

import java.util.Collections;
import java.util.List;

public class ColumnList implements Expression {
  private final List<String> columns;

  public ColumnList(List<String> columns) {
    this.columns = columns;
  }

  public List<String> getColumns() {
    return Collections.unmodifiableList(columns);
  }

  @Override
  public String toString() {
    StringBuilder rep = new StringBuilder();
    rep.append("(");
    for (String column : columns) {
      rep.append(column).append(" ");
    }
    rep.deleteCharAt(rep.length() - 1);
    rep.append(")");
    return rep.toString();
  }

  @Override
  public void accept(ExpressionVisitor visitor) {
    visitor.visitColumnListNode(this);
  }
}
