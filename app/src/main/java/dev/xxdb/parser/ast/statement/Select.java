package dev.xxdb.parser.ast.statement;

import java.util.Optional;

public class Select implements Statement {
  private String tableName;
  private Statement columnList;
  private Optional<Statement> joinClause = Optional.empty();
  private Optional<Statement> whereClause = Optional.empty();
  private Optional<Statement> limitClause = Optional.empty();

  public String getTableName() {
    return tableName;
  }

  public void setTableName(String tableName) {
    this.tableName = tableName;
  }

  public Statement getColumnList() {
    return columnList;
  }

  public void setColumnList(Statement columnList) {
    this.columnList = columnList;
  }

  public Optional<Statement> getJoinClause() {
    return joinClause;
  }

  public void setJoinClause(Statement joinClause) {
    this.joinClause = Optional.of(joinClause);
  }

  public Optional<Statement> getWhereClause() {
    return whereClause;
  }

  public void setWhereClause(Statement whereClause) {
    this.whereClause = Optional.of(whereClause);
  }

  public Optional<Statement> getLimitClause() {
    return limitClause;
  }

  public void setLimitClause(Statement limitClause) {
    this.limitClause = Optional.of(limitClause);
  }

  @Override
  public void accept(StatementVisitor visitor) {
    visitor.visitSelectNode(this);
  }

  @Override
  public String toString() {
    String str = "Select{" +
        "tableName='" + tableName + '\'' +
        ", columnList=" + columnList;
    if (joinClause.isPresent()) {
      str += ", joinClause=" + joinClause.get();
    }
    if (whereClause.isPresent()) {
      str += ", whereClause=" + whereClause.get();
    }

    if (limitClause.isPresent()) {
      str += ", limitClause=" + limitClause.get();
    }
    str += "}";
    return str;
  }
}

