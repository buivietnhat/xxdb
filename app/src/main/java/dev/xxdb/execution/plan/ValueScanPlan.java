package dev.xxdb.execution.plan;

import dev.xxdb.types.Value;

import java.util.ArrayList;
import java.util.List;

public class ValueScanPlan extends PhysicalPlan {
  private final String tableName;
  private final List<String> columns;
  private final List<Value> values;

  public ValueScanPlan(String tableName, List<String> columns, List<Value> values) {
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
