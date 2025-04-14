package dev.xxdb.parser.ast.expression;

public class CreateTable implements Expression {
  private final String tableName;
  private final Expression columnDefinitionList;

  public String getTableName() {
    return tableName;
  }

  public Expression getColumnDefinitionList() {
    return columnDefinitionList;
  }

  public CreateTable(String tableName, Expression columnDefinitionList) {
    this.tableName = tableName;
    this.columnDefinitionList = columnDefinitionList;
  }

  @Override
  public String toString() {
    StringBuilder rep = new StringBuilder("CreateTableNode ");
    rep.append("tableName:").append(tableName).append(" ");
    rep.append(columnDefinitionList.toString());
    return rep.toString();
  }

  @Override
  public void accept(ExpressionVisitor visitor) {
    visitor.visitCreateTableNode(this);
  }
}
