package dev.xxdb.parser.ast.statement;

public class Join implements Statement {
  private final String tableName;
  private final Statement condition;

  public Join(String tableName, Statement condition) {
    this.tableName = tableName;
    this.condition = condition;
  }

  public String getTableName() {
    return tableName;
  }

  public Statement getCondition() {
    return condition;
  }

  @Override
  public void accept(StatementVisitor visitor) {
    visitor.visitJoinNode(this);
  }

  @Override
  public String toString() {
    return "Join{" +
        "tableName='" + tableName + '\'' +
        ", condition=" + condition +
        '}';
  }
}
