package dev.xxdb.execution.executor;

import dev.xxdb.execution.ExecutionException;
import dev.xxdb.execution.plan.ValueScanPlan;

import java.util.Optional;

public class ValueScanExecutor extends Executor {
  private final ValueScanPlan plan;

  public ValueScanExecutor(ExecutionContext ctx, ValueScanPlan plan) {
    super(ctx);
    this.plan = plan;
  }

  @Override
  public void init() throws ExecutionException {
  }

  @Override
  public Optional<TupleResult> next() throws ExecutionException {
    throw new RuntimeException("unimplemented");
  }
}
