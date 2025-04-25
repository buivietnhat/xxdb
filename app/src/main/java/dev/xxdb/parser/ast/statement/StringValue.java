package dev.xxdb.parser.ast.statement;

public class StringValue implements Value {
  public final String value;

  public StringValue(String value) {
    // remove the double ' at the beginning and the end
    this.value = value.substring(1, value.length() - 1);
  }

  public String getValue() {
    return value;
  }

  @Override
  public String toString() {
    return "String: " + value;
  }

  @Override
  public void accept(StatementVisitor visitor) {
    visitor.visitStringValueNode(this);
  }
}
