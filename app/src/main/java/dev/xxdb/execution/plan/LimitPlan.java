package dev.xxdb.execution.plan;

public class LimitPlan extends PhysicalPlan {
  private final int number;

  public LimitPlan(int number) {
    this.number = number;
  }

  public int getNumber() {
    return number;
  }

  @Override
  public <T> T accept(PhysicalPlanVisitor<T> visitor) {
    return visitor.visitLimitPlan(this);
  }

  @Override
  public String toString() {
    return "LimitPlan{" + "number=" + number + ", child=" + leftChild + '}';
  }
}
