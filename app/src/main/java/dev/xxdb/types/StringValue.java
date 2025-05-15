package dev.xxdb.types;

import java.nio.charset.StandardCharsets;

public final class StringValue implements Value {
  private final String value;
  private final int originalLength;

  public static String padRight(String s, int length) {
    if (s.length() >= length) return s.substring(0, length);
    return s + " ".repeat(length - s.length());
  }

  public StringValue(String value) {
    this.value = padRight(value, VARCHAR_MAX_SIZE);
    this.originalLength = value.length();
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

  @Override
  public String toString() {
    return "StringValue[" + "value=" + value.substring(0, originalLength) + ']';
  }
}
