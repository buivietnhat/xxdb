package dev.xxdb.types;

import java.nio.ByteBuffer;

public record IntValue(int value) implements Value {
  @Override
  public boolean compareTo(Op op, Value other) {
    if (!(other instanceof IntValue)) {
      throw new RuntimeException("Cannot compare InValue with different type");
    }

    int otherVal = ((IntValue) other).value;

    switch (op) {
      case EQUALS -> {
        return value == otherVal;
      }
      case NOT_EQUALS -> {
        return value != otherVal;
      }
      case GREATER_THAN -> {
        return value > otherVal;
      }
      case LESS_THAN -> {
        return value < otherVal;
      }
      case GREATER_THAN_OR_EQUAL -> {
        return value >= otherVal;
      }
      case LESS_THAN_OR_EQUAL -> {
        return value <= otherVal;
      }
    }
    throw new RuntimeException("Unrecognized op for comparing");
  }

  @Override
  public TypeId getTypeId() {
    return TypeId.INTEGER;
  }

  @Override
  public int size() {
    return 4;
  }

  @Override
  public byte[] getData() {
    ByteBuffer buffer = ByteBuffer.allocate(INT_SIZE);
    buffer.putInt(value);
    return buffer.array();
  }
}
