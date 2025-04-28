package dev.xxdb.execution.executor;

import dev.xxdb.execution.ExecutionException;
import dev.xxdb.execution.plan.HashJoinPlan;

import java.util.Optional;

public class HashJoinExecutor extends Executor {
  private final HashJoinPlan plan;
  private final Executor leftChild;
  private final Executor rightChild;

  public HashJoinExecutor(ExecutionContext ctx, HashJoinPlan plan, Executor leftChild, Executor rightChild) {
    super(ctx);
    this.plan = plan;
    this.leftChild = leftChild;
    this.rightChild = rightChild;
  }

  @Override
  public void init() {

  }

  @Override
  public Optional<TupleResult> next() throws ExecutionException {
    return Optional.empty();
  }

  @Override
  public String toString() {
    return "HashJoinExecutor{" +
        "leftChild=" + leftChild +
        ", rightChild=" + rightChild +
        '}';
  }
}
