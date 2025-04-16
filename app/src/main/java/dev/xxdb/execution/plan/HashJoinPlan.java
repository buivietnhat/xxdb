package dev.xxdb.execution.plan;

public class HashJoinPlan extends PhysicalPlan {
  @Override
  public <T> T accept(PhysicalPlanVisitor<T> visitor) {
    return visitor.visitHashJoinPlan(this);
  }
}
