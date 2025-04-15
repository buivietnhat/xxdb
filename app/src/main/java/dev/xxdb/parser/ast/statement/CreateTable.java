package dev.xxdb.parser.ast.statement;

public class CreateTable implements Statement {
  private final String tableName;
  private final Statement columnDefinitionList;

  public String getTableName() {
    return tableName;
  }

  public Statement getColumnDefinitionList() {
    return columnDefinitionList;
  }

  public CreateTable(String tableName, Statement columnDefinitionList) {
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
  public void accept(StatementVisitor visitor) {
    visitor.visitCreateTableNode(this);
  }
}
