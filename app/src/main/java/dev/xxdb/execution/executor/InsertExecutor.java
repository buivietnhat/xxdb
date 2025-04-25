package dev.xxdb.execution.executor;

import dev.xxdb.execution.ExecutionException;
import dev.xxdb.execution.plan.InsertPlan;
import dev.xxdb.storage.tuple.Tuple;

import java.util.Optional;

public class InsertExecutor extends Executor {
  private final InsertPlan plan;
  // Insert needs a child who knows how to supply tuples
  private final Executor child;

  public InsertExecutor(ExecutionContext ctx, InsertPlan plan, Executor child) {
    super(ctx);
    this.plan = plan;
    this.child = child;
  }

  @Override
  public Optional<TupleResult> next() throws ExecutionException {
    return Optional.empty();
  }
}
