package dev.xxdb.execution;

import dev.xxdb.execution.executor.ExecutionContext;
import dev.xxdb.execution.executor.Executor;
import dev.xxdb.execution.executor.TupleResult;
import dev.xxdb.execution.plan.ProjectionPlan;

import java.util.Optional;

public class ProjectionExecutor extends Executor {
  private final ProjectionPlan plan;
  private final Executor child;

  public ProjectionExecutor(ExecutionContext ctx, ProjectionPlan plan, Executor child) {
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
    return "ProjectionExecutor{" +
        "child=" + child +
        '}';
  }
}
