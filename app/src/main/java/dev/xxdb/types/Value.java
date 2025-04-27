package dev.xxdb.types;

public sealed interface Value permits StringValue, IntValue {
  /**
   * Implement arithmetic comparation to another value
   * @param op: which arithmetic to compare
   * @param other: who to compare
   * @return True if this Value is `op` than other
   */
  boolean compareTo(Ops op, Value other);

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
   * @return byte array with size size()
   */
  byte[] getData();
}
