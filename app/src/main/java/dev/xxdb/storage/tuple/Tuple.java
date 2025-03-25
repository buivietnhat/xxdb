package dev.xxdb.storage.tuple;

import java.util.Arrays;
import java.util.Objects;

class TupleHeader {}

public class Tuple {
  private final byte[] data;

  public Tuple(byte[] data) {
    this.data = Arrays.copyOf(data, data.length);
  }

  /** Get bytes representation of this tuple */
  public byte[] getData() {
    return Arrays.copyOf(data, data.length);
  }

  /** Return size of this tuple in bytes */
  public int getSize() {
    return data.length;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Tuple tuple = (Tuple) o;
    return Objects.deepEquals(data, tuple.data);
  }

  @Override
  public int hashCode() {
    return Arrays.hashCode(data);
  }
}
