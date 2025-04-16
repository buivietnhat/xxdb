package dev.xxdb.execution.executor;

import dev.xxdb.execution.ExecutionException;
import dev.xxdb.storage.tuple.Tuple;

import java.util.Optional;

public class SortingExecutor extends Executor {
  @Override
  public Optional<TupleResult> next() throws ExecutionException {
    return Optional.empty();
  }
}
