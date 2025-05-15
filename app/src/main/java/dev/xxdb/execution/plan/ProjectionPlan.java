package dev.xxdb.execution.plan;

import java.util.List;

public class ProjectionPlan extends PhysicalPlan {
  private final List<String> columns;

  public ProjectionPlan(List<String> columns) {
    this.columns = columns;
  }

  public List<String> getColumns() {
    return columns;
  }

  @Override
  public <T> T accept(PhysicalPlanVisitor<T> visitor) {
    return visitor.visitProjectionPlan(this);
  }

  @Override
  public String toString() {
    return "ProjectionPlan{" + "columns=" + columns + ", child=" + leftChild + '}';
  }
}
