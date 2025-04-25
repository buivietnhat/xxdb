package dev.xxdb.parser.ast.statement;

import java.util.Collections;
import java.util.List;

public class ColumnList implements Statement {
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
  public void accept(StatementVisitor visitor) {
    visitor.visitColumnListNode(this);
  }
}
