package dev.xxdb.parser.ast.statement;

public class ColumnDefinition implements Statement {
  private final String columnName;
  private final String dataType;

  public ColumnDefinition(String columnName, String dataType) {
    this.columnName = columnName;
    this.dataType = dataType;
  }

  public String getDataType() {
    return dataType;
  }

  public String getColumnName() {
    return columnName;
  }

  @Override
  public String toString() {
    return "(" + columnName + ":" + dataType + ")";
  }

  @Override
  public void accept(StatementVisitor visitor) {
    visitor.visitColumnDefinitionNode(this);
  }
}
