package dev.xxdb.execution.plan;

import dev.xxdb.types.Value;

import java.util.List;

public class ValueScanPlan extends PhysicalPlan {
  private final String tableName;
  private final List<String> columns;
  private final List<List<Value>> values;

  public List<String> getColumns() {
    return columns;
  }

  public List<List<Value>> getValues() {
    return values;
  }

  public String getTableName() {
    return tableName;
  }

  public ValueScanPlan(String tableName, List<String> columns, List<List<Value>> values) {
    this.tableName = tableName;
    this.columns = columns;
    this.values = values;
  }

  @Override
  public <T> T accept(PhysicalPlanVisitor<T> visitor) {
    return visitor.visitValueScanPlan(this);
  }

  @Override
  public String toString() {
    return "ValueScanPlan{" +
        "tableName='" + tableName + '\'' +
        ", columns=" + columns +
        ", values=" + values +
        '}';
  }
}
