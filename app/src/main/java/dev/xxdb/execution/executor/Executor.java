package dev.xxdb.execution.executor;

import dev.xxdb.execution.ExecutionException;
import dev.xxdb.storage.tuple.Tuple;

public interface Executor {
  Tuple next() throws ExecutionException;
}
