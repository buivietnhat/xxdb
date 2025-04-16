package dev.xxdb.execution.executor;

import dev.xxdb.execution.ExecutionException;
import dev.xxdb.execution.plan.FilterPlan;
import dev.xxdb.storage.tuple.Tuple;

import java.util.Optional;

public class FilterExecutor extends Executor {
  public FilterExecutor(final FilterPlan plan) {
    throw new RuntimeException("unimplemented");
  }

  public Optional<TupleResult> next() throws ExecutionException {
    throw new RuntimeException("unimplemented");
  }
}
