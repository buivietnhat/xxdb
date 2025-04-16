package dev.xxdb.execution.plan;

import dev.xxdb.storage.tuple.Tuple;
import java.util.function.Predicate;

public class FilterPlan extends PhysicalPlan {
  @Override
  public <T> T accept(PhysicalPlanVisitor<T> visitor) {
    return visitor.visitFilterPlan(this);
  }
}
