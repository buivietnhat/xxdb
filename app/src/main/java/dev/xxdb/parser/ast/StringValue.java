package dev.xxdb.parser.ast;

public class StringValue implements ValueNode {
  public final String value;

  public StringValue(String value) {
    this.value = value;
  }

  @Override
  public String toString() {
    return "StringValue: " + value;
  }
}
