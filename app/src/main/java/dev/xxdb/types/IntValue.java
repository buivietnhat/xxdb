package dev.xxdb.types;

public record IntValue(int value) implements Value {
  @Override
  public boolean compareTo(Ops op, Value other) {
    throw new RuntimeException("unimplemented");
  }
}
