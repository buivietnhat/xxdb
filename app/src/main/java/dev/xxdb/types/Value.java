package dev.xxdb.types;

public sealed interface Value permits StringValue, IntValue {
  boolean compareTo(Ops op, Value other);
}
