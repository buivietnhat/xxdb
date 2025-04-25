package dev.xxdb.execution.executor;

import dev.xxdb.execution.ExecutionException;
import dev.xxdb.execution.plan.CreateTablePlan;

import java.util.Optional;

public class CreateTableExecutor extends Executor {
  private final CreateTablePlan plan;

  public CreateTableExecutor(ExecutionContext ctx, CreateTablePlan plan) {
    super(ctx);
    this.plan = plan;
  }

  @Override
  public Optional<TupleResult> next() throws ExecutionException {
    ctx.catalog().createNewTable(plan.getTableName(), plan.getColumns(), plan.getTypes());
    return Optional.empty();
  }
}
