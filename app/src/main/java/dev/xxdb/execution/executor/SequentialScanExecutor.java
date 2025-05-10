package dev.xxdb.execution.executor;

import dev.xxdb.catalog.Schema;
import dev.xxdb.execution.ExecutionException;
import dev.xxdb.execution.plan.SequentialScanPlan;

import java.util.Optional;

public class SequentialScanExecutor extends Executor {
  private final SequentialScanPlan plan;

  public SequentialScanExecutor(ExecutionContext ctx, SequentialScanPlan plan) {
    super(ctx);
    this.plan = plan;
  }

  @Override
  public void init() throws ExecutionException {

  }

  @Override
  public Optional<TupleResult> next() throws ExecutionException {
    return Optional.empty();
  }

  @Override
  public Schema getOutputSchema() {
    return plan.getOutputSchema();
  }

  @Override
  public String toString() {
    return "SequentialScanExecutor{}";
  }
}
