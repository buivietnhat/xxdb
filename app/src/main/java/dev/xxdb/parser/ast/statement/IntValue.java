package dev.xxdb.parser.ast.statement;

public class IntValue implements Value {
  public final int value;

  public int getValue() {
    return value;
  }

  public IntValue(int value) {
    this.value = value;
  }

  @Override
  public String toString() {
    return "Int: " + value;
  }

  @Override
  public void accept(StatementVisitor visitor) {
    visitor.visitIntValueNode(this);
  }
}
