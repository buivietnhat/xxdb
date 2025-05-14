package dev.xxdb.execution.executor;

import dev.xxdb.catalog.Schema;
import dev.xxdb.execution.ExecutionException;
import dev.xxdb.execution.plan.ProjectionPlan;
import dev.xxdb.storage.tuple.Tuple;
import dev.xxdb.types.Value;

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
    child.init();
  }

  @Override
  public Optional<TupleResult> next() throws ExecutionException {
    Optional<TupleResult> childResult = child.next();
    if (childResult.isEmpty()) {
      return childResult;
    }

    Tuple childTuple = childResult.get().tuple();
    Tuple.Builder tupleBuilder = new Tuple.Builder();
    this.plan.getColumns().stream()
        .map(col -> childTuple.getValue(child.getOutputSchema(), col))
        .forEach(val -> tupleBuilder.addColumn(val.getTypeId(), val.getData()));

    Tuple producedTuple = tupleBuilder.build();
    return Optional.of(new TupleResult(producedTuple, childResult.get().rid()));
  }

  @Override
  public Schema getOutputSchema() {
    return plan.getOutputSchema();
  }

  @Override
  public String toString() {
    return "ProjectionExecutor{" +
        "child=" + child +
        '}';
  }
}
