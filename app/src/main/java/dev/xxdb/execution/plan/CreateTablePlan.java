package dev.xxdb.execution.plan;

import java.util.List;

public class CreateTablePlan extends PhysicalPlan {
  private final String tableName;
  private final List<String> columns;
  private final List<String> types;

  public String getTableName() {
    return tableName;
  }

  public List<String> getColumns() {
    return columns;
  }

  public List<String> getTypes() {
    return types;
  }

  // decorate the table name for each column name, e.g Table.col1
  private List<String> decorateTableName(List<String> columns) {
    return columns.stream()
        .map(col -> col.contains(".") ? col : this.tableName + "." + col)
        .toList();
  }

  public CreateTablePlan(String tableName, List<String> columns, List<String> types) {
    this.tableName = tableName;
    this.columns = decorateTableName(columns);
    this.types = types;
  }

  @Override
  public <T> T accept(PhysicalPlanVisitor<T> visitor) {
    return visitor.visitCreateTablePlan(this);
  }

  @Override
  public String toString() {
    return "CreateTablePlan{" +
        "tableName='" + tableName + '\'' +
        ", columns=" + columns +
        ", types=" + types +
        '}';
  }
}
