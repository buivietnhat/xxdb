package dev.xxdb.parser.ast.statement;

public class OrCondition implements Condition{
  private final Statement left;
  private final Statement right;

  public OrCondition(Statement left, Statement right) {
    this.left = left;
    this.right = right;
  }

  public Statement getLeft() {
    return left;
  }

  public Statement getRight() {
    return right;
  }

  @Override
  public void accept(StatementVisitor visitor) {
    visitor.visitOrConditionNode(this);
  }

  @Override
  public String toString() {
    return "OrCondition{" +
        "left=" + left +
        ", right=" + right +
        '}';
  }
}
