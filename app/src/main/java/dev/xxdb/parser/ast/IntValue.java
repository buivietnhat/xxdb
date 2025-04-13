package dev.xxdb.parser.ast;

public class IntValue implements ValueNode {
  public final int value;

  public IntValue(int value) {
    this.value = value;
  }

  @Override
  public String toString() {
    return "IntValue: " + value;
  }
}
