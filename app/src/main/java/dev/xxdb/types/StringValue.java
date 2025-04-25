package dev.xxdb.types;

public record StringValue(String value) implements Value {
  @Override
  public boolean compareTo(Ops op, Value other) {
    throw new RuntimeException("unimplemented");
  }
}
