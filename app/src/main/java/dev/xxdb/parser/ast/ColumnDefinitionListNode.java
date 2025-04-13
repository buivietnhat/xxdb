package dev.xxdb.parser.ast;

import java.util.List;

public class ColumnDefinitionListNode implements LogicalPlan {
  private final List<LogicalPlan> columnDefinitions;

  public ColumnDefinitionListNode(List<LogicalPlan> columnDefinitions) {
    this.columnDefinitions = columnDefinitions;
  }

  @Override
  public String toString() {
    StringBuilder rep = new StringBuilder();
    for (LogicalPlan child : columnDefinitions) {
      rep.append(child.toString()).append(" ");
    }
    return rep.toString();
  }
}
