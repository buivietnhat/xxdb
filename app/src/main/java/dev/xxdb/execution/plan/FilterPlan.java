package dev.xxdb.execution.plan;

import dev.xxdb.types.Predicate;

public class FilterPlan extends PhysicalPlan {
  private final Predicate predicate;

  public FilterPlan(Predicate predicate) {
    this.predicate = predicate;
  }

  public Predicate getPredicate() {
    return predicate;
  }

  @Override
  public <T> T accept(PhysicalPlanVisitor<T> visitor) {
    return visitor.visitFilterPlan(this);
  }

  @Override
  public String toString() {
    return "FilterPlan{" + "predicate=" + predicate + ", child=" + leftChild + '}';
  }
}
