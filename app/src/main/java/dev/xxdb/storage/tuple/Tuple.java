package dev.xxdb.storage.tuple;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

import dev.xxdb.catalog.Column;
import dev.xxdb.catalog.Schema;
import dev.xxdb.types.TypeId;
import dev.xxdb.types.Value;

class TupleHeader {}

public class Tuple {
  private final byte[] data;

  public static class Builder {
    private final ByteArrayOutputStream byteArray = new ByteArrayOutputStream();

    public Builder addIntegerColumn(byte[] bytes) {
      try {
        byteArray.write(bytes);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
      return this;
    }

    public Builder addVarcharColumn(byte[] bytes) {
      if (bytes.length > Value.VARCHAR_MAX_SIZE) {
        throw new RuntimeException("exceed varchar max size (" + Value.VARCHAR_MAX_SIZE + ")");
      }
      try {
        byteArray.write(bytes);
        // padding
        byteArray.write(new byte[Value.VARCHAR_MAX_SIZE - bytes.length]);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
      return this;
    }

    public Builder addColumn(TypeId typeId, byte[] bytes) {
      switch (typeId) {
        case BOOLEAN -> {
          throw new RuntimeException("unimplemented");
        }
        case INTEGER -> {
          return addIntegerColumn(bytes);
        }
        case DECIMAL -> {
          throw new RuntimeException("unimplemented");
        }
        case VARCHAR -> {
          return addVarcharColumn(bytes);
        }
        case DATETIME -> {
          throw new RuntimeException("unimplemented");
        }
      }
      throw new RuntimeException("unregconized type");
    }

    public Tuple build() {
      Tuple tuple = new Tuple(byteArray.toByteArray());
      byteArray.reset();
      return tuple;
    }
  }

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

  private Value getColumnValue(int offset, int length, TypeId type) {
    byte[] cols = Arrays.copyOfRange(data, offset, offset + length);
    switch (type) {
      case BOOLEAN -> {
        throw new RuntimeException("unimplemented");
      }
      case INTEGER -> {
        return Value.makeInt(cols);
      }
      case DECIMAL -> {
        throw new RuntimeException("unimplemented");
      }
      case VARCHAR -> {
        return Value.makeVarchar(cols);
      }
      case DATETIME -> {
        throw new RuntimeException("unimplemented");
      }
    }
    throw new RuntimeException("Unrecognized type of column");
  }

  public Value getValue(Schema schema, String column) {
    for (Column col : schema.getColumns()) {
      if (col.name().equals(column)) {
        return getColumnValue(col.tupleOffset(), col.length(), col.typeId());
      }
    }
    throw new RuntimeException("Cannot find the column: " + column);
  }

}
