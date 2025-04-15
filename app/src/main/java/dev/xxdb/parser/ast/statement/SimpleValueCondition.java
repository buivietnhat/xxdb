package dev.xxdb.parser.ast.statement;

public class SimpleValueCondition implements Condition{
  private final String columnName;
  private final Statement operator;
  private final Statement value;

  public SimpleValueCondition(String columnName, Statement operator, Statement value) {
    this.columnName = columnName;
    this.operator = operator;
    this.value = value;
  }

  @Override
  public void accept(StatementVisitor visitor) {
    visitor.visitSimpleValueConditionNode(this);
  }

  @Override
  public String toString() {
    return "SimpleCondition{" +
        "columnName='" + columnName + '\'' +
        ", operator=" + operator +
        ", value=" + value +
        '}';
  }
}
