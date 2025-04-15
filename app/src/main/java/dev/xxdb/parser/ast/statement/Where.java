package dev.xxdb.parser.ast.statement;

public class Where implements Statement {
  private final Statement condition;

  public Where(Statement condition) {
    this.condition = condition;
  }

  @Override
  public void accept(StatementVisitor visitor) {
    visitor.visitWhereNode(this);
  }

  @Override
  public String toString() {
    return "Where{" +
        "condition=" + condition +
        '}';
  }
}
