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

  public CreateTablePlan(String tableName, List<String> columns, List<String> types) {
    this.tableName = tableName;
    this.columns = columns;
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
