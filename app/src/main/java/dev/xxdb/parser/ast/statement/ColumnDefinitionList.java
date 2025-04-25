package dev.xxdb.parser.ast.statement;

import java.util.Collections;
import java.util.List;

public class ColumnDefinitionList implements Statement {
  private final List<Statement> columnDefinitions;

  public ColumnDefinitionList(List<Statement> columnDefinitions) {
    this.columnDefinitions = columnDefinitions;
  }

  public List<Statement> getColumnDefinitions() {
    return Collections.unmodifiableList(columnDefinitions);
  }

  @Override
  public String toString() {
    StringBuilder rep = new StringBuilder();
    for (Statement child : columnDefinitions) {
      rep.append(child.toString()).append(" ");
    }
    return rep.toString();
  }

  @Override
  public void accept(StatementVisitor visitor) {
    visitor.visitColumnDefinitionListNode(this);
  }
}
