package dev.xxdb.execution.executor;

import dev.xxdb.execution.ExecutionException;
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
