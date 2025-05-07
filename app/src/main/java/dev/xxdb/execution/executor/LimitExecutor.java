package dev.xxdb.execution.executor;

import dev.xxdb.execution.ExecutionException;
import dev.xxdb.execution.plan.LimitPlan;

import java.util.Optional;

public class LimitExecutor extends Executor  {
  private final LimitPlan plan;
  private final Executor child;

  public LimitExecutor(ExecutionContext ctx, LimitPlan plan, Executor child) {
    super(ctx);
    this.plan = plan;
    this.child = child;
  }

  @Override
  public void init() throws ExecutionException {

  }

  @Override
  public Optional<TupleResult> next() throws ExecutionException {
    return Optional.empty();
  }

  @Override
  public String toString() {
    return "LimitExecutor{" +
        "child=" + child +
        '}';
  }
}
