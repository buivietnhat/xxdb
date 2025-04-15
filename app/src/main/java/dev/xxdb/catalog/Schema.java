package dev.xxdb.catalog;

import dev.xxdb.types.TypeId;
import java.util.List;

/**
 * @param name: name of the column
 * @param typeId: type representation of the column
 * @param tupleOffset: offset to interpret bytes of data of this column in a tuple
 * @param length: how many bytes this column uses to interpret data
 */
record Column(String name, TypeId typeId, int tupleOffset, int length) {}

public class Schema {
  // private final List<Column> columns;

  // public Schema()

  public List<Column> getColumns() {
    throw new RuntimeException("unimplemented");
  }
}
