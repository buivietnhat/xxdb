package dev.xxdb.catalog;

import dev.xxdb.storage.file.TableHeap;

import java.util.List;
import java.util.Optional;

public class Catalog {
  /**
   * Retrieve the schema information a table
   * @param tableName: name of the table
   * @return the schema
   */
  public Optional<Schema> getTableSchema(String tableName) {
    throw new RuntimeException("unimplemeted");
  }

  /**
   * Create a new table
   * @param tableName name of the table
   * @param columns column names in that table
   * @param types type definition for each column
   * @return true if created successfully, false if the table name already exist
   */
  public boolean createNewTable(String tableName, List<String> columns, List<String> types) {
    throw new RuntimeException("unimplemeted");
  }

  public Optional<TableHeap> getTable(String table) {
    throw new RuntimeException("unimplemeted");
  }
}
