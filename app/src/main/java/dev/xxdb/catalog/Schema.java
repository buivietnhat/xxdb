package dev.xxdb.catalog;

import dev.xxdb.types.TypeId;
import dev.xxdb.types.Value;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Schema {
   private final List<Column> columns;

   public static class Builder {
      private Schema schema = new Schema();
      private int currentColumnOffset = 0;

     /**
      * Add a new integer column to the current schema
      * @param columnName: name of the column
      */
      public Builder addIntColumn(String columnName) {
        schema.addColumn(new Column(columnName, TypeId.INTEGER, currentColumnOffset, Value.INT_SIZE));
        currentColumnOffset += Value.INT_SIZE;
        return this;
      }

     /**
      * Add a new varchar column to the current schema
      * @param columnName: name of the column
      */
     public Builder addVarcharColumn(String columnName) {
       schema.addColumn(new Column(columnName, TypeId.VARCHAR, currentColumnOffset, Value.VARCHAR_MAX_SIZE));
       currentColumnOffset += Value.VARCHAR_MAX_SIZE;
       return this;
     }

     public Builder addColumn(TypeId typeId, String columnName) {
       switch (typeId) {
         case BOOLEAN -> {
           throw new RuntimeException("unimplemented");
         }
         case INTEGER -> {
           return addIntColumn(columnName);
         }
         case DECIMAL -> {
           throw new RuntimeException("unimplemented");
         }
         case VARCHAR -> {
           return addVarcharColumn(columnName);
         }
         case DATETIME -> {
           throw new RuntimeException("unimplemented");
         }
       }
       throw new RuntimeException("unregconized type");
     }

     public Schema build() {
       Schema clone = schema;
       schema = new Schema();
       currentColumnOffset = 0;
       return clone;
     }
   }

   private Schema() {
     columns = new ArrayList<>();
   }

  /**
   * Produce new Schema by joining this schema and another
   * @param other: the other Schema to join
   * @return new schema
   */
   public Schema join(final Schema other) {
     List<Column> clone = new ArrayList<>(columns.stream()
         .toList());

     int currentOffset = clone.getLast().tupleOffset() + clone.getLast().length();
     for (Column col : other.getColumns()) {
       clone.add(new Column(col.name(), col.typeId(), currentOffset, col.length()));
       currentOffset += col.length();
     }

     return new Schema(clone);
   }

  /**
   * Produce new Schema by only keep a subset of columns of this Schema
   * @param keptCols: list of columns to keep
   * @return new Schema
   */
   public Schema filter(List<String> keptCols) {
     Builder builder = new Builder();

     keptCols.stream()
         .map(col -> columns.stream()
             .filter(column -> column.name().equals(col))
             .findFirst()
             .get()
         )
         .forEach(col -> builder.addColumn(col.typeId(), col.name()));

     return builder.build();
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

  public boolean containsColumn(String column) {
     return columns.stream()
         .anyMatch(col -> col.name().equals(column));
  }

  @Override
  public String toString() {
    return "Schema{" +
        "columns=" + columns +
        '}';
  }
}
