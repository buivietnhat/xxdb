package dev.xxdb.types;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public sealed interface Value permits StringValue, IntValue {
  int INT_SIZE = 4;
  int VARCHAR_MAX_SIZE = 100;

  /**
   * Implement arithmetic comparation to another value
   *
   * @param op: which arithmetic to compare
   * @param other: who to compare
   * @return True if this Value is `op` than other
   */
  boolean compareTo(Op op, Value other);

  /**
   * @return the Type ID of this Value
   */
  TypeId getTypeId();

  /**
   * @return number of bytes to represent this value
   */
  int size();

  /**
   * Get bytes representation of this value
   *
   * @return byte array with size size()
   */
  byte[] getData();

  /**
   * Convert String representation of types to the actual TypeId system
   *
   * @param type: String representaiton of the type
   * @return TypeId object
   */
  static TypeId getTypeId(String type) {
    switch (type) {
      case "INT":
        return TypeId.INTEGER;
      case "VARCHAR":
        return TypeId.VARCHAR;
      default:
        throw new RuntimeException("unregconized type");
    }
  }

  /**
   * Factory method to create an Integer type
   *
   * @param data serialized data for the type
   */
  static IntValue makeInt(byte[] data) {
    ByteBuffer buffer = ByteBuffer.wrap(data);
    return new IntValue(buffer.getInt());
  }

  /**
   * Factory method to create a Varchar type
   *
   * @param data serialized data for the type
   */
  static StringValue makeVarchar(byte[] data) {
    ByteBuffer buffer = ByteBuffer.wrap(data);
    Charset charset = StandardCharsets.UTF_8;
    return new StringValue(charset.decode(buffer).toString());
  }
}
