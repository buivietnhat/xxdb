package dev.xxdb.storage.tuple;

public abstract class Value {
  /** Get the type id representation of given value */
  public TypeId getType() {
    throw new RuntimeException("unimplemented");
  }

  // /** Return the serialization format for the underlying type */
  // public abstract char[] getSerializedData();

  /** Factory method for creating Boolean value */
  public static Value makeBoolean(boolean val) {
    throw new RuntimeException("unimplemented");
  }

  /** Factory method for creating Integer value */
  public static Value makeInteger(int val) {
    throw new RuntimeException("unimplemented");
  }

  /** Factory method for creating Varchar value */
  public static Value makeVarchar(String val) {
    throw new RuntimeException("unimplemented");
  }
}

// class BooleanValue extends Value {
// }
//
// class IntegerValue extends Value {
// }
//
// class DecimalValue extends Value {
// }
//
// class VarcharValue extends Value {
// }
