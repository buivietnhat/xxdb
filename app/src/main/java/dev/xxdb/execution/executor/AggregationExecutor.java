package dev.xxdb.execution.executor;

import dev.xxdb.execution.ExecutionException;
import dev.xxdb.storage.tuple.Tuple;

public class AggregationExecutor implements Executor {
  public Tuple next() throws ExecutionException {
    throw new RuntimeException("unimplemented");
  }
}
