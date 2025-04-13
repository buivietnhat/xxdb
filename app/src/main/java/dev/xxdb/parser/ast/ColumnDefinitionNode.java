package dev.xxdb.parser.ast;

public class ColumnDefinitionNode implements LogicalPlan {
  private final String columnName;
  private final String dataType;

  public ColumnDefinitionNode(String columnName, String dataType) {
    this.columnName = columnName;
    this.dataType = dataType;
  }

  @Override
  public String toString() {
    return "(" + columnName + ":" + dataType + ")";
  }
}
