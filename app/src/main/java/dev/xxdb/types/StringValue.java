package dev.xxdb.types;

import java.nio.charset.StandardCharsets;

public record StringValue(String value) implements Value {
  @Override
  public boolean compareTo(Ops op, Value other) {
    throw new RuntimeException("unimplemented");
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
