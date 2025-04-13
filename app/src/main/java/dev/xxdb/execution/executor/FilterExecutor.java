package dev.xxdb.execution.executor;

import dev.xxdb.execution.ExecutionException;
import dev.xxdb.execution.plan.FilterPlan;
import dev.xxdb.storage.tuple.Tuple;

public class FilterExecutor implements Executor {
  public FilterExecutor(final FilterPlan plan) {
    throw new RuntimeException("unimplemented");
  }

  public Tuple next() throws ExecutionException {
    throw new RuntimeException("unimplemented");
  }
}
