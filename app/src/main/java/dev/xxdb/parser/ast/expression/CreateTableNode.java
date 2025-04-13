package dev.xxdb.parser.ast.expression;

public class CreateTableNode implements Expression {
  private final String tableName;
  private final Expression columnDefinitionList;

  public CreateTableNode(String tableName, Expression columnDefinitionList) {
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
