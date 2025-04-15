package dev.xxdb.parser.ast.statement;

public class Limit implements Statement {
  private final int number;

  public Limit(int number) {
    this.number = number;
  }

  @Override
  public void accept(StatementVisitor visitor) {
    visitor.visitLimitNode(this);
  }

  @Override
  public String toString() {
    return "Limit{" +
        "number=" + number +
        '}';
  }
}
