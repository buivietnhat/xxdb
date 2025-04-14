package dev.xxdb.parser.ast.expression;

public class ColumnDefinition implements Expression {
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
  public void accept(ExpressionVisitor visitor) {
    visitor.visitColumnDefinitionNode(this);
  }
}
