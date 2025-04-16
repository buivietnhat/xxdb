package dev.xxdb.catalog;

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

  public boolean createNewTable(String tableName, List<String> columns, List<String> types) {
    throw new RuntimeException("unimplemeted");
  }
}
