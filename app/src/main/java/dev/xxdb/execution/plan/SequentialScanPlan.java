package dev.xxdb.execution.plan;

public class SequentialScanPlan extends PhysicalPlan {
  private final String table;

  public String getTable() {
    return table;
  }

  public SequentialScanPlan(String table) {
    this.table = table;
  }

  @Override
  public <T> T accept(PhysicalPlanVisitor<T> visitor) {
    return visitor.visitSequentialScanPlan(this);
  }

  @Override
  public String toString() {
    return "SequentialScanPlan{" +
        "table='" + table + '\'' +
        '}';
  }
}
