package dev.xxdb.execution.plan;

public class InsertPlan extends PhysicalPlan {
  private final String tableName;

  public InsertPlan(String tableName) {
    this.tableName = tableName;
  }

  public String getTableName() {
    return tableName;
  }

  @Override
  public <T> T accept(PhysicalPlanVisitor<T> visitor) {
    return visitor.visitInsertPlan(this);
  }

  @Override
  public String toString() {
    return "InsertPlan{" + "tableName='" + tableName + '\'' + ", leftChild: " + leftChild + '}';
  }
}
