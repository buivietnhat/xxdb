package dev.xxdb.catalog;

import dev.xxdb.storage.file.TableHeap;
import dev.xxdb.storage.page.SlottedPageRepository;
import dev.xxdb.types.Value;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class Catalog {
  private class TableInfo {
    public final Schema schema;
    public final TableHeap tableHeap;

    public TableInfo(Schema schema, TableHeap tableHeap) {
      this.schema = schema;
      this.tableHeap = tableHeap;
    }
  }

  private final HashMap<String, TableInfo> tableInfo = new HashMap<>();
  private final SlottedPageRepository slottedPageRepository;

  public Catalog(SlottedPageRepository slottedPageRepository) {
    this.slottedPageRepository = slottedPageRepository;
  }

  /**
   * Retrieve the schema information a table
   * @param tableName: name of the table
   * @return the schema
   */
  public Optional<Schema> getTableSchema(String tableName) {
    if (tableInfo.containsKey(tableName)) {
      return Optional.of(tableInfo.get(tableName).schema);
    }
    return Optional.empty();
  }

  /**
   * Create a new table
   * @param tableName name of the table
   * @param columns column names in that table
   * @param types type definition for each column
   * @return true if created successfully, false if the table name already exist
   */
  public boolean createNewTable(String tableName, List<String> columns, List<String> types) {
    if (tableInfo.containsKey(tableName)) {
      return false;
    }

    Schema.Builder schemaBuilder = new Schema.Builder();
    for (int i = 0; i < columns.size(); i++) {
      schemaBuilder.addColumn(Value.getTypeId(types.get(i)), columns.get(i));
    }
    Schema schema = schemaBuilder.build();
    TableHeap tableHeap = new TableHeap(slottedPageRepository);
    tableInfo.put(tableName, new TableInfo(schema, tableHeap));
    return true;
  }

  /**
   * Get the table heap corresponding to SQL Table
   * @param table name of the table
   */
  public Optional<TableHeap> getTable(String table) {
    if (tableInfo.containsKey(table)) {
      return Optional.of(tableInfo.get(table).tableHeap);
    }
    return Optional.empty();
  }
}
