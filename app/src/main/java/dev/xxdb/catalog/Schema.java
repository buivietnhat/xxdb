package dev.xxdb.catalog;

import dev.xxdb.types.TypeId;
import dev.xxdb.types.Value;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Schema {
   private final List<Column> columns;

   public static class Builder {
      private final Schema schema = new Schema();
      private int currentColumnOffset = 0;

     /**
      * Add a new integer column to the current schema
      * @param columnName: name of the column
      */
      public void addIntColumn(String columnName) {
        schema.addColumn(new Column(columnName, TypeId.INTEGER, currentColumnOffset, Value.INT_SIZE));
        currentColumnOffset += Value.INT_SIZE;
      }

     /**
      * Add a new varchar column to the current schema
      * @param columnName: name of the column
      */
     public void addVarcharColumn(String columnName) {
       schema.addColumn(new Column(columnName, TypeId.VARCHAR, currentColumnOffset, Value.VARCHAR_MAX_SIZE));
       currentColumnOffset += Value.VARCHAR_MAX_SIZE;
     }

     public Schema build() {
       return schema;
     }
   }

   private Schema() {
     columns = new ArrayList<>();
   }

   private void addColumn(Column column) {
     columns.add(column);
   }

  public Schema(List<Column> columns) {
    this.columns = columns;
  }

  public List<Column> getColumns() {
    return Collections.unmodifiableList(columns);
  }
}
