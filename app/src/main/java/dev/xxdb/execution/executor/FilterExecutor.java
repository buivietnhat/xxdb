package dev.xxdb.execution.executor;

import dev.xxdb.catalog.Schema;
import dev.xxdb.execution.ExecutionException;
import dev.xxdb.execution.plan.FilterPlan;
import dev.xxdb.storage.tuple.Tuple;
import java.util.Optional;

public class FilterExecutor extends Executor {
  private final FilterPlan plan;
  private final Executor child;
  private Schema tableSchema;

  public FilterExecutor(ExecutionContext ctx, FilterPlan plan, Executor child) {
    super(ctx);
    this.plan = plan;
    this.child = child;
  }

  @Override
  public void init() throws ExecutionException {
    child.init();
  }

  public Optional<TupleResult> next() throws ExecutionException {
    Optional<TupleResult> tupleResult = child.next();
    while (tupleResult.isPresent()) {
      Tuple tuple = tupleResult.get().tuple();
      if (plan.getPredicate().evaluate(tuple)) {
        return tupleResult;
      }

      tupleResult = child.next();
    }

    return Optional.empty();
  }

  @Override
  public Schema getOutputSchema() {
    return plan.getOutputSchema();
  }

  @Override
  public String toString() {
    return "FilterExecutor{" + "child=" + child + '}';
  }
}
