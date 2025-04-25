package dev.xxdb.parser.ast.statement;

public class SimpleColumnCondition implements Condition {
  private final String columnName1;
  private final String columnName2;
  private final Statement operator;

  public SimpleColumnCondition(String columnName1, String columnName2, Statement operator) {
    this.columnName1 = columnName1;
    this.columnName2 = columnName2;
    this.operator = operator;
  }

  public String getColumnName1() {
    return columnName1;
  }

  public String getColumnName2() {
    return columnName2;
  }

  public Statement getOperator() {
    return operator;
  }

  @Override
  public void accept(StatementVisitor visitor) {
    visitor.visitSimpleColumnConditionNode(this);
  }

  @Override
  public String toString() {
    return "SimpleColumnCondition{" +
        "columnName1='" + columnName1 + '\'' +
        ", columnName2='" + columnName2 + '\'' +
        ", operator=" + operator +
        '}';
  }
}
