package dev.xxdb.parser.ast.statement;

public class SelectBuilder {
  private final Select select = new Select();

  public void setTableName(String tableName) {
    select.setTableName(tableName);
  }

  public void setColumnList(Statement columnList) {
    select.setColumnList(columnList);
  }

  public void setJoinClause(Statement joinClause) {
    select.setJoinClause(joinClause);
  }

  public void setWhereClause(Statement whereClause) {
    select.setWhereClause(whereClause);
  }

  public void setLimitClause(Statement limitClause) {
    select.setLimitClause(limitClause);
  }

  public Select build() {
    return select;
  }
}
