package dev.xxdb.types;

import java.nio.ByteBuffer;

public record IntValue(int value) implements Value {
  @Override
  public boolean compareTo(Ops op, Value other) {
    throw new RuntimeException("unimplemented");
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
    ByteBuffer buffer = ByteBuffer.allocate(4);
    buffer.putInt(value);
    return buffer.array();
  }


}
