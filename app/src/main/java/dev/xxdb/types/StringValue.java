package dev.xxdb.types;

import java.nio.charset.StandardCharsets;

public record StringValue(String value) implements Value {
  public static String padRight(String s, int length) {
    if (s.length() >= length) return s.substring(0, length);
    return s + " ".repeat(length - s.length());
  }

  public StringValue(String value) {
    this.value = padRight(value, VARCHAR_MAX_SIZE);
  }

  @Override
  public boolean compareTo(Ops op, Value other) {
    if (!(other instanceof StringValue)) {
      throw new RuntimeException("Cannot compare StringValue with different type");
    }

    String otherVal = ((StringValue) other).value;

    switch (op) {
      case EQUALS -> {
        return value.equals(otherVal);
      }
      case NOT_EQUALS -> {
        return !value.equals(otherVal);
      }
      case GREATER_THAN -> {
        throw new RuntimeException("Not supported for compare > with String");
      }
      case LESS_THAN -> {
        throw new RuntimeException("Not supported for compare < with String");
      }
      case GREATER_THAN_OR_EQUAL -> {
        throw new RuntimeException("Not supported for compare >= with String");
      }
      case LESS_THAN_OR_EQUAL -> {
        throw new RuntimeException("Not supported for compare <= with String");
      }
    }
    throw new RuntimeException("Unrecognized op for comparing");
  }

  @Override
  public TypeId getTypeId() {
    return TypeId.VARCHAR;
  }

  @Override
  public int size() {
    return value.length();
  }

  @Override
  public byte[] getData() {
    return value.getBytes(StandardCharsets.UTF_8);
  }
}
