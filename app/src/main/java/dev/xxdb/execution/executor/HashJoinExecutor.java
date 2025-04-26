package dev.xxdb.execution.executor;

import dev.xxdb.execution.ExecutionException;
import dev.xxdb.execution.plan.HashJoinPlan;

import java.util.Optional;

public class HashJoinExecutor extends Executor {
  private final HashJoinPlan plan;

  public HashJoinExecutor(ExecutionContext ctx, HashJoinPlan plan) {
    super(ctx);
    this.plan = plan;
  }

  @Override
  public void init() {

  }

  @Override
  public Optional<TupleResult> next() throws ExecutionException {
    return Optional.empty();
  }
}
