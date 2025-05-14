package dev.xxdb.execution.plan;

public class HashJoinPlan extends PhysicalPlan {
  private final String leftJoinKey;
  private final String rightJoinKey;

  public String getLeftJoinKey() {
    return leftJoinKey;
  }

  public String getRightJoinKey() {
    return rightJoinKey;
  }

  public HashJoinPlan(String leftJoinKey, String rightJoinKey) {
    this.leftJoinKey = leftJoinKey;
    this.rightJoinKey = rightJoinKey;
  }

  @Override
  public <T> T accept(PhysicalPlanVisitor<T> visitor) {
    return visitor.visitHashJoinPlan(this);
  }

  @Override
  public String toString() {
    return "HashJoinPlan{" +
        "leftJoinKey='" + leftJoinKey + '\'' +
        ", rightJoinKey='" + rightJoinKey + '\'' +
        ", leftChild='" + leftChild + '\'' +
        ", rightChild='" + rightChild + '\'' +
        '}';
  }
}
