package dev.xxdb.parser.ast.expression;

public class ColumnDefinitionNode implements Expression {
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

  @Override
  public void accept(ExpressionVisitor visitor) {
    visitor.visitColumnDefinitionNode(this);
  }
}
