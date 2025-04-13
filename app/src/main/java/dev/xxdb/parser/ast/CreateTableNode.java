package dev.xxdb.parser.ast;

public class CreateTableNode implements LogicalPlan {
  private final String tableName;
  private final LogicalPlan columnDefinitionList;

  public CreateTableNode(String tableName, LogicalPlan columnDefinitionList) {
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
}
