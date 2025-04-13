package dev.xxdb.parser.ast.expression;

public class StringValue implements ValueNode {
  public final String value;

  public StringValue(String value) {
    this.value = value;
  }

  @Override
  public String toString() {
    return "StringValue: " + value;
  }

  @Override
  public void accept(ExpressionVisitor visitor) {
    throw new RuntimeException("unimplemented");
//    visitor.visitStringValueNode(this);
  }
}
