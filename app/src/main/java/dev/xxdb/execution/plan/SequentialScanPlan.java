package dev.xxdb.execution.plan;

public class SequentialScanPlan extends PhysicalPlan {
  @Override
  public <T> T accept(PhysicalPlanVisitor<T> visitor) {
    return visitor.visitSequentialScanPlan(this);
  }
}
