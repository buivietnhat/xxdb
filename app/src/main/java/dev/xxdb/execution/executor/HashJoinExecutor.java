package dev.xxdb.execution.executor;

import dev.xxdb.execution.ExecutionException;

import java.util.Optional;

public class HashJoinExecutor extends Executor {
  @Override
  public Optional<TupleResult> next() throws ExecutionException {
    return Optional.empty();
  }
}
