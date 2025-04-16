package dev.xxdb.execution.plan;

import dev.xxdb.types.Value;

import java.util.ArrayList;
import java.util.List;

public class InsertPlan extends PhysicalPlan {
  private final String tableName;

  public InsertPlan(String tableName) {
    this.tableName = tableName;
  }

  @Override
  public <T> T accept(PhysicalPlanVisitor<T> visitor) {
    return visitor.visitInsertPlan(this);
  }
}
