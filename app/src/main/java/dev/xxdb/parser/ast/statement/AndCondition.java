package dev.xxdb.parser.ast.statement;

public class AndCondition implements Condition{
  private final Statement left;
  private final Statement right;

  public AndCondition(Statement left, Statement right) {
    this.left = left;
    this.right = right;
  }

  @Override
  public void accept(StatementVisitor visitor) {
    visitor.visitAndConditionNode(this);
  }

  @Override
  public String toString() {
    return "AndCondition{" +
        "left=" + left +
        ", right=" + right +
        '}';
  }
}
