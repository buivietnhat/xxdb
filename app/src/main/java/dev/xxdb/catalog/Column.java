package dev.xxdb.catalog;

import dev.xxdb.types.TypeId; /**
 * @param name:        name of the column
 * @param typeId:      type representation of the column
 * @param tupleOffset: offset to interpret bytes of data of this column in a tuple
 * @param length:      how many bytes this column uses to interpret data
 */
public record Column(String name, TypeId typeId, int tupleOffset, int length) {
  public Column withName(String newName) {
    return new Column(newName, this.typeId, this.tupleOffset, this.length);
  }
}
