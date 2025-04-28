package dev.xxdb.execution.executor;

import dev.xxdb.execution.ExecutionException;
import dev.xxdb.execution.plan.FilterPlan;

import java.util.Optional;

public class FilterExecutor extends Executor {
  private final FilterPlan plan;
  private final Executor child;

  public FilterExecutor(ExecutionContext ctx, FilterPlan plan, Executor child) {
    super(ctx);
    this.plan = plan;
    this.child = child;
  }

  @Override
  public void init() {

  }

  public Optional<TupleResult> next() throws ExecutionException {
    throw new RuntimeException("unimplemented");
  }

  @Override
  public String toString() {
    return "FilterExecutor{" +
        "child=" + child +
        '}';
  }
}
