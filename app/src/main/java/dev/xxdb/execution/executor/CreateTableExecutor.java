package dev.xxdb.execution.executor;

import dev.xxdb.catalog.Schema;
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
  public void init() {
  }

  @Override
  public Optional<TupleResult> next() throws ExecutionException {
    boolean created = ctx.catalog().createNewTable(plan.getTableName(), plan.getColumns(), plan.getTypes());
    if (!created) {
      throw new ExecutionException("The table already exist");
    }
    return Optional.empty();
  }

  @Override
  public Schema getOutputSchema() {
    return plan.getOutputSchema();
  }

  @Override
  public String toString() {
    return "CreateTableExecutor{}";
  }
}
