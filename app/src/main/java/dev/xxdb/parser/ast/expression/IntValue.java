package dev.xxdb.parser.ast.expression;

public class IntValue implements Value {
  public final int value;

  public IntValue(int value) {
    this.value = value;
  }

  @Override
  public String toString() {
    return "IntValue: " + value;
  }

  @Override
  public void accept(ExpressionVisitor visitor) {
    visitor.visitIntValueNode(this);
  }
}
